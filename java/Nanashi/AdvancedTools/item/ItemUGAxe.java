package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ItemUGAxe extends ItemUGTool {
    private static final String TOOL_KIND = "axe";
    private static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
    private static final Set<Material> materialEffectiveAgainst = new HashSet<>();

    static {
        blocksEffectiveAgainst.add(Blocks.PLANKS);
        blocksEffectiveAgainst.add(Blocks.BOOKSHELF);
        blocksEffectiveAgainst.add(Blocks.LOG);
        blocksEffectiveAgainst.add(Blocks.LOG2);
        blocksEffectiveAgainst.add(Blocks.CHEST);
        blocksEffectiveAgainst.add(Blocks.DOUBLE_WOODEN_SLAB);
        blocksEffectiveAgainst.add(Blocks.WOODEN_SLAB);
        blocksEffectiveAgainst.add(Blocks.PUMPKIN);
        blocksEffectiveAgainst.add(Blocks.LIT_PUMPKIN);
        blocksEffectiveAgainst.add(Blocks.ACACIA_FENCE);
        blocksEffectiveAgainst.add(Blocks.ACACIA_FENCE_GATE);
        blocksEffectiveAgainst.add(Blocks.BIRCH_FENCE);
        blocksEffectiveAgainst.add(Blocks.BIRCH_FENCE_GATE);
        blocksEffectiveAgainst.add(Blocks.DARK_OAK_FENCE);
        blocksEffectiveAgainst.add(Blocks.DARK_OAK_FENCE_GATE);
        blocksEffectiveAgainst.add(Blocks.JUNGLE_FENCE);
        blocksEffectiveAgainst.add(Blocks.JUNGLE_FENCE_GATE);
        blocksEffectiveAgainst.add(Blocks.OAK_FENCE);
        blocksEffectiveAgainst.add(Blocks.OAK_FENCE_GATE);
        blocksEffectiveAgainst.add(Blocks.SPRUCE_FENCE);
        blocksEffectiveAgainst.add(Blocks.SPRUCE_FENCE_GATE);

        materialEffectiveAgainst.add(Material.WOOD);
        materialEffectiveAgainst.add(Material.CACTUS);
    }

    public ItemUGAxe(ToolMaterial toolMaterial, float damage, float speed, float durationModifier) {
        super(damage, speed, toolMaterial, blocksEffectiveAgainst, durationModifier, TOOL_KIND);
        setHarvestLevel(TOOL_KIND, toolMaterial.getHarvestLevel());
    }

    @Override
    public float getStrVsBlock(@Nonnull ItemStack itemStack, @Nonnull IBlockState state) {
        return (state.getMaterial() == Material.WOOD
                || state.getMaterial() == Material.PLANTS
                || state.getMaterial() == Material.VINE)
                ? this.efficiencyOnProperMaterial : super.getStrVsBlock(itemStack, state);
    }

    @Override
    public boolean doChainDestruction(IBlockState state) {
        return checkArrays(state.getBlock(), AdvancedTools.addBlockForAxe)
                && (blocksEffectiveAgainst.contains(state.getBlock()) || materialEffectiveAgainst.contains(state.getMaterial()));
    }

    @Override
    public boolean isProperTool(IBlockState state) {
        return Optional.ofNullable(state.getBlock().getHarvestTool(state)).orElse("").equals("axe");
    }

    @Override
    public int getConnectedDistance() {
        return 3;
    }

    public boolean ignoreMeta() {
        return true;
    }

    /*@Override
    protected ArrayList<BlockPos> searchAroundBlock(World world, BlockPos var1, BlockPos minChunkPos, BlockPos maxChunkPos, IBlockState var4, ItemStack var5, EntityPlayer var6) {
        ArrayList<BlockPos> var7 = new ArrayList<BlockPos>();
        BlockPos[] var8 = new BlockPos[17];

        if (var1.getY() < maxChunkPos.getY()) {
            var8[0] = new BlockPos(var1).add(0, 1, 0);
        }

        if (var1.getZ() > minChunkPos.getZ()) {
            var8[1] = new BlockPos(var1).add(0, 0, -1);
        }

        if (var1.getZ() < maxChunkPos.getZ()) {
            var8[2] = new BlockPos(var1.getX(), var1.getY(), var1.getZ() + 1).add(0, 0, 1);
        }

        if (var1.getX() > minChunkPos.getX()) {
            var8[3] = new BlockPos(var1).add(-1, 0, 0);
        }

        if (var1.getX() < maxChunkPos.getX()) {
            var8[4] = new BlockPos(var1).add(1, 0, 0);
        }

        if (checkArrays(var4.getBlock(), AdvancedTools.addBlockForAxe)) {
            if (var1.getZ() > minChunkPos.getZ() && var1.getX() > minChunkPos.getX()) {
                var8[5] = new BlockPos(var1).add(-1, 0, -1);
            }

            if (var1.getZ() < maxChunkPos.getZ() && var1.getX() > minChunkPos.getX()) {
                var8[6] = new BlockPos(var1).add(-1, 0, 1);
            }

            if (var1.getZ() > minChunkPos.getZ() && var1.getX() < maxChunkPos.getX()) {
                var8[7] = new BlockPos(var1).add(1, 0, -1);
            }

            if (var1.getZ() < maxChunkPos.getZ() && var1.getX() < maxChunkPos.getX()) {
                var8[8] = new BlockPos(var1).add(1, 0, 1);
            }

            if (var1.getY() < maxChunkPos.getY()) {
                if (var1.getZ() > minChunkPos.getZ()) {
                    var8[13] = new BlockPos(var1).add(0, 1, -1);
                }

                if (var1.getZ() < maxChunkPos.getZ()) {
                    var8[14] = new BlockPos(var1).add(0, 1, 1);
                }

                if (var1.getX() > minChunkPos.getX()) {
                    var8[15] = new BlockPos(var1).add(-1, 1, 0);
                }

                if (var1.getX() < maxChunkPos.getX()) {
                    var8[16] = new BlockPos(var1).add(1, 1, 0);
                }

                if (var1.getZ() > minChunkPos.getZ() && var1.getX() > minChunkPos.getX()) {
                    var8[9] = new BlockPos(var1).add(-1, 1, -1);
                }

                if (var1.getZ() < maxChunkPos.getZ() && var1.getX() > minChunkPos.getX()) {
                    var8[10] = new BlockPos(var1).add(-1, 1, 1);
                }

                if (var1.getZ() > minChunkPos.getZ() && var1.getX() < maxChunkPos.getX()) {
                    var8[11] = new BlockPos(var1).add(1, 1, -1);
                }

                if (var1.getZ() < maxChunkPos.getZ() && var1.getX() < maxChunkPos.getX()) {
                    var8[12] = new BlockPos(var1).add(1, 1, 1);
                }
            }
        } else if (var1.getY() > minChunkPos.getY()) {
            var8[5] = new BlockPos(var1).add(0, -1, 0);
        }

        for (int var9 = 0; var9 < 17; ++var9) {
            if (var8[var9] != null && this.destroyBlock(world, var8[var9], var4, var5, var6)) {
                var7.add(var8[var9]);
            }
        }

        return var7;
    }*/
}
