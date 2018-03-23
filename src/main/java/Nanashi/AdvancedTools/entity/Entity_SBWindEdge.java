package Nanashi.AdvancedTools.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class Entity_SBWindEdge extends Entity {
    public boolean doesArrowBelongToPlayer;
    public Entity FB_Master;
    private int xTile;
    private int yTile;
    private int zTile;
    private boolean inGround;
    private int ticksInAir;

    public Entity_SBWindEdge(World var1) {
        super(var1);
        this.init();
    }

    public Entity_SBWindEdge(World var1, EntityLivingBase var2, float var3) {
        super(var1);
        this.init();
        this.doesArrowBelongToPlayer = false;
        this.FB_Master = var2;
        this.doesArrowBelongToPlayer = var2 instanceof EntityPlayer;
        this.setLocationAndAngles(var2.posX, var2.posY + (double) var2.getEyeHeight(), var2.posZ, var2.rotationYaw, var2.rotationPitch);
        this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.08F);
        this.posY -= 0.3D;
        this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.08F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        this.setHeading(this.motionX, this.motionY, this.motionZ, 1.8F, 1.0F);
    }

    private void init() {
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inGround = false;
        this.ticksInAir = 0;
        this.setSize(0.5F, 0.5F);
    }

    public void setEntityDead() {
        super.setDead();

        for (int var1 = 0; var1 < 10; ++var1) {
            this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    protected void entityInit() {}

    public void setHeading(double var1, double var3, double var5, float var7, float var8) {
        float var9 = MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
        var1 /= (double) var9;
        var3 /= (double) var9;
        var5 /= (double) var9;
        var1 += this.rand.nextGaussian() * 0.007499999832361937D * (double) var8;
        var3 += this.rand.nextGaussian() * 0.007499999832361937D * (double) var8;
        var5 += this.rand.nextGaussian() * 0.007499999832361937D * (double) var8;
        var1 *= (double) var7;
        var3 *= (double) var7;
        var5 *= (double) var7;
        this.motionX = var1;
        this.motionY = var3;
        this.motionZ = var5;
        float var10 = MathHelper.sqrt(var1 * var1 + var5 * var5);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(var1, var5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(var3, (double) var10) * 180.0D / Math.PI);
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.isBurning()) {
            this.setEntityDead();
        } else {
            if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
                float var1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
                this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var1) * 180.0D / Math.PI);
            }

            BlockPos blockPos = new BlockPos(this.xTile, this.yTile, this.zTile);
            IBlockState blockState = this.getEntityWorld().getBlockState(blockPos);

            AxisAlignedBB var2 = blockState.getBlock().getCollisionBoundingBox(blockState, this.getEntityWorld(), blockPos);

            if (var2 != null && var2.contains(new Vec3d(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
            if (!this.inGround && this.ticksInAir <= 15) {
                ++this.ticksInAir;
                Vec3d startPosition = new Vec3d(this.posX, this.posY, this.posZ);
                Vec3d movedPosition = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                RayTraceResult rayTraceResult = this.getEntityWorld().rayTraceBlocks(startPosition, movedPosition, false, true, false);
                movedPosition = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

                if (rayTraceResult != null) {
                    movedPosition = new Vec3d(rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z);
                }

                Entity var5 = null;
                List<Entity> var6 = this.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().contract(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
                double var7 = 0.0D;
                if (!this.getEntityWorld().isRemote) {
                    for (Entity entity : var6) {
                        if (entity.canBeCollidedWith() && (entity != this.FB_Master || this.ticksInAir >= 5)) {
                            float var11 = 0.3F;
                            AxisAlignedBB var12 = entity.getEntityBoundingBox().expand((double) var11, (double) var11, (double) var11);
                            RayTraceResult var13 = var12.calculateIntercept(startPosition, movedPosition);

                            if (var13 != null) {
                                double var14 = startPosition.distanceTo(var13.hitVec);

                                if (var14 < var7 || var7 == 0.0D) {
                                    var5 = entity;
                                    var7 = var14;
                                }
                            }
                        }
                    }
                }
                if (var5 != null) {
                    rayTraceResult = new RayTraceResult(var5);
                }

                if (rayTraceResult != null) {
                    float var33;

                    if (rayTraceResult.entityHit != null) {
                        DamageSource damageSource;

                        if (this.FB_Master == null) {
                            damageSource = DamageSource.causeThrownDamage(this, this);
                        } else {
                            damageSource = DamageSource.causeThrownDamage(this, this.FB_Master);
                        }

                        Entity hitEntity = rayTraceResult.entityHit;
                        hitEntity.attackEntityFrom(damageSource, 2);
                        hitEntity.addVelocity(this.motionX * 0.8d, hitEntity.motionY * 1.414D, this.motionZ * 0.8d);
                        this.setEntityDead();
                    } else {
                        this.xTile = rayTraceResult.getBlockPos().getX();
                        this.yTile = rayTraceResult.getBlockPos().getY();
                        this.zTile = rayTraceResult.getBlockPos().getZ();
                        this.motionX = (double) ((float) (rayTraceResult.hitVec.x - this.posX));
                        this.motionY = (double) ((float) (rayTraceResult.hitVec.y - this.posY));
                        this.motionZ = (double) ((float) (rayTraceResult.hitVec.z - this.posZ));
                        var33 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                        this.posX -= this.motionX / (double) var33 * 0.05000000074505806D;
                        this.posY -= this.motionY / (double) var33 * 0.05000000074505806D;
                        this.posZ -= this.motionZ / (double) var33 * 0.05000000074505806D;
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

                for (var15 = 0; var15 < 10; ++var15) {
                    float var16 = 0.1F * (float) var15;
                    this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + var35 * (double) var16, this.posY + 0.5D + var37 * (double) var16, this.posZ + var38 * (double) var16, 0.0D, 0.0D, 0.0D);
                }

                for (var15 = 0; var15 < 40; ++var15) {
                    double var40 = (double) this.rotationYaw / 180.0D * Math.PI;
                    double var18 = (double) (-this.rotationPitch) / 180.0D * Math.PI;
                    float var20 = 0.157F * (float) var15;
                    double var21 = 0.25D * Math.cos((double) var20);
                    double var23 = 0.25D * -Math.sin((double) var20);
                    double var25 = var21 * Math.cos(var40) + var23 * Math.sin(var18) * Math.sin(var40);
                    double var27 = var23 * Math.cos(var18);
                    double var29 = -var21 * Math.sin(var40) + var23 * Math.sin(var18) * Math.cos(var40);
                    this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY + 0.5D, this.posZ, var25, var27, var29);
                }

                float var39 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

//				for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var39) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
//				{
//					;
//				}

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

                if (this.isInWater() || this.isWet()) {
                    this.setEntityDead();
                }

                this.setPosition(this.posX, this.posY, this.posZ);
            } else {
                this.setEntityDead();
            }
        }
    }

    public void writeEntityToNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short) this.xTile);
        nbtTagCompound.setShort("yTile", (short) this.yTile);
        nbtTagCompound.setShort("zTile", (short) this.zTile);
        nbtTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbtTagCompound.setBoolean("attacker", this.doesArrowBelongToPlayer);
    }

    public void readEntityFromNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        this.inGround = nbtTagCompound.getByte("inGround") == 1;
        this.doesArrowBelongToPlayer = nbtTagCompound.getBoolean("attacker");
    }

    public void onCollideWithPlayer(EntityPlayer var1) {}
}
