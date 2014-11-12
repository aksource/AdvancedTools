package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

//	public ItemUGPickaxe(ToolMaterial var2)
//	{
//		super(2, var2, blocksEffectiveAgainst, 1.0F);
//	}

    @Override
    public boolean canHarvestBlock(Block var1, ItemStack itemStack) {
        return materialEffectiveAgainst.contains(var1.getMaterial());
    }

	@Override
	public float getDigSpeed(ItemStack var1, Block var2, int meta) {
		return var2 != null && (materialEffectiveAgainst.contains(var2.getMaterial())) ? this.efficiencyOnProperMaterial : super.getDigSpeed(var1, var2, meta);
	}

//    @Override
//    public Set<String> getToolClasses(ItemStack stack) {
//        return ImmutableSet.of("pickaxe");
//    }

    @Override
    public boolean doChainDestruction(Block var1, int var2) {
		return checkArrays(var1, AdvancedTools.addBlockForPickaxe)
                && (isProperTool(var1, var2) && this.toolMaterial.getHarvestLevel() >= var1.getHarvestLevel(var2));
	}

    @Override
    public boolean isProperTool(Block block, int meta) {
        return Optional.fromNullable(block.getHarvestTool(meta)).or("").equals("pickaxe");
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
