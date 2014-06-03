package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class Entity_ThrowingKnife extends Entity implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inData = 0;
	public float exRotate = 0.0F;
	private boolean inGround = false;
	private boolean poison;
	public Entity Master;
	private int ticksInAir = 0;

	public void setEntityDead(boolean var1)
	{
		super.setDead();

		if (!var1 && !this.worldObj.isRemote)
		{
			if (this.isPoison())
			{
				this.dropItem(AdvancedTools.PoisonKnife, 1);
			}
			else
			{
				this.dropItem(AdvancedTools.ThrowingKnife, 1);
			}
		}
	}

//	public Entity_ThrowingKnife(World var1)
//	{
//		super(var1);
//		this.setSize(0.5F, 0.5F);
//	}
//
//	public Entity_ThrowingKnife(World var1, double var2, double var4, double var6)
//	{
//		super(var1);
//		this.setSize(0.5F, 0.5F);
//		this.setPosition(var2, var4, var6);
//		this.yOffset = 0.0F;
//	}

	public Entity_ThrowingKnife(World var1, EntityLivingBase var2, float var3, boolean var4)
	{
		super(var1);
		this.setSize(0.5F, 0.5F);
		this.setPoison(var4);

		if (!this.isPoison())
		{
			this.exRotate = 180.0F;
		}

		this.Master = var2;

		this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, var3 * 1.5F, 1.0F);
	}
	@Override
	protected void entityInit(){}

	@Override
	public void setThrowableHeading(double var1, double var3, double var5, float var7, float var8) {
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
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI);
		}

		Block var16 = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);

		//		 if (var16 > 0)
		//		 {
		var16.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
		AxisAlignedBB var2 = var16.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

		if (var2 != null && var2.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
		{
			this.inGround = true;
			this.setEntityDead(false);
		}
		//		 }

		++this.ticksInAir;
		Vec3 var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
		Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var4 = this.worldObj.func_147447_a(var17, var3, false, true, false);
		var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
		var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (var4 != null)
		{
			var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
		}

		Entity var5 = null;
		List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double var7 = 0.0D;
		Entity var10;

		for (Object object : var6)
		{
			var10 = (Entity)object;

			if (var10.canBeCollidedWith() && (var10 != this.Master || this.ticksInAir >= 5))
			{
				float var11 = 0.3F;
				AxisAlignedBB var12 = var10.boundingBox.expand((double)var11, (double)var11, (double)var11);
				MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);

				if (var13 != null)
				{
					double var14 = var17.distanceTo(var13.hitVec);

					if (var14 < var7 || var7 == 0.0D)
					{
						var5 = var10;
						var7 = var14;
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
			float var18;

			if (var4.entityHit != null)
			{
//				var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
//				var10 = null;
				DamageSource var20;

				if (this.Master == null)
				{
					var20 = DamageSource.causeThrownDamage(this, this);
				}
				else
				{
					var20 = DamageSource.causeThrownDamage(this, this.Master);
				}

				if (var4.entityHit instanceof EntityLiving && this.isPoison())
				{
					EntityLiving var19 = (EntityLiving)((EntityLiving)var4.entityHit);
					var19.addPotionEffect(new PotionEffect(Potion.poison.id, 60, 1));
				}

				this.setEntityDead(var4.entityHit.attackEntityFrom(var20, this.isPoison() ? 2 : 4));
			}
			else
			{
				this.xTile = var4.blockX;
				this.yTile = var4.blockY;
				this.zTile = var4.blockZ;
//				this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
				this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
				this.motionX = (double)((float)(var4.hitVec.xCoord - this.posX));
				this.motionY = (double)((float)(var4.hitVec.yCoord - this.posY));
				this.motionZ = (double)((float)(var4.hitVec.zCoord - this.posZ));
				var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
				this.posX -= this.motionX / (double)var18 * 0.05000000074505806D;
				this.posY -= this.motionY / (double)var18 * 0.05000000074505806D;
				this.posZ -= this.motionZ / (double)var18 * 0.05000000074505806D;
				this.inGround = true;
				this.setEntityDead(false);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
//		float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

//		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var15) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
//		{
//			;
//		}

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
		this.motionX *= 0.99D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.99D;
		this.motionY -= 0.02D;
		this.exRotate += 50.0F;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
    @Override
	public void writeEntityToNBT(NBTTagCompound var1)
	{
		var1.setShort("xTile", (short)this.xTile);
		var1.setShort("yTile", (short)this.yTile);
		var1.setShort("zTile", (short)this.zTile);
		//		 var1.setByte("inTile", (byte)this.inTile);
		var1.setByte("inData", (byte)this.inData);
		var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
    @Override
	public void readEntityFromNBT(NBTTagCompound var1)
	{
		this.xTile = var1.getShort("xTile");
		this.yTile = var1.getShort("yTile");
		this.zTile = var1.getShort("zTile");
		//		 this.inTile = var1.getByte("inTile") & 255;
		this.inData = var1.getByte("inData") & 255;
		this.inGround = var1.getByte("inGround") == 1;
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
    @Override
	public void onCollideWithPlayer(EntityPlayer var1) {}

    @Override
	public float getShadowSize()
	{
		return 0.0F;
	}

	public void setPoison(boolean var1)
	{
		this.poison = var1;
	}

	public boolean isPoison()
	{
		return this.poison;
	}


}
