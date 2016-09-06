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
//				this.originalMethodDoubleEntity(var1);
                if (this.frozen instanceof EntityMob) {
//					this.altenativeMethodForAI();
                    this.originalMethodForAI();
                }
                this.FrozenRest = 200;
            }
        }
    }

    private void originalMethodForAI() {
        this.entityTasks = this.frozen.tasks;
        ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this.frozen, new EntityAITasks(this.worldObj.theProfiler), 7);
    }

    public void onUpdate() {
        if (!this.worldObj.isRemote) {
            --this.FrozenRest;

            if (this.frozen == null) {
                this.setDead();
            } else if (!this.frozen.isBurning() && this.FrozenRest > 0 && (this.frozen.hurtTime == 0 || this.FrozenRest >= 190)) {
                if (this.frozen.getHealth() > 0 && !(this.frozen instanceof EntityEnderman)) {
                    this.frozen.setPosition(this.posX, this.frozen.posY, this.posZ);
                    this.rotationYaw = this.frozen.rotationYaw;
                    this.frozen.onGround = false;
                    if (this.frozen instanceof EntityMob) {
//						((EntityMob)this.frozen).attackTime = 20;
                        this.frozen.setAttackTarget(this.frozen);
                        this.frozen.setLastAttacker(this.frozen);
                    }
                } else {
                    this.setDead();
                }
            } else {
                this.worldObj.playSound(this.posX, this.posY, this.posZ,
                        SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.VOICE,
                        1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F, false);
                this.setDead();
            }
        } else {
            for (int var1 = 0; var1 < 2; ++var1) {
                double var2 = this.rand.nextDouble() * (double) this.width;
                double var4 = this.rand.nextDouble() * Math.PI * 1.0D;
                double var6 = this.posX + var2 * Math.sin(var4);
                double var8 = this.posY + (double) this.height * this.rand.nextDouble();
                double var10 = this.posZ + var2 * Math.cos(var4);
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public void setDead() {
        if (this.entityTasks != null) {
            ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this.frozen, this.entityTasks, 7);
            this.frozen.setAttackTarget(this.attacker);
            this.frozen.setLastAttacker(this.attacker);
        }
        super.setDead();
    }

    public float getBrightness(float var1) {
        return 1.0f;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    protected void entityInit() {}

    protected void readEntityFromNBT(NBTTagCompound var1) {
        this.FrozenRest = var1.getInteger("frozenRest");
    }

    protected void writeEntityToNBT(NBTTagCompound var1) {
        var1.setInteger("frozenRest", FrozenRest);
    }
}
