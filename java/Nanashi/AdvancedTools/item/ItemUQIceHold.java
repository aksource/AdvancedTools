package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_IHFrozenMob;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemUQIceHold extends ItemUniqueArms {
    private int coolTime;
    private int dmg;

    public ItemUQIceHold(ToolMaterial var2, int var3) {
        super(var2);
        this.dmg = var3;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity var1) {
        int weapondmg = var1 instanceof EntityEnderman ? dmg * 3 : dmg;
        ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, (float) weapondmg, 0);
        return false;
    }

    @Override
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
        super.onUpdate(var1, var2, var3, var4, var5);

        if (this.coolTime > 0) {
            --this.coolTime;
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int var4) {
        int foodLevel = player.getFoodStats().getFoodLevel();

        if (foodLevel > 6) {
            int var6 = this.getMaxItemUseDuration(itemStack) - var4;
            float var7 = (float) var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
//            int var10;
//            int var11;
//            int var12;
//            int var13;
            BlockPos blockPos;
            if (var7 >= 1.0F) {
//                int var8 = (int) var3.posX;
//                int var9 = (int) var3.posY - 2;
//                var10 = (int) var3.posZ;

                blockPos = new BlockPos(player.posX, player.posY - 2, player.posZ);
                if (!world.canMineBlockBody(player, blockPos)) {
                    return;
                }

                for (int dy = -1; dy <= 1; ++dy) {
                    for (int dx = -6; dx <= 6; ++dx) {
                        for (int dz = -6; dz <= 6; ++dz) {
                            setSnowLayer(world, blockPos, dx, dy, dz);
//                            BlockPos blockPos1 = new BlockPos(blockPos).add(var12, var11, var13);
//                            if (Math.sqrt((double) (var12 * var12 + var13 * var13)) <= 5.8D && var2.isAirBlock(blockPos1.up())) {
//                                var2.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (double) (var8 + var12), (double) (var9 + 1 + var11), (double) (var10 + var13), 0.0D, 0.0D, 0.0D);
//                                Material var14 = var2.getBlockState(blockPos1).getBlock().getMaterial();
//                                if (var14 == Material.water && ((Integer) var2.getBlockState(new BlockPos(blockPos).add(0, var11, 0)).getValue(BlockLiquid.LEVEL)) == 0) {
//                                    var2.setBlockState(blockPos1, Blocks.ice.getDefaultState());
//                                } else if (var2.getBlockState(blockPos1.up()).getBlock() == Blocks.air && Blocks.snow_layer.canPlaceBlockAt(var2, blockPos1.up())) {
//                                    var2.setBlockState(blockPos1.up(), Blocks.snow_layer.getDefaultState());
//                                }
//                            }
                        }
                    }
                }

                @SuppressWarnings("unchecked")
                List<EntityLiving> var19 = world.getEntitiesWithinAABB(EntityLiving.class, player.getEntityBoundingBox().expand(5.0D, 1.0D, 5.0D));

                for (EntityLiving entityLiving : var19) {
                    DamageSource var24 = DamageSource.causePlayerDamage(player);

                    if (entityLiving instanceof EntityEnderman) {
                        entityLiving.attackEntityFrom(var24, this.dmg * 3);
                    } else {
                        entityLiving.attackEntityFrom(var24, 0);
                        Entity_IHFrozenMob var15 = new Entity_IHFrozenMob(world, entityLiving, player);

                        if (!world.isRemote) {
                            world.spawnEntityInWorld(var15);
                        }
                    }
                }
            } else {
                MovingObjectPosition var16 = AdvancedTools.getMousePoint(world, player);
                boolean var17 = false;

                if (var16 != null && var16.typeOfHit == MovingObjectType.ENTITY) {
                    Entity var18 = var16.entityHit;

                    if (var18 instanceof EntityLiving) {
                        DamageSource var20 = DamageSource.causePlayerDamage(player);

                        if (var18 instanceof EntityEnderman) {
                            var18.attackEntityFrom(var20, this.dmg * 3);
                        } else {
                            var18.attackEntityFrom(var20, 0);
                            Entity_IHFrozenMob var22 = new Entity_IHFrozenMob(world, (EntityLiving) var18, player);

                            if (!world.isRemote) {
                                world.spawnEntityInWorld(var22);
                            }
                        }

                        var17 = true;
                    }
                }

                if (!var17) {
                    var16 = this.getMovingObjectPositionFromPlayer(world, player, true);

                    if (var16 == null) {
                        return;
                    }

                    if (var16.typeOfHit == MovingObjectType.BLOCK) {
//                        var10 = var16.getBlockPos().getX();
//                        var11 = var16.getBlockPos().getY();
//                        var12 = var16.getBlockPos().getZ();
                        blockPos = var16.getBlockPos();
                        for (int dx = -3; dx <= 3; ++dx) {
                            for (int dz = -3; dz <= 3; ++dz) {
                                setSnowLayer(world, blockPos, dx, 0, dz);
//                                BlockPos blockPos2 = new BlockPos(blockPos).add(var13, 0, var25);
//                                if (Math.sqrt((double) (var13 * var13 + var25 * var25)) <= 2.8D && var2.isAirBlock(blockPos2.up())) {
//                                    var2.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (double) (var10 + var13), (double) (var11 + 1), (double) (var12 + var25), 0.0D, 0.0D, 0.0D);
//                                    Material var23 = var2.getBlockState(blockPos2).getBlock().getMaterial();
//                                    BlockPos blockPos3 = new BlockPos(blockPos).add(var13, 0, var25);
//
//                                    if (var23 == Material.water && ((Integer) var2.getBlockState(blockPos).getValue(BlockLiquid.LEVEL)) == 0) {
//                                        var2.setBlockState(blockPos3, Blocks.ice.getDefaultState());
//                                    } else if (var2.getBlockState(blockPos3.up()).getBlock() == Blocks.air && Blocks.snow_layer.canPlaceBlockAt(var2, blockPos3.up())) {
//                                        var2.setBlockState(blockPos3.up(), Blocks.snow_layer.getDefaultState());
//                                    }
//                                }
                            }
                        }
                    }
                }
            }

            itemStack.damageItem(1, player);

            if (!player.capabilities.isCreativeMode) {
                player.getFoodStats().addStats(-1, 1.0f);
            }

            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
            player.swingItem();
        }
    }

    private void setSnowLayer(World world, BlockPos centerPos, double x, double y, double z) {
        BlockPos blockPos2 = new BlockPos(centerPos).add(x, y, z);
        if (Math.sqrt(x * x + z * z) <= 2.8D && world.isAirBlock(blockPos2.up())) {
            world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                    centerPos.getX() + x,
                    centerPos.getY() + y + 1,
                    centerPos.getZ() + z,
                    0.0D, 0.0D, 0.0D);
            Material blockMaterial = world.getBlockState(blockPos2).getBlock().getMaterial();

            if (blockMaterial == Material.water && ((Integer) world.getBlockState(centerPos).getValue(BlockLiquid.LEVEL)) == 0) {
                world.setBlockState(blockPos2, Blocks.ice.getDefaultState());
            } else if (world.getBlockState(blockPos2).getBlock() == Blocks.snow_layer) {
                if (((Integer) world.getBlockState(blockPos2).getValue(BlockSnow.LAYERS)) != 8) {
                    int nextValue = (Integer) world.getBlockState(blockPos2).getValue(BlockSnow.LAYERS) + 1;
                    world.setBlockState(blockPos2, Blocks.snow_layer.getDefaultState().withProperty(BlockSnow.LAYERS, nextValue));
                } else {
                    world.setBlockState(blockPos2, Blocks.snow.getDefaultState());
                }
            } else if (world.getBlockState(blockPos2.up()).getBlock() == Blocks.air
                    && Blocks.snow_layer.canPlaceBlockAt(world, blockPos2.up())) {
                world.setBlockState(blockPos2.up(), Blocks.snow_layer.getDefaultState());
            }
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack var1) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("Ability : Ice Coffin");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        int var4 = var3.getFoodStats().getFoodLevel();

        if (var4 > 6) {
            var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
        }

        return var1;
    }
}
