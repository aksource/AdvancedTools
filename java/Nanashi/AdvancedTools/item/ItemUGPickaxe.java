package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ItemUGPickaxe extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
	public ItemUGPickaxe(ToolMaterial var2, float var3)
	{
		super(2, var2, blocksEffectiveAgainst, var3);
	}

	public ItemUGPickaxe(ToolMaterial var2)
	{
		super(2, var2, blocksEffectiveAgainst, 1.0F);
	}

	@Override
	public boolean func_150897_b(Block var1)
	{
		return this.toolMaterial.getHarvestLevel() >= var1.getHarvestLevel(0);
//		if (var1 == Blocks.obsidian){
//			return this.toolMaterial.getHarvestLevel() == 3;
//		}else if (var1 != Blocks.diamond_block && var1 != Blocks.diamond_ore){
//			if (var1 != Blocks.gold_block && var1 != Blocks.gold_ore){
//				if (var1 != Blocks.iron_block && var1 != Blocks.iron_ore){
//					if (var1 != Blocks.lapis_block && var1 != Blocks.lapis_ore){
//						if (var1 != Blocks.redstone_ore && var1 != Blocks.lit_redstone_ore){
//                            return var1.getMaterial() == Material.rock || blocksEffectiveAgainst.contains(var1) || var1.getMaterial() == Material.iron;
//						}else{
//							return this.toolMaterial.getHarvestLevel() >= 2;
//						}
//					}else{
//						return this.toolMaterial.getHarvestLevel() >= 1;
//					}
//				}else{
//					return this.toolMaterial.getHarvestLevel() >= 1;
//				}
//			}else{
//				return this.toolMaterial.getHarvestLevel() >= 2;
//			}
//		}else{
//			return this.toolMaterial.getHarvestLevel() >= 2;
//		}
	}
	@Override
	public float getDigSpeed(ItemStack var1, Block var2, int meta)
	{
		return var2 != null && (var2.getMaterial() == Material.iron || var2.getMaterial() == Material.rock) ? this.efficiencyOnProperMaterial : super.getDigSpeed(var1, var2, meta);
	}

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("pickaxe");
    }

    public boolean doChainDestruction(Block var1)
	{
		return checkArrays(var1, AdvancedTools.addBlockForPickaxe) && this.func_150897_b(var1);
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
	}
}
