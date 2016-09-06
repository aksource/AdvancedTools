package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class Entity_ThrowingKnife extends EntityThrowable {
    public float exRotate = 0.0F;
    public Entity Master;
    private boolean poison;
    private int ticksInAir = 0;

    public Entity_ThrowingKnife(World var1) {
        super(var1);
    }

    public Entity_ThrowingKnife(World var1, EntityLivingBase var2, float var3, boolean var4) {
        super(var1, var2);
        this.setSize(0.5F, 0.5F);
        this.setPoison(var4);

        if (!this.isPoison()) {
            this.exRotate = 180.0F;
        }
    }

    public void setEntityDead(boolean var1) {
        super.setDead();

        if (!var1 && !this.worldObj.isRemote) {
            if (this.isPoison()) {
                this.dropItem(AdvancedTools.PoisonKnife, 1);
            } else {
                this.dropItem(AdvancedTools.ThrowingKnife, 1);
            }
        }
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate() {
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var1) * 180.0D / Math.PI);
        }
        super.onUpdate();
    }

    /**
     * Called to update the entity's position/logic.
     */
    //Todo EntityThrowableを継承してみた。
    public void onUpdateOld() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var1) * 180.0D / Math.PI);
        }

        ++this.ticksInAir;
        Vec3d var17 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult rayTraceResult = this.worldObj.rayTraceBlocks(var17, var3, false, true, false);
        var17 = new Vec3d(this.posX, this.posY, this.posZ);
        var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (rayTraceResult != null) {
            var3 = new Vec3d(rayTraceResult.hitVec.xCoord, rayTraceResult.hitVec.yCoord, rayTraceResult.hitVec.zCoord);
        }

        Entity var5 = null;
        @SuppressWarnings("unchecked")
        List<Entity> var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double var7 = 0.0D;

        for (Entity var10 : var6) {
            if (var10.canBeCollidedWith() && (var10 != this.Master || this.ticksInAir >= 10)) {
                float var11 = 0.3F;
                AxisAlignedBB var12 = var10.getEntityBoundingBox().expand((double) var11, (double) var11, (double) var11);
                RayTraceResult var13 = var12.calculateIntercept(var17, var3);

                if (var13 != null) {
                    double var14 = var17.distanceTo(var13.hitVec);

                    if (var14 < var7 || var7 == 0.0D) {
                        var5 = var10;
                        var7 = var14;
                    }
                }
            }
        }

        if (var5 != null) {
            rayTraceResult = new RayTraceResult(var5);
        }

        if (rayTraceResult != null) {
            float var18;

            if (rayTraceResult.entityHit != null) {
                DamageSource var20;

                if (this.Master == null) {
                    var20 = DamageSource.causeThrownDamage(this, this);
                } else {
                    var20 = DamageSource.causeThrownDamage(this, this.Master);
                }

                if (rayTraceResult.entityHit instanceof EntityLiving && this.isPoison()) {
                    EntityLiving var19 = (EntityLiving) rayTraceResult.entityHit;
                    var19.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 60, 1));
                }

                this.setEntityDead(rayTraceResult.entityHit.attackEntityFrom(var20, this.isPoison() ? 2 : 4));
            } else {
                this.motionX = (double) ((float) (rayTraceResult.hitVec.xCoord - this.posX));
                this.motionY = (double) ((float) (rayTraceResult.hitVec.yCoord - this.posY));
                this.motionZ = (double) ((float) (rayTraceResult.hitVec.zCoord - this.posZ));
                var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double) var18 * 0.05000000074505806D;
                this.posY -= this.motionY / (double) var18 * 0.05000000074505806D;
                this.posZ -= this.motionZ / (double) var18 * 0.05000000074505806D;
                this.setEntityDead(false);
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
//		float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

//		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var15) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
//		{
//			;
//		}

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
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
    protected void onImpact(RayTraceResult var1) {
        if (var1.entityHit != null) {
            DamageSource var20;

            if (this.Master == null) {
                var20 = DamageSource.causeThrownDamage(this, this);
            } else {
                var20 = DamageSource.causeThrownDamage(this, this.Master);
            }

            if (var1.entityHit instanceof EntityLivingBase && this.isPoison()) {
                EntityLivingBase var19 = (EntityLivingBase) var1.entityHit;
                var19.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 60, 1));
            }

            this.setEntityDead(var1.entityHit.attackEntityFrom(var20, this.isPoison() ? 2 : 4));
        } else {
            float var18;
            this.motionX = (double) ((float) (var1.hitVec.xCoord - this.posX));
            this.motionY = (double) ((float) (var1.hitVec.yCoord - this.posY));
            this.motionZ = (double) ((float) (var1.hitVec.zCoord - this.posZ));
            var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double) var18 * 0.05000000074505806D;
            this.posY -= this.motionY / (double) var18 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double) var18 * 0.05000000074505806D;
            this.setEntityDead(false);
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.01F;
    }

    public boolean isPoison() {
        return this.poison;
    }

    public void setPoison(boolean var1) {
        this.poison = var1;
    }


}
