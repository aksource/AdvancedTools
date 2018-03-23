package Nanashi.AdvancedTools.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;

public class Entity_IHFrozenMob extends Entity {
    EntityLiving frozen;
    EntityLivingBase attacker;
    EntityAITasks entityTasks;
    int FrozenRest;

    public Entity_IHFrozenMob(World var1) {
        super(var1);
    }

    public Entity_IHFrozenMob(World world, EntityLiving target, EntityLivingBase attacker) {
        super(world);
        if (target == null) {
            this.setDead();
            this.frozen = null;
        } else {
            this.frozen = target;
            this.attacker = attacker;
            this.setSize(this.frozen.width, this.frozen.height);
            this.frozen.dismountRidingEntity();
            this.setPosition(this.frozen.posX, this.frozen.posY, this.frozen.posZ);
            this.rotationYaw = this.frozen.rotationYaw;

            if (this.frozen.isBurning()) {
                this.setDead();
                this.frozen.setFire(0);
                this.frozen = null;
            } else {
                this.frozen.setFire(0);
                if (this.frozen instanceof EntityMob) {
                    this.originalMethodForAI();
                }
                this.FrozenRest = 200;
            }
        }
    }

    private void originalMethodForAI() {
        this.entityTasks = this.frozen.tasks;
        ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this.frozen, new EntityAITasks(this.getEntityWorld().profiler), 7);
    }

    public void onUpdate() {
        if (!this.getEntityWorld().isRemote) {
            --this.FrozenRest;

            if (this.frozen == null) {
                this.setDead();
            } else if (!this.frozen.isBurning() && this.FrozenRest > 0 && (this.frozen.hurtTime == 0 || this.FrozenRest >= 190)) {
                if (this.frozen.getHealth() > 0 && !(this.frozen instanceof EntityEnderman)) {
                    this.frozen.setPosition(this.posX, this.frozen.posY, this.posZ);
                    this.rotationYaw = this.frozen.rotationYaw;
                    this.frozen.onGround = false;
                    if (this.frozen instanceof EntityMob) {
                        this.frozen.setAttackTarget(this.frozen);
                        this.frozen.setLastAttackedEntity(this.frozen);
                    }
                } else {
                    this.setDead();
                }
            } else {
                this.getEntityWorld().playSound(this.posX, this.posY, this.posZ,
                        SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.VOICE,
                        1.0F, this.getEntityWorld().rand.nextFloat() * 0.1F + 0.9F, false);
                this.setDead();
            }
        } else {
            for (int var1 = 0; var1 < 2; ++var1) {
                double var2 = this.rand.nextDouble() * (double) this.width;
                double var4 = this.rand.nextDouble() * Math.PI * 1.0D;
                double var6 = this.posX + var2 * Math.sin(var4);
                double var8 = this.posY + (double) this.height * this.rand.nextDouble();
                double var10 = this.posZ + var2 * Math.cos(var4);
                this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void setDead() {
        if (this.entityTasks != null) {
            ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this.frozen, this.entityTasks, 7);
            this.frozen.setAttackTarget(this.attacker);
            this.frozen.setLastAttackedEntity(this.attacker);
        }
        super.setDead();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        this.FrozenRest = nbtTagCompound.getInteger("frozenRest");
    }

    @Override
    protected void writeEntityToNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger("frozenRest", FrozenRest);
    }
}
