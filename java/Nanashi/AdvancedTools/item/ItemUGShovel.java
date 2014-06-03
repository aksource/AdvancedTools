package Nanashi.AdvancedTools.item;

import java.util.HashSet;
import java.util.Set;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemUGShovel extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
	public ItemUGShovel(ToolMaterial var2, float var3)
	{
		super(1, var2, blocksEffectiveAgainst, var3);
	}

	public ItemUGShovel(ToolMaterial var2)
	{
		super(1, var2, blocksEffectiveAgainst, 1.0F);
	}

	@Override
	public boolean func_150897_b(Block var1)
	{
		return blocksEffectiveAgainst.contains(var1);
	}

	public boolean doChainDestruction(Block var1)
	{
		return checkArrays(var1, AdvancedTools.addBlockForShovel) && this.func_150897_b(var1);
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
	}
}
