package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ItemUGPickaxe extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
    public static final Set<Material> materialEffectiveAgainst = new HashSet<>();
	public ItemUGPickaxe(ToolMaterial var2, float var3)
	{
		super(2, var2, blocksEffectiveAgainst, var3);
        setHarvestLevel("pickaxe", var2.getHarvestLevel());
	}

    @Override
    public boolean canHarvestBlock(Block var1, ItemStack itemStack) {
        return materialEffectiveAgainst.contains(var1.getMaterial());
    }

	@Override
	public float getDigSpeed(ItemStack var1, IBlockState state) {
		return state.getBlock() != null && (materialEffectiveAgainst.contains(state.getBlock().getMaterial())) ? this.efficiencyOnProperMaterial : super.getDigSpeed(var1, state);
	}

    @Override
    public boolean doChainDestruction(IBlockState state) {
		return checkArrays(state.getBlock(), AdvancedTools.addBlockForPickaxe)
                && (isProperTool(state) && this.toolMaterial.getHarvestLevel() >= state.getBlock().getHarvestLevel(state));
	}

    @Override
    public boolean isProperTool(IBlockState state) {
        return Optional.fromNullable(state.getBlock().getHarvestTool(state)).or("").equals("pickaxe");
    }

    static{
		blocksEffectiveAgainst.add(Blocks.cobblestone);
		blocksEffectiveAgainst.add(Blocks.double_stone_slab);
		blocksEffectiveAgainst.add(Blocks.stone_slab);
		blocksEffectiveAgainst.add(Blocks.stone);
		blocksEffectiveAgainst.add(Blocks.stained_hardened_clay);
		blocksEffectiveAgainst.add(Blocks.stone_brick_stairs);
		blocksEffectiveAgainst.add(Blocks.stone_button);
		blocksEffectiveAgainst.add(Blocks.stone_pressure_plate);
		blocksEffectiveAgainst.add(Blocks.stone_stairs);
		blocksEffectiveAgainst.add(Blocks.stonebrick);
		blocksEffectiveAgainst.add(Blocks.sandstone);
		blocksEffectiveAgainst.add(Blocks.sandstone_stairs);
		blocksEffectiveAgainst.add(Blocks.mossy_cobblestone);
		blocksEffectiveAgainst.add(Blocks.coal_ore);
		blocksEffectiveAgainst.add(Blocks.coal_block);
		blocksEffectiveAgainst.add(Blocks.iron_ore);
		blocksEffectiveAgainst.add(Blocks.iron_block);
		blocksEffectiveAgainst.add(Blocks.iron_bars);
		blocksEffectiveAgainst.add(Blocks.iron_door);
		blocksEffectiveAgainst.add(Blocks.gold_block);
		blocksEffectiveAgainst.add(Blocks.gold_ore);
		blocksEffectiveAgainst.add(Blocks.diamond_ore);
		blocksEffectiveAgainst.add(Blocks.diamond_block);
		blocksEffectiveAgainst.add(Blocks.ice);
		blocksEffectiveAgainst.add(Blocks.nether_brick);
		blocksEffectiveAgainst.add(Blocks.nether_brick_fence);
		blocksEffectiveAgainst.add(Blocks.nether_brick_stairs);
		blocksEffectiveAgainst.add(Blocks.netherrack);
		blocksEffectiveAgainst.add(Blocks.lapis_block);
		blocksEffectiveAgainst.add(Blocks.lapis_ore);
		blocksEffectiveAgainst.add(Blocks.redstone_ore);
		blocksEffectiveAgainst.add(Blocks.lit_redstone_ore);
		blocksEffectiveAgainst.add(Blocks.redstone_block);
		blocksEffectiveAgainst.add(Blocks.rail);
		blocksEffectiveAgainst.add(Blocks.detector_rail);
		blocksEffectiveAgainst.add(Blocks.golden_rail);
		blocksEffectiveAgainst.add(Blocks.activator_rail);
		blocksEffectiveAgainst.add(Blocks.packed_ice);

        materialEffectiveAgainst.add(Material.rock);
        materialEffectiveAgainst.add(Material.iron);
        materialEffectiveAgainst.add(Material.anvil);
        materialEffectiveAgainst.add(Material.coral);
        materialEffectiveAgainst.add(Material.ice);
        materialEffectiveAgainst.add(Material.packedIce);
	}
}
