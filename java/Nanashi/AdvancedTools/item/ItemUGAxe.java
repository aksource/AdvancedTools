package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ItemUGAxe extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
    public static final Set<Material> materialEffectiveAgainst = new HashSet<>();
	public ItemUGAxe(ToolMaterial var2, float var3)
	{
		super(3, var2, blocksEffectiveAgainst, var3);
        setHarvestLevel("axe", var2.getHarvestLevel());
	}

	@Override
    public float getDigSpeed(ItemStack par1ItemStack, IBlockState state) {
        return state != null && (state.getBlock().getMaterial() == Material.wood || state.getBlock().getMaterial() == Material.plants || state.getBlock().getMaterial() == Material.vine) ? this.efficiencyOnProperMaterial : super.getDigSpeed(par1ItemStack, state);
    }

//    @Override
//    public Set<String> getToolClasses(ItemStack stack) {
//        return ImmutableSet.of("axe");
//    }

    @Override
	public boolean doChainDestruction(IBlockState state) {
		return checkArrays(state.getBlock(), AdvancedTools.addBlockForAxe)
                && (blocksEffectiveAgainst.contains(state.getBlock()) || materialEffectiveAgainst.contains(state.getBlock().getMaterial()));
	}

    @Override
    public boolean isProperTool(IBlockState state) {
        return Optional.fromNullable(state.getBlock().getHarvestTool(state)).or("").equals("axe");
    }

    @Override
	protected ArrayList<BlockPos> searchAroundBlock(World world, BlockPos var1, BlockPos minChunkPos, BlockPos maxChunkPos, IBlockState var4, ItemStack var5, EntityPlayer var6)
	{
		ArrayList<BlockPos> var7 = new ArrayList<BlockPos>();
        BlockPos[] var8 = new BlockPos[17];

		if (var1.getY() < maxChunkPos.getY()){
			var8[0] = new BlockPos(var1).add(0, 1, 0);
		}

		if (var1.getZ() > minChunkPos.getZ()){
			var8[1] = new BlockPos(var1).add(0, 0, -1);
		}

		if (var1.getZ() < maxChunkPos.getZ()){
			var8[2] = new BlockPos(var1.getX(), var1.getY(), var1.getZ() + 1).add(0 , 0, 1);
		}

		if (var1.getX() > minChunkPos.getX()){
			var8[3] = new BlockPos(var1).add(-1, 0, 0);
		}

		if (var1.getX() < maxChunkPos.getX()){
			var8[4] = new BlockPos(var1).add(1, 0, 0);
		}

		if (checkArrays(var4.getBlock(), AdvancedTools.addBlockForAxe)){
			if (var1.getZ() > minChunkPos.getZ() && var1.getX() > minChunkPos.getX()){
				var8[5] = new BlockPos(var1).add(-1, 0, -1);
			}

			if (var1.getZ() < maxChunkPos.getZ() && var1.getX() > minChunkPos.getX()){
				var8[6] = new BlockPos(var1).add(-1, 0, 1);
			}

			if (var1.getZ() > minChunkPos.getZ() && var1.getX() < maxChunkPos.getX()){
				var8[7] = new BlockPos(var1).add(1, 0, -1);
			}

			if (var1.getZ() < maxChunkPos.getZ() && var1.getX() < maxChunkPos.getX()){
				var8[8] = new BlockPos(var1).add(1, 0, 1);
			}

			if (var1.getY() < maxChunkPos.getY()){
				if (var1.getZ() > minChunkPos.getZ()){
					var8[13] = new BlockPos(var1).add(0, 1, -1);
				}

				if (var1.getZ() < maxChunkPos.getZ()){
					var8[14] = new BlockPos(var1).add(0, 1, 1);
				}

				if (var1.getX() > minChunkPos.getX()){
					var8[15] = new BlockPos(var1).add(-1, 1, 0);
				}

				if (var1.getX() < maxChunkPos.getX()){
					var8[16] = new BlockPos(var1).add(1, 1, 0);
				}

				if (var1.getZ() > minChunkPos.getZ() && var1.getX() > minChunkPos.getX()){
					var8[9] = new BlockPos(var1).add(-1, 1, -1);
				}

				if (var1.getZ() < maxChunkPos.getZ() && var1.getX() > minChunkPos.getX()){
					var8[10] = new BlockPos(var1).add(-1, 1, 1);
				}

				if (var1.getZ() > minChunkPos.getZ() && var1.getX() < maxChunkPos.getX()){
					var8[11] = new BlockPos(var1).add(1, 1, -1);
				}

				if (var1.getZ() < maxChunkPos.getZ() && var1.getX() < maxChunkPos.getX()){
					var8[12] = new BlockPos(var1).add(1, 1, 1);
				}
			}
		}else if (var1.getY() > minChunkPos.getY()){
			var8[5] = new BlockPos(var1).add(0, -1, 0);
		}

		for (int var9 = 0; var9 < 17; ++var9){
			if (var8[var9] != null && this.destroyBlock(world,var8[var9], var4, var5, var6)){
				var7.add(var8[var9]);
			}
		}

		return var7;
	}
	static{
		blocksEffectiveAgainst.add(Blocks.planks);
		blocksEffectiveAgainst.add(Blocks.bookshelf);
		blocksEffectiveAgainst.add(Blocks.log);
		blocksEffectiveAgainst.add(Blocks.log2);
		blocksEffectiveAgainst.add(Blocks.chest);
		blocksEffectiveAgainst.add(Blocks.double_wooden_slab);
		blocksEffectiveAgainst.add(Blocks.wooden_slab);
		blocksEffectiveAgainst.add(Blocks.pumpkin);
		blocksEffectiveAgainst.add(Blocks.lit_pumpkin);
        blocksEffectiveAgainst.add(Blocks.acacia_fence);
        blocksEffectiveAgainst.add(Blocks.acacia_fence_gate);
        blocksEffectiveAgainst.add(Blocks.birch_fence);
        blocksEffectiveAgainst.add(Blocks.birch_fence_gate);
        blocksEffectiveAgainst.add(Blocks.dark_oak_fence);
        blocksEffectiveAgainst.add(Blocks.dark_oak_fence_gate);
        blocksEffectiveAgainst.add(Blocks.jungle_fence);
        blocksEffectiveAgainst.add(Blocks.jungle_fence_gate);
        blocksEffectiveAgainst.add(Blocks.oak_fence);
        blocksEffectiveAgainst.add(Blocks.oak_fence_gate);
        blocksEffectiveAgainst.add(Blocks.spruce_fence);
        blocksEffectiveAgainst.add(Blocks.spruce_fence_gate);

        materialEffectiveAgainst.add(Material.wood);
        materialEffectiveAgainst.add(Material.cactus);
	}
}
