package Nanashi.AdvancedTools.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class Entity_PGPowerBomb extends Entity {
    public EntityLivingBase PB_Master;
    private float expower;

    public Entity_PGPowerBomb(World var1) {
        super(var1);
        this.Init();
    }

    public Entity_PGPowerBomb(World var1, double var2, double var4, double var6) {
        super(var1);
        this.Init();
        this.setPosition(var2, var4, var6);
    }

    public Entity_PGPowerBomb(World var1, EntityLivingBase var2, float var3) {
        super(var1);
        this.expower = var3;
        this.Init();
        this.PB_Master = var2;
        this.setLocationAndAngles(var2.posX, var2.posY + (double) var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
        double var4 = this.posX + 4.0D * (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)) * (double) MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        double var6 = this.posZ + 4.0D * (double) MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * (double) MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        double var8 = this.posY + 4.0D * (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        Vec3d var10 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d var11 = new Vec3d(var4, var8, var6);
        RayTraceResult rayTraceResult = this.getEntityWorld().rayTraceBlocks(var10, var11, false, true, false);

        if (rayTraceResult == null) {
            this.setPosition(var4, var8, var6);
        } else {
            Vec3d var13 = rayTraceResult.hitVec;
            this.setPosition(var13.x, var13.y, var13.z);
        }
    }

    private void Init() {
        this.setSize(0.5F, 0.5F);
    }

    private void setEntityDead() {
        super.setDead();
        this.getEntityWorld().playSound(this.posX, this.posY, this.posZ,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE,
                4.0F, (1.0F + (this.getEntityWorld().rand.nextFloat() - this.getEntityWorld().rand.nextFloat()) * 0.2F) * 0.7F, false);
        List var1 = this.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(5.0D, 5.0D, 5.0D));
        int var2;

        for (var2 = 0; var2 < var1.size(); ++var2) {
            EntityLivingBase var3 = (EntityLivingBase) var1.get(var2);
            if (var3.onGround && var3 != this.PB_Master) {
                if (!this.getEntityWorld().isRemote) {
                    DamageSource var4 = DamageSource.causeMobDamage(this.PB_Master);
                    var3.attackEntityFrom(var4, 0);
                }
                var3.addVelocity(var3.motionX, 1.35D * (double) this.expower, var3.motionZ);
            }
        }
        for (var2 = 0; var2 < 200; ++var2) {
            double var11 = this.rand.nextDouble() * 5.0D;
            double var5 = this.rand.nextDouble() * Math.PI * 2.0D;
            double var7 = this.posX + var11 * Math.sin(var5);
            double var9 = this.posZ + var11 * Math.cos(var5);
            this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var7, this.posY, var9, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setEntityDead();
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound nbtTagCompound) {}

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound nbtTagCompound) {}
}
