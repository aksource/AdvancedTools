package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.utils.Items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Entity_ThrowingKnife extends EntityThrowable {
    public float exRotate = 0.0F;
    private boolean poison;

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

        if (!var1 && !this.getEntityWorld().isRemote) {
            if (this.isPoison()) {
                this.dropItem(Items.PoisonKnife, 1);
            } else {
                this.dropItem(Items.ThrowingKnife, 1);
            }
        }
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate() {
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float var1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var1) * 180.0D / Math.PI);
        }
        super.onUpdate();
    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult rayTraceResult) {
        if (rayTraceResult.entityHit != null) {
            DamageSource var20;

            if (this.getThrower() == null) {
                var20 = DamageSource.causeThrownDamage(this, this);
            } else {
                var20 = DamageSource.causeThrownDamage(this, this.getThrower());
            }

            if (rayTraceResult.entityHit instanceof EntityLivingBase && this.isPoison()) {
                EntityLivingBase var19 = (EntityLivingBase) rayTraceResult.entityHit;
                Potion poison = Potion.getPotionFromResourceLocation("poison");
                if (poison != null) {
                    var19.addPotionEffect(new PotionEffect(poison, 60, 1));
                }
            }

            this.setEntityDead(rayTraceResult.entityHit.attackEntityFrom(var20, this.isPoison() ? 2 : 4));
        } else {
            float var18;
            this.motionX = (double) ((float) (rayTraceResult.hitVec.x - this.posX));
            this.motionY = (double) ((float) (rayTraceResult.hitVec.y - this.posY));
            this.motionZ = (double) ((float) (rayTraceResult.hitVec.z - this.posZ));
            var18 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
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

    private void setPoison(boolean var1) {
        this.poison = var1;
    }


}
