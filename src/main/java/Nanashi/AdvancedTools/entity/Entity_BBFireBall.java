package Nanashi.AdvancedTools.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class Entity_BBFireBall extends Entity {
    public boolean doesArrowBelongToPlayer;
    public Entity FB_Master;
    private int xTile;
    private int yTile;
    private int zTile;
    private boolean inGround;
    private boolean explodeFlag;
    private int ticksInAir;
    private float expower;

    public Entity_BBFireBall(World world) {
        super(world);
        this.init();
    }

    public Entity_BBFireBall(World world, EntityLivingBase entityLivingBase, float var3, boolean var4) {
        super(world);
        this.init();
        this.explodeFlag = var4;
        this.doesArrowBelongToPlayer = false;
        this.FB_Master = entityLivingBase;
        this.doesArrowBelongToPlayer = entityLivingBase instanceof EntityPlayer;
        this.setLocationAndAngles(entityLivingBase.posX, entityLivingBase.posY + (double) entityLivingBase.getEyeHeight(), entityLivingBase.posZ, entityLivingBase.rotationYaw, entityLivingBase.rotationPitch);
        this.posX -= (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.08F);
        this.posY -= 0.12D;
        this.posZ -= (double) (MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.08F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionZ = (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
        this.motionY = (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
        this.setArrowHeading(this.motionX, this.motionY, this.motionZ, 1.8F, 1.0F);
    }

    private void init() {
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inGround = false;
        this.explodeFlag = false;
        this.ticksInAir = 0;
        this.expower = 1.5F;
        this.setSize(0.5F, 0.5F);
    }

    public void setEntityDead() {
        super.setDead();
        float var1 = this.explodeFlag ? this.expower * 1.2F : 1.5F;

        if (!this.isWet()) {
            List var2 = this.getEntityWorld().getEntitiesWithinAABB(EntityLiving.class, this.getEntityBoundingBox().expand((double) var1, (double) var1, (double) var1));
            int var3;

            for (var3 = 0; var3 < var2.size(); ++var3) {
                EntityLiving var4 = (EntityLiving) var2.get(var3);
                var4.setFire(5);
            }

            BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);

            if (this.getEntityWorld().getBlockState(blockPos).getBlock() == Blocks.AIR && Blocks.FIRE.canPlaceBlockAt(this.getEntityWorld(), blockPos)) {
                this.getEntityWorld().setBlockState(blockPos, Blocks.FIRE.getDefaultState());
            }

            for (int var6 = 0; var6 < 4; ++var6) {
                blockPos = new BlockPos(this.posX + this.rand.nextInt(3) - 1, this.posY + this.rand.nextInt(3) - 1, this.posZ + this.rand.nextInt(3) - 1);

                if (this.getEntityWorld().getBlockState(blockPos).getBlock() == Blocks.AIR && Blocks.FIRE.canPlaceBlockAt(this.getEntityWorld(), blockPos)) {
                    this.getEntityWorld().setBlockState(blockPos, Blocks.FIRE.getDefaultState());
                }
            }

            if (this.explodeFlag) {
                this.getEntityWorld().createExplosion(null, this.posX, this.posY, this.posZ, this.expower, true);
            } else {
                this.getEntityWorld().createExplosion(null, this.posX, this.posY, this.posZ, 0.0F, true);
            }
        } else {
            for (int var7 = 0; var7 < 10; ++var7) {
                this.getEntityWorld().spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void entityInit() {}

    public void setArrowHeading(double var1, double var3, double var5, float var7, float var8) {
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
        float entity = MathHelper.sqrt(var1 * var1 + var5 * var5);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(var1, var5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(var3, (double) entity) * 180.0D / Math.PI);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setFire(1);

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float var1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var1) * 180.0D / Math.PI);
        }

        BlockPos blockPos = new BlockPos(this.xTile, this.yTile, this.zTile);

        IBlockState blockState = this.getEntityWorld().getBlockState(blockPos);

        if (blockState.getBlock() != Blocks.AIR) {
            AxisAlignedBB var2 = blockState.getBoundingBox(this.getEntityWorld(), blockPos);

            if (var2 != null && var2.contains(new Vec3d(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }

        if (!this.inGround && this.ticksInAir <= 15) {
            ++this.ticksInAir;
            Vec3d var18 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult rayTraceResult = this.getEntityWorld().rayTraceBlocks(var18, var3, false, true, false);
            var18 = new Vec3d(this.posX, this.posY, this.posZ);
            var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (rayTraceResult != null) {
                var3 = new Vec3d(rayTraceResult.hitVec.x, rayTraceResult.hitVec.y, rayTraceResult.hitVec.z);
            }

            Entity var5 = null;
            List<Entity> var6 = this.getEntityWorld().getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().contract(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double var7 = 0.0D;
            if (!this.getEntityWorld().isRemote) {
                for (Entity entityTmp : var6) {

                    if (entityTmp.canBeCollidedWith() && (entityTmp != this.FB_Master || this.ticksInAir >= 5)) {
                        float var11 = 0.3F;
                        AxisAlignedBB var12 = entityTmp.getEntityBoundingBox().expand((double) var11, (double) var11, (double) var11);
                        RayTraceResult var13 = var12.calculateIntercept(var18, var3);

                        if (var13 != null) {
                            double var14 = var18.distanceTo(var13.hitVec);

                            if (var14 < var7 || var7 == 0.0D) {
                                var5 = entityTmp;
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
                float var19;

                if (rayTraceResult.entityHit != null) {
                    DamageSource var22;

                    if (this.FB_Master == null) {
                        var22 = DamageSource.causeThrownDamage(this, this);
                    } else {
                        var22 = DamageSource.causeThrownDamage(this, this.FB_Master);
                    }

                    rayTraceResult.entityHit.setFire(5);
                    rayTraceResult.entityHit.attackEntityFrom(var22, 2 + (this.explodeFlag ? 4 : 0));
                    this.setEntityDead();
                } else {
                    this.xTile = rayTraceResult.getBlockPos().getX();//BlockPosから取得
                    this.yTile = rayTraceResult.getBlockPos().getY();
                    this.motionX = (double) ((float) (rayTraceResult.hitVec.x - this.posX));
                    this.motionY = (double) ((float) (rayTraceResult.hitVec.y - this.posY));
                    this.motionZ = (double) ((float) (rayTraceResult.hitVec.z - this.posZ));
                    var19 = MathHelper.sqrt(
                            this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double) var19 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double) var19 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double) var19 * 0.05000000074505806D;
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

            for (int var15 = 0; var15 < 10; ++var15) {
                float var16 = 0.1F * (float) var15;
                this.getEntityWorld().spawnParticle(
                        EnumParticleTypes.SMOKE_NORMAL,
                        this.posX + var21 * (double) var16,
                        this.posY + 0.5D + var20 * (double) var16,
                        this.posZ + var23 * (double) var16, 0.0D, 0.0D, 0.0D);

                if (this.explodeFlag) {
                    this.getEntityWorld().spawnParticle(
                            EnumParticleTypes.REDSTONE,
                            this.posX + var21 * (double) var16,
                            this.posY + 0.5D + var20 * (double) var16,
                            this.posZ + var23 * (double) var16, 0.0D, 0.0D, 0.0D);
                }
            }

            float var24 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float) (Math.atan2(this.motionY,
                    (double) var24) * 180.0D / Math.PI);
                 this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
                ;
            }

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

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short) this.xTile);
        nbtTagCompound.setShort("yTile", (short) this.yTile);
        nbtTagCompound.setShort("zTile", (short) this.zTile);
        nbtTagCompound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbtTagCompound.setBoolean("attacker", this.doesArrowBelongToPlayer);
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        this.inGround = nbtTagCompound.getByte("inGround") == 1;
        this.doesArrowBelongToPlayer = nbtTagCompound.getBoolean("attacker");
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {}
}
