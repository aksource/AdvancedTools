package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class Entity_ThrowingKnife extends EntityThrowable
{
	public float exRotate = 0.0F;
	private boolean poison;
	public Entity Master;
	private int ticksInAir = 0;

	public void setEntityDead(boolean var1)
	{
		super.setDead();

		if (!var1 && !this.worldObj.isRemote) {
			if (this.isPoison()) {
				this.dropItem(AdvancedTools.PoisonKnife, 1);
			} else {
				this.dropItem(AdvancedTools.ThrowingKnife, 1);
			}
		}
	}

	public Entity_ThrowingKnife(World var1)
	{
		super(var1);
	}

	public Entity_ThrowingKnife(World var1, EntityLivingBase var2, float var3, boolean var4)
	{
		super(var1, var2);
		this.setSize(0.5F, 0.5F);
		this.setPoison(var4);

		if (!this.isPoison())
		{
			this.exRotate = 180.0F;
		}

/*		this.Master = var2;

		this.setLocationAndAngles(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);//-
		this.posY -= 0.10000000149011612D;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);//-
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
        //todo rendering test
        float speed = 0.5f;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * speed);
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * speed);
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * speed);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, var3 * 1.5F, 1.0F);*/
	}
	@Override
	protected void entityInit(){}

    //Todo EntityThrowableを継承してみた。
//	@Override
//	public void setThrowableHeading(double var1, double var3, double var5, float var7, float var8) {
//		float var9 = MathHelper.sqrt_double(var1 * var1 + var3 * var3 + var5 * var5);
//		var1 /= (double)var9;
//		var3 /= (double)var9;
//		var5 /= (double)var9;
//		var1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
//		var3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
//		var5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)var8;
//		var1 *= (double)var7;
//		var3 *= (double)var7;
//		var5 *= (double)var7;
//		this.motionX = var1;
//		this.motionY = var3;
//		this.motionZ = var5;
//		float var10 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
//		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(var1, var5) * 180.0D / Math.PI);
//		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(var3, (double)var10) * 180.0D / Math.PI);
//	}

	/**
	 * Called to update the entity's position/logic.
	 */
    //Todo EntityThrowableを継承してみた。
	public void onUpdateOld()
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

		++this.ticksInAir;
		Vec3 var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var4 = this.worldObj.func_147447_a(var17, var3, false, true, false);
		var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (var4 != null)
		{
			var3 = Vec3.createVectorHelper(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
		}

		Entity var5 = null;
        @SuppressWarnings("unchecked")
		List<Entity> var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double var7 = 0.0D;

		for (Entity var10 : var6)
		{
			if (var10.canBeCollidedWith() && (var10 != this.Master || this.ticksInAir >= 10))
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
					EntityLiving var19 = (EntityLiving)var4.entityHit;
					var19.addPotionEffect(new PotionEffect(Potion.poison.id, 60, 1));
				}

				this.setEntityDead(var4.entityHit.attackEntityFrom(var20, this.isPoison() ? 2 : 4));
			}
			else
			{
				this.motionX = (double)((float)(var4.hitVec.xCoord - this.posX));
				this.motionY = (double)((float)(var4.hitVec.yCoord - this.posY));
				this.motionZ = (double)((float)(var4.hitVec.zCoord - this.posZ));
				var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
				this.posX -= this.motionX / (double)var18 * 0.05000000074505806D;
				this.posY -= this.motionY / (double)var18 * 0.05000000074505806D;
				this.posZ -= this.motionZ / (double)var18 * 0.05000000074505806D;
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

    @Override
    protected void onImpact(MovingObjectPosition var1) {
        if (var1.entityHit != null) {
            DamageSource var20;

            if (this.Master == null) {
                var20 = DamageSource.causeThrownDamage(this, this);
            } else {
                var20 = DamageSource.causeThrownDamage(this, this.Master);
            }

            if (var1.entityHit instanceof EntityLivingBase && this.isPoison()) {
                EntityLivingBase var19 = (EntityLivingBase)var1.entityHit;
                var19.addPotionEffect(new PotionEffect(Potion.poison.id, 60, 1));
            }

            this.setEntityDead(var1.entityHit.attackEntityFrom(var20, this.isPoison() ? 2 : 4));
        } else {
            float var18;
            this.motionX = (double)((float)(var1.hitVec.xCoord - this.posX));
            this.motionY = (double)((float)(var1.hitVec.yCoord - this.posY));
            this.motionZ = (double)((float)(var1.hitVec.zCoord - this.posZ));
            var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double)var18 * 0.05000000074505806D;
            this.posY -= this.motionY / (double)var18 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double)var18 * 0.05000000074505806D;
            this.setEntityDead(false);
        }
    }

    /**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
//    @Override
//	public void writeEntityToNBT(NBTTagCompound var1)
//	{
//		var1.setShort("xTile", (short)this.xTile);
//		var1.setShort("yTile", (short)this.yTile);
//		var1.setShort("zTile", (short)this.zTile);
		//		 var1.setByte("inTile", (byte)this.inTile);
//		var1.setByte("inData", (byte)this.inData);
//		var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
//	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
//    @Override
//	public void readEntityFromNBT(NBTTagCompound var1)
//	{
//		this.xTile = var1.getShort("xTile");
//		this.yTile = var1.getShort("yTile");
//		this.zTile = var1.getShort("zTile");
		//		 this.inTile = var1.getByte("inTile") & 255;
//		this.inData = var1.getByte("inData") & 255;
//		this.inGround = var1.getByte("inGround") == 1;
//	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
//    @Override
//	public void onCollideWithPlayer(EntityPlayer var1) {}

    @Override
    protected float getGravityVelocity() {
        return 0.01F;
    }
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
