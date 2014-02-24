package Nanashi.AdvancedTools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Entity_SBWindEdge extends Entity
{
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private int inData;
	private boolean inGround;
	public boolean doesArrowBelongToPlayer;
	public Entity FB_Master;
	private int ticksInAir;
	private float expower;

	private void Init()
	{
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = Blocks.air;
		this.inData = 0;
		this.inGround = false;
		this.ticksInAir = 0;
		this.expower = 2.0F;
		this.setSize(0.5F, 0.5F);
	}

	public void setEntityDead()
	{
		super.setDead();

		for (int var1 = 0; var1 < 10; ++var1)
		{
			this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	public Entity_SBWindEdge(World var1)
	{
		super(var1);
		this.Init();
	}

	public Entity_SBWindEdge(World var1, double var2, double var4, double var6)
	{
		super(var1);
		this.Init();
		this.setPosition(var2, var4, var6);
		this.yOffset = 0.0F;
	}

	public Entity_SBWindEdge(World var1, EntityLivingBase var2, float var3)
	{
		super(var1);
		this.Init();
		this.yOffset = 0.0F;
		this.doesArrowBelongToPlayer = false;
		this.FB_Master = var2;
		this.doesArrowBelongToPlayer = var2 instanceof EntityPlayer;
		this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.08F);
		this.posY -= 0.3D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.08F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setHeading(this.motionX, this.motionY, this.motionZ, 1.8F, 1.0F);
	}

	protected void entityInit() {}

	public void setHeading(double var1, double var3, double var5, float var7, float var8)
	{
		float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
		var1 /= (double)var9;
		var3 /= (double)var9;
		var5 /= (double)var9;
		var1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
		var3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
		var5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
		var1 *= (double)var7;
		var3 *= (double)var7;
		var5 *= (double)var7;
		this.motionX = var1;
		this.motionY = var3;
		this.motionZ = var5;
		float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)var10) * 180.0D / Math.PI);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		super.onUpdate();

		if (this.isBurning())
		{
			this.setEntityDead();
		}
		else
		{
			if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
			{
				float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
				this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI);
			}

			Block var31 = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

			//			 if (var31 > 0)
			//			 {
			var31.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB var2 = var31.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

			if (var2 != null && var2.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
			//			 }

			if (!this.inGround && this.ticksInAir <= 15)
			{
				++this.ticksInAir;
				Vec3 var32 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
				Vec3 var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
				MovingObjectPosition var4 = this.worldObj.func_147447_a(var32, var3, false, true, false);
				var32 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
				var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

				if (var4 != null)
				{
					var3 = Vec3.createVectorHelper(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
				}

				Entity var5 = null;
				List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
				double var7 = 0.0D;
				Entity var10;
				if (!this.worldObj.isRemote)
				{
					for (int var9 = 0; var9 < var6.size(); ++var9)
					{
						var10 = (Entity)var6.get(var9);

						if (var10.canBeCollidedWith() && (var10 != this.FB_Master || this.ticksInAir >= 5))
						{
							float var11 = 0.3F;
							AxisAlignedBB var12 = var10.boundingBox.expand((double)var11, (double)var11, (double)var11);
							MovingObjectPosition var13 = var12.calculateIntercept(var32, var3);

							if (var13 != null)
							{
								double var14 = var32.distanceTo(var13.hitVec);

								if (var14 < var7 || var7 == 0.0D)
								{
									var5 = var10;
									var7 = var14;
								}
							}
						}
					}
				}
				if (var5 != null)
				{
					var4 = new MovingObjectPosition(var5);
				}

				if (var4 != null)
				{
					float var33;

					if (var4.entityHit != null)
					{
						var33 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
						var10 = null;
						DamageSource var36;

						if (this.FB_Master == null)
						{
							var36 = DamageSource.causeThrownDamage(this, this);
						}
						else
						{
							var36 = DamageSource.causeThrownDamage(this, this.FB_Master);
						}

						Entity var34 = var4.entityHit;
						var34.attackEntityFrom(var36, 2);
						var34.addVelocity(this.motionX * 0.8d, var34.motionY * 1.414D, this.motionZ * 0.8d);
						this.setEntityDead();
					}
					else
					{
						this.xTile = var4.blockX;
						this.yTile = var4.blockY;
						this.zTile = var4.blockZ;
						this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
						this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
						this.motionX = (double)((float)(var4.hitVec.xCoord - this.posX));
						this.motionY = (double)((float)(var4.hitVec.yCoord - this.posY));
						this.motionZ = (double)((float)(var4.hitVec.zCoord - this.posZ));
						var33 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
						this.posX -= this.motionX / (double)var33 * 0.05000000074505806D;
						this.posY -= this.motionY / (double)var33 * 0.05000000074505806D;
						this.posZ -= this.motionZ / (double)var33 * 0.05000000074505806D;
						this.inGround = true;
					}
				}

				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;
				this.posX += this.motionX;
				this.posY += this.motionY;
				this.posZ += this.motionZ;
				double var35 = this.prevPosX - this.posX;
				double var37 = this.prevPosY - this.posY;
				double var38 = this.prevPosZ - this.posZ;
				int var15;

				for (var15 = 0; var15 < 10; ++var15)
				{
					float var16 = 0.1F * (float)var15;
					this.worldObj.spawnParticle("explode", this.posX + var35 * (double)var16, this.posY + 0.5D + var37 * (double)var16, this.posZ + var38 * (double)var16, 0.0D, 0.0D, 0.0D);
				}

				for (var15 = 0; var15 < 40; ++var15)
				{
					double var40 = (double)this.rotationYaw / 180.0D * Math.PI;
					double var18 = (double)(-this.rotationPitch) / 180.0D * Math.PI;
					float var20 = 0.157F * (float)var15;
					double var21 = 0.25D * Math.cos((double)var20);
					double var23 = 0.25D * -Math.sin((double)var20);
					double var25 = var21 * Math.cos(var40) + var23 * Math.sin(var18) * Math.sin(var40);
					double var27 = var23 * Math.cos(var18);
					double var29 = -var21 * Math.sin(var40) + var23 * Math.sin(var18) * Math.cos(var40);
					this.worldObj.spawnParticle("explode", this.posX, this.posY + 0.5D, this.posZ, var25, var27, var29);
				}

				float var39 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

				for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var39) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
				{
					;
				}

				while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
				{
					this.prevRotationPitch += 360.0F;
				}

				while (this.rotationYaw - this.prevRotationYaw < -180.0F)
				{
					this.prevRotationYaw -= 360.0F;
				}

				while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
				{
					this.prevRotationYaw += 360.0F;
				}

				this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
				this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

				if (this.isInWater() || this.isWet())
				{
					this.setEntityDead();
				}

				this.setPosition(this.posX, this.posY, this.posZ);
			}
			else
			{
				this.setEntityDead();
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound var1)
	{
		var1.setShort("xTile", (short)this.xTile);
		var1.setShort("yTile", (short)this.yTile);
		var1.setShort("zTile", (short)this.zTile);
		//		 var1.setByte("inTile", (byte)this.inTile);
		var1.setByte("inData", (byte)this.inData);
		var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		var1.setBoolean("player", this.doesArrowBelongToPlayer);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound var1)
	{
		this.xTile = var1.getShort("xTile");
		this.yTile = var1.getShort("yTile");
		this.zTile = var1.getShort("zTile");
		//		 this.inTile = var1.getByte("inTile") & 255;
		this.inData = var1.getByte("inData") & 255;
		this.inGround = var1.getByte("inGround") == 1;
		this.doesArrowBelongToPlayer = var1.getBoolean("player");
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void onCollideWithPlayer(EntityPlayer var1) {}

	public float getShadowSize()
	{
		return 0.0F;
	}
}
