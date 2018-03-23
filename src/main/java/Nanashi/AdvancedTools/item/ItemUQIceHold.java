package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_IHFrozenMob;
import Nanashi.AdvancedTools.utils.AdvToolsUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemUQIceHold extends ItemUniqueArms {
    private int coolTime;
    private int dmg;

    public ItemUQIceHold(ToolMaterial toolMaterial, int damageValue) {
        super(toolMaterial);
        this.dmg = damageValue;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        int weapondmg = entity instanceof EntityEnderman ? dmg * 3 : dmg;
        ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, (float) weapondmg, 0);
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHand) {
        super.onUpdate(stack, world, entity, slot, isHand);

        if (this.coolTime > 0) {
            --this.coolTime;
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int foodLevel = getFoodStat(entityLiving);

        if (foodLevel > 6) {
            int itemDuration = this.getMaxItemUseDuration(itemStack) - timeLeft;
            float itemDurationSecond = (float) itemDuration / 20.0F;
            itemDurationSecond = (itemDurationSecond * itemDurationSecond + itemDurationSecond * 2.0F) / 3.0F;
            BlockPos blockPos;
            if (itemDurationSecond >= 1.0F) {

                blockPos = new BlockPos(entityLiving.posX, entityLiving.posY - 2, entityLiving.posZ);
                if (entityLiving instanceof EntityPlayer && !worldIn.canMineBlockBody((EntityPlayer) entityLiving, blockPos)) {
                    return;
                }

                for (int dy = -1; dy <= 1; ++dy) {
                    for (int dx = -6; dx <= 6; ++dx) {
                        for (int dz = -6; dz <= 6; ++dz) {
                            setSnowLayer(worldIn, blockPos, dx, dy, dz);
                        }
                    }
                }

                List<EntityLiving> entityLivings = worldIn.getEntitiesWithinAABB(EntityLiving.class, entityLiving.getEntityBoundingBox().expand(5.0D, 1.0D, 5.0D));

                for (EntityLiving living : entityLivings) {
                    DamageSource var24 = DamageSource.causeMobDamage(entityLiving);

                    if (living instanceof EntityEnderman) {
                        living.attackEntityFrom(var24, this.dmg * 3);
                    } else {
                        living.attackEntityFrom(var24, 0);
                        Entity_IHFrozenMob var15 = new Entity_IHFrozenMob(worldIn, living, entityLiving);

                        if (!worldIn.isRemote) {
                            worldIn.spawnEntity(var15);
                        }
                    }
                }
            } else {
                RayTraceResult mousePoint = AdvToolsUtil.getMousePoint(worldIn, entityLiving);
                boolean var17 = false;

                if (mousePoint != null && mousePoint.typeOfHit == RayTraceResult.Type.ENTITY) {
                    Entity entityHit = mousePoint.entityHit;

                    if (entityHit instanceof EntityLiving) {
                        DamageSource var20 = DamageSource.causeMobDamage(entityLiving);

                        if (entityHit instanceof EntityEnderman) {
                            entityHit.attackEntityFrom(var20, this.dmg * 3);
                        } else {
                            entityHit.attackEntityFrom(var20, 0);
                            Entity_IHFrozenMob var22 = new Entity_IHFrozenMob(worldIn, (EntityLiving) entityHit, entityLiving);

                            if (!worldIn.isRemote) {
                                worldIn.spawnEntity(var22);
                            }
                        }

                        var17 = true;
                    }
                }

                if (!var17) {
                    if (mousePoint == null) {
                        return;
                    }

                    if (mousePoint.typeOfHit == RayTraceResult.Type.BLOCK) {
                        blockPos = mousePoint.getBlockPos();
                        for (int dx = -3; dx <= 3; ++dx) {
                            for (int dz = -3; dz <= 3; ++dz) {
                                setSnowLayer(worldIn, blockPos, dx, 0, dz);
                            }
                        }
                    }
                }
            }

            itemStack.damageItem(1, entityLiving);

            if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
                ((EntityPlayer) entityLiving).getFoodStats().addStats(-1, 1.0f);
            }

            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
            entityLiving.swingArm(EnumHand.MAIN_HAND);
        }
    }

    /**
     * 雪タイル設置処理
     * @param world World
     * @param centerPos BlockPos
     * @param x x
     * @param y y
     * @param z z
     */
    private void setSnowLayer(World world, BlockPos centerPos, double x, double y, double z) {
        BlockPos blockPos2 = new BlockPos(centerPos).add(x, y, z);
        if (Math.sqrt(x * x + z * z) <= 2.8D && world.isAirBlock(blockPos2.up())) {
            world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                    centerPos.getX() + x,
                    centerPos.getY() + y + 1,
                    centerPos.getZ() + z,
                    0.0D, 0.0D, 0.0D);
            Material blockMaterial = world.getBlockState(blockPos2).getMaterial();

            if (blockMaterial == Material.WATER && (world.getBlockState(centerPos).getValue(BlockLiquid.LEVEL)) == 0) {
                world.setBlockState(blockPos2, Blocks.ICE.getDefaultState());
            } else if (world.getBlockState(blockPos2).getBlock() == Blocks.SNOW_LAYER) {
                if ((world.getBlockState(blockPos2).getValue(BlockSnow.LAYERS)) != 8) {
                    int nextValue = world.getBlockState(blockPos2).getValue(BlockSnow.LAYERS) + 1;
                    world.setBlockState(blockPos2, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, nextValue));
                } else {
                    world.setBlockState(blockPos2, Blocks.SNOW.getDefaultState());
                }
            } else if (world.getBlockState(blockPos2.up()).getBlock() == Blocks.AIR
                    && Blocks.SNOW_LAYER.canPlaceBlockAt(world, blockPos2.up())) {
                world.setBlockState(blockPos2.up(), Blocks.SNOW_LAYER.getDefaultState());
            }
        }
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.BOW;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Ability : Ice Coffin");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        return setActiveHand(worldIn, playerIn, hand);
    }
}
