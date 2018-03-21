package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.HashSet;
import java.util.Set;

public class ItemUGShovel extends ItemUGTool {
    private static final String TOOL_KIND = "shovel";
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
    public static final Set<Material> materialEffectiveAgainst = new HashSet<>();

    static {
        blocksEffectiveAgainst.add(Blocks.GRASS);
        blocksEffectiveAgainst.add(Blocks.GRAVEL);
        blocksEffectiveAgainst.add(Blocks.DIRT);
        blocksEffectiveAgainst.add(Blocks.SAND);
        blocksEffectiveAgainst.add(Blocks.SNOW);
        blocksEffectiveAgainst.add(Blocks.SNOW_LAYER);
        blocksEffectiveAgainst.add(Blocks.CLAY);
        blocksEffectiveAgainst.add(Blocks.FARMLAND);
        blocksEffectiveAgainst.add(Blocks.SOUL_SAND);
        blocksEffectiveAgainst.add(Blocks.MYCELIUM);

        materialEffectiveAgainst.add(Material.GRASS);
        materialEffectiveAgainst.add(Material.CLAY);
        materialEffectiveAgainst.add(Material.CRAFTED_SNOW);
        materialEffectiveAgainst.add(Material.SAND);
        materialEffectiveAgainst.add(Material.GROUND);
        materialEffectiveAgainst.add(Material.SNOW);
    }

    public ItemUGShovel(ToolMaterial toolMaterial, float durationModifier) {
        super(1.5F, -3.0F, toolMaterial, blocksEffectiveAgainst, durationModifier, TOOL_KIND);
        setHarvestLevel(TOOL_KIND, toolMaterial.getHarvestLevel());
    }

    @Override
    public boolean doChainDestruction(IBlockState state) {
        return checkArrays(state.getBlock(), AdvancedTools.addBlockForShovel)
                && (blocksEffectiveAgainst.contains(state.getBlock()) || materialEffectiveAgainst.contains(state.getMaterial()));
    }
}
