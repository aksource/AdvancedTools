package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.HashSet;
import java.util.Set;

public class ItemUGShovel extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
    public static final Set<Material> materialEffectiveAgainst = new HashSet<>();
	public ItemUGShovel(ToolMaterial var2, float var3)
	{
		super(1, var2, blocksEffectiveAgainst, var3);
        setHarvestLevel("shovel", var2.getHarvestLevel());
	}

//	public ItemUGShovel(ToolMaterial var2)
//	{
//		super(1, var2, blocksEffectiveAgainst, 1.0F);
//	}

//	@Override
//	public boolean func_150897_b(Block var1)
//	{
//		return blocksEffectiveAgainst.contains(var1) || materialEffectiveAgainst.contains(var1.getMaterial());
//	}

//    @Override
//    public Set<String> getToolClasses(ItemStack stack) {
//        return ImmutableSet.of("shovel");
//    }

    @Override
	public boolean doChainDestruction(Block var1, int var2) {
		return checkArrays(var1, AdvancedTools.addBlockForShovel)
                && (blocksEffectiveAgainst.contains(var1) || materialEffectiveAgainst.contains(var1.getMaterial()));
	}

    @Override
    public boolean isProperTool(Block block, int meta) {
        return Optional.fromNullable(block.getHarvestTool(meta)).or("").equals("shovel");
    }

    static{
		blocksEffectiveAgainst.add(Blocks.grass);
		blocksEffectiveAgainst.add(Blocks.gravel);
		blocksEffectiveAgainst.add(Blocks.dirt);
		blocksEffectiveAgainst.add(Blocks.sand);
		blocksEffectiveAgainst.add(Blocks.snow);
		blocksEffectiveAgainst.add(Blocks.snow_layer);
		blocksEffectiveAgainst.add(Blocks.clay);
		blocksEffectiveAgainst.add(Blocks.farmland);
		blocksEffectiveAgainst.add(Blocks.soul_sand);
		blocksEffectiveAgainst.add(Blocks.mycelium);

        materialEffectiveAgainst.add(Material.grass);
        materialEffectiveAgainst.add(Material.clay);
        materialEffectiveAgainst.add(Material.craftedSnow);
        materialEffectiveAgainst.add(Material.sand);
        materialEffectiveAgainst.add(Material.ground);
        materialEffectiveAgainst.add(Material.snow);
	}
}
