package Nanashi.AdvancedTools.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class Entity_BBFireBall extends Entity
{
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private int inData;
	private boolean inGround;
	private boolean explodeFlag;
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
		this.explodeFlag = false;
		this.ticksInAir = 0;
		this.expower = 1.5F;
		this.setSize(0.5F, 0.5F);
	}

	public void setEntityDead()
	{
		super.setDead();
		float var1 = this.explodeFlag ? this.expower * 1.2F : 1.5F;

		if (!this.isWet())
		{
			List var2 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, this.boundingBox.expand((double)var1, (double)var1, (double)var1));
			int var3;

			for (var3 = 0; var3 < var2.size(); ++var3)
			{
				EntityLiving var4 = (EntityLiving)var2.get(var3);
				var4.setFire(5);
			}

			var3 = MathHelper.floor_double(this.posX);
			int var8 = MathHelper.floor_double(this.posY);
			int var5 = MathHelper.floor_double(this.posZ);

			if (this.worldObj.getBlock(var3, var8, var5) == Blocks.air && Blocks.fire.canPlaceBlockAt(this.worldObj, var3, var8, var5))
			{
				this.worldObj.setBlock(var3, var8, var5, Blocks.fire);
			}

			for (int var6 = 0; var6 < 4; ++var6)
			{
				var3 = MathHelper.floor_double(this.posX) + this.rand.nextInt(3) - 1;
				var8 = MathHelper.floor_double(this.posY) + this.rand.nextInt(3) - 1;
				var5 = MathHelper.floor_double(this.posZ) + this.rand.nextInt(3) - 1;

				if (this.worldObj.getBlock(var3, var8, var5) == Blocks.air && Blocks.fire.canPlaceBlockAt(this.worldObj, var3, var8, var5))
				{
					this.worldObj.setBlock(var3, var8, var5, Blocks.fire);
				}
			}

			if (this.explodeFlag)
			{
				this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, this.expower, true);
			}
			else
			{
				this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 0.0F, true);
			}
		}
		else
		{
			for (int var7 = 0; var7 < 10; ++var7)
			{
				this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public Entity_BBFireBall(World var1)
	{
		super(var1);
		this.Init();
	}

	public Entity_BBFireBall(World var1, double var2, double var4, double var6)
	{
		super(var1);
		this.Init();
		this.setPosition(var2, var4, var6);
		this.yOffset = 0.0F;
	}

	public Entity_BBFireBall(World var1, EntityLivingBase var2, float var3, boolean var4)
	{
		super(var1);
		this.Init();
		this.yOffset = 0.0F;
		this.explodeFlag = var4;
		this.doesArrowBelongToPlayer = false;
		this.FB_Master = var2;
		this.doesArrowBelongToPlayer = var2 instanceof EntityPlayer;
		this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.08F);
		this.posY -= 0.12D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.08F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setArrowHeading(this.motionX, this.motionY, this.motionZ, 1.8F, 1.0F);
	}

	protected void entityInit() {}

	public void setArrowHeading(double var1, double var3, double var5, float var7, float var8)
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
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.setFire(1);

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI);
		}

		Block var17 = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

		if (var17 != Blocks.air)
		{
			var17.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB var2 = var17.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

			if (var2 != null && var2.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}

		if (!this.inGround && this.ticksInAir <= 15)
		{
			++this.ticksInAir;
			Vec3 var18 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition var4 = this.worldObj.func_147447_a(var18, var3, false, true, false);
			var18 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
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
						MovingObjectPosition var13 = var12.calculateIntercept(var18, var3);

						if (var13 != null)
						{
							double var14 = var18.distanceTo(var13.hitVec);

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
				float var19;

				if (var4.entityHit != null)
				{
					var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					var10 = null;
					DamageSource var22;

					if (this.FB_Master == null)
					{
						var22 = DamageSource.causeThrownDamage(this, this);
					}
					else
					{
						var22 = DamageSource.causeThrownDamage(this, this.FB_Master);
					}

					var4.entityHit.setFire(5);
					var4.entityHit.attackEntityFrom(var22, 2 + (this.explodeFlag ? 4 : 0));
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
					var19 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / (double)var19 * 0.05000000074505806D;
					this.posY -= this.motionY / (double)var19 * 0.05000000074505806D;
					this.posZ -= this.motionZ / (double)var19 * 0.05000000074505806D;
					this.inGround = true;
				}
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			double var21 = this.prevPosX - this.posX;
			double var20 = this.prevPosY - this.posY;
			double var23 = this.prevPosZ - this.posZ;

			for (int var15 = 0; var15 < 10; ++var15)
			{
				float var16 = 0.1F * (float)var15;
				this.worldObj.spawnParticle("smoke", this.posX + var21 * (double)var16, this.posY + 0.5D + var20 * (double)var16, this.posZ + var23 * (double)var16, 0.0D, 0.0D, 0.0D);

				if (this.explodeFlag)
				{
					this.worldObj.spawnParticle("reddust", this.posX + var21 * (double)var16, this.posY + 0.5D + var20 * (double)var16, this.posZ + var23 * (double)var16, 0.0D, 0.0D, 0.0D);
				}
			}

			float var24 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var24) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
