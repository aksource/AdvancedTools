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

public class ItemUGPickaxe extends ItemUGTool {
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<>();
    public static final Set<Material> materialEffectiveAgainst = new HashSet<>();

    static {
        blocksEffectiveAgainst.add(Blocks.COBBLESTONE);
        blocksEffectiveAgainst.add(Blocks.DOUBLE_STONE_SLAB);
        blocksEffectiveAgainst.add(Blocks.STONE_SLAB);
        blocksEffectiveAgainst.add(Blocks.STONE);
        blocksEffectiveAgainst.add(Blocks.STAINED_HARDENED_CLAY);
        blocksEffectiveAgainst.add(Blocks.STONE_BRICK_STAIRS);
        blocksEffectiveAgainst.add(Blocks.STONE_BUTTON);
        blocksEffectiveAgainst.add(Blocks.STONE_PRESSURE_PLATE);
        blocksEffectiveAgainst.add(Blocks.STONE_STAIRS);
        blocksEffectiveAgainst.add(Blocks.STONEBRICK);
        blocksEffectiveAgainst.add(Blocks.SANDSTONE);
        blocksEffectiveAgainst.add(Blocks.SANDSTONE_STAIRS);
        blocksEffectiveAgainst.add(Blocks.MOSSY_COBBLESTONE);
        blocksEffectiveAgainst.add(Blocks.COAL_ORE);
        blocksEffectiveAgainst.add(Blocks.COAL_BLOCK);
        blocksEffectiveAgainst.add(Blocks.IRON_ORE);
        blocksEffectiveAgainst.add(Blocks.IRON_BLOCK);
        blocksEffectiveAgainst.add(Blocks.IRON_BARS);
        blocksEffectiveAgainst.add(Blocks.IRON_DOOR);
        blocksEffectiveAgainst.add(Blocks.GOLD_BLOCK);
        blocksEffectiveAgainst.add(Blocks.GOLD_ORE);
        blocksEffectiveAgainst.add(Blocks.DIAMOND_ORE);
        blocksEffectiveAgainst.add(Blocks.DIAMOND_BLOCK);
        blocksEffectiveAgainst.add(Blocks.ICE);
        blocksEffectiveAgainst.add(Blocks.NETHER_BRICK);
        blocksEffectiveAgainst.add(Blocks.NETHER_BRICK_FENCE);
        blocksEffectiveAgainst.add(Blocks.NETHER_BRICK_STAIRS);
        blocksEffectiveAgainst.add(Blocks.NETHERRACK);
        blocksEffectiveAgainst.add(Blocks.LAPIS_BLOCK);
        blocksEffectiveAgainst.add(Blocks.LAPIS_ORE);
        blocksEffectiveAgainst.add(Blocks.REDSTONE_ORE);
        blocksEffectiveAgainst.add(Blocks.LIT_REDSTONE_ORE);
        blocksEffectiveAgainst.add(Blocks.REDSTONE_BLOCK);
        blocksEffectiveAgainst.add(Blocks.RAIL);
        blocksEffectiveAgainst.add(Blocks.DETECTOR_RAIL);
        blocksEffectiveAgainst.add(Blocks.GOLDEN_RAIL);
        blocksEffectiveAgainst.add(Blocks.ACTIVATOR_RAIL);
        blocksEffectiveAgainst.add(Blocks.PACKED_ICE);

        materialEffectiveAgainst.add(Material.ROCK);
        materialEffectiveAgainst.add(Material.IRON);
        materialEffectiveAgainst.add(Material.ANVIL);
        materialEffectiveAgainst.add(Material.CORAL);
        materialEffectiveAgainst.add(Material.ICE);
        materialEffectiveAgainst.add(Material.PACKED_ICE);
    }

    public ItemUGPickaxe(ToolMaterial var2, float var3) {
        super(1.0F, -2.8F, var2, blocksEffectiveAgainst, var3);
        setHarvestLevel("pickaxe", var2.getHarvestLevel());
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack itemStack) {
        return materialEffectiveAgainst.contains(state.getMaterial());
    }

    @Override
    public float getStrVsBlock(ItemStack var1, IBlockState state) {
        return state.getBlock() != null && (materialEffectiveAgainst.contains(state.getMaterial())) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(var1, state);
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
}
