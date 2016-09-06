package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSpecialSet extends Item
{
    EnumRarity r;

    protected ItemSpecialSet(EnumRarity var2)
    {
        super();
        this.r = var2;
    }
    @Override
    public boolean hasEffect(ItemStack var1)
    {
        return true;
    }
    @Override
    public EnumRarity getRarity(ItemStack var1)
    {
        return this.r;
    }
    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer playerIn, World worldIn, BlockPos blockPos, EnumHand hand, EnumFacing par7, float par8, float par9, float par10)
    {
        blockPos.add(par7.getFrontOffsetX(), par7.getFrontOffsetY(), par7.getFrontOffsetZ());

        if (!playerIn.canPlayerEdit(blockPos, par7, itemStack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            Block var8 = worldIn.getBlockState(blockPos).getBlock();
            Block var9 = worldIn.getBlockState(new BlockPos(blockPos).add(0, +1, 0)).getBlock();

            if (var8 == Blocks.AIR && var9 == Blocks.AIR)
            {
                worldIn.playSound(playerIn, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            	worldIn.setBlockState(blockPos, Blocks.CHEST.getDefaultState());
                TileEntityChest var10 = (TileEntityChest)worldIn.getTileEntity(blockPos);

                if (var10 != null)
                {
                    var10.setInventorySlotContents(0, new ItemStack(AdvancedTools.UGWoodPickaxe));
                    var10.setInventorySlotContents(1, new ItemStack(AdvancedTools.UGStonePickaxe));
                    var10.setInventorySlotContents(2, new ItemStack(AdvancedTools.UGIronPickaxe));
                    var10.setInventorySlotContents(3, new ItemStack(AdvancedTools.UGDiamondPickaxe));
                    var10.setInventorySlotContents(4, new ItemStack(AdvancedTools.UGGoldPickaxe));
                    var10.setInventorySlotContents(5, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(6, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(7, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(8, new ItemStack(AdvancedTools.NEGI));
                    var10.setInventorySlotContents(9, new ItemStack(AdvancedTools.UGWoodShovel));
                    var10.setInventorySlotContents(10, new ItemStack(AdvancedTools.UGStoneShovel));
                    var10.setInventorySlotContents(11, new ItemStack(AdvancedTools.UGIronShovel));
                    var10.setInventorySlotContents(12, new ItemStack(AdvancedTools.UGDiamondShovel));
                    var10.setInventorySlotContents(13, new ItemStack(AdvancedTools.UGGoldShovel));
                    var10.setInventorySlotContents(14, new ItemStack(AdvancedTools.LuckLuck));
                    var10.setInventorySlotContents(15, new ItemStack(AdvancedTools.SmashBat));
                    var10.setInventorySlotContents(16, new ItemStack(AdvancedTools.CrossBow));
                    var10.setInventorySlotContents(17, new ItemStack(Items.ARROW, 64));
                    var10.setInventorySlotContents(18, new ItemStack(AdvancedTools.UGWoodAxe));
                    var10.setInventorySlotContents(19, new ItemStack(AdvancedTools.UGStoneAxe));
                    var10.setInventorySlotContents(20, new ItemStack(AdvancedTools.UGIronAxe));
                    var10.setInventorySlotContents(21, new ItemStack(AdvancedTools.UGDiamondAxe));
                    var10.setInventorySlotContents(22, new ItemStack(AdvancedTools.UGGoldAxe));
                    var10.setInventorySlotContents(23, new ItemStack(AdvancedTools.ThrowingKnife, 16));
                    var10.setInventorySlotContents(24, new ItemStack(AdvancedTools.ThrowingKnife, 16));
                    var10.setInventorySlotContents(25, new ItemStack(AdvancedTools.PoisonKnife, 16));
                    var10.setInventorySlotContents(26, new ItemStack(AdvancedTools.PoisonKnife, 16));
                }

                BlockPos neighbor = new BlockPos(blockPos).add(1, 0, 0);
                worldIn.setBlockState(neighbor, Blocks.CHEST.getDefaultState());
                var10 = (TileEntityChest)worldIn.getTileEntity(neighbor);

                if (var10 != null)
                {
                    var10.setInventorySlotContents(0, new ItemStack(AdvancedTools.BlazeBlade));
                    var10.setInventorySlotContents(1, new ItemStack(AdvancedTools.IceHold));
                    var10.setInventorySlotContents(2, new ItemStack(AdvancedTools.AsmoSlasher));
                    var10.setInventorySlotContents(3, new ItemStack(AdvancedTools.PlanetGuardian));
                    var10.setInventorySlotContents(4, new ItemStack(AdvancedTools.StormBringer));
                    var10.setInventorySlotContents(5, new ItemStack(AdvancedTools.HolySaber));
                    var10.setInventorySlotContents(6, new ItemStack(AdvancedTools.DevilSword));
                    var10.setInventorySlotContents(7, new ItemStack(AdvancedTools.RedEnhancer, 64));
                    var10.setInventorySlotContents(8, new ItemStack(AdvancedTools.BlueEnhancer, 64));
                    var10.setInventorySlotContents(9, new ItemStack(AdvancedTools.InfiniteSword));
                    var10.setInventorySlotContents(10, new ItemStack(AdvancedTools.InfinitePickaxe));
                    var10.setInventorySlotContents(11, new ItemStack(AdvancedTools.InfiniteShovel));
                    var10.setInventorySlotContents(12, new ItemStack(AdvancedTools.InfiniteAxe));
                    var10.setInventorySlotContents(13, new ItemStack(AdvancedTools.InfiniteHoe));
                    var10.setInventorySlotContents(14, new ItemStack(Items.STICK, 64));
                    var10.setInventorySlotContents(15, new ItemStack(Items.IRON_INGOT, 64));
                    var10.setInventorySlotContents(16, new ItemStack(Items.GOLD_INGOT, 64));
                    var10.setInventorySlotContents(17, new ItemStack(Items.DIAMOND, 64));
                    var10.setInventorySlotContents(18, new ItemStack(Blocks.LOG, 64));
                    var10.setInventorySlotContents(18, new ItemStack(Blocks.LOG2, 64));
                    var10.setInventorySlotContents(19, new ItemStack(Items.BLAZE_ROD, 64));
                    var10.setInventorySlotContents(20, new ItemStack(Blocks.SNOW, 64));
                    var10.setInventorySlotContents(21, new ItemStack(Items.FEATHER, 64));
                    var10.setInventorySlotContents(22, new ItemStack(Blocks.GLOWSTONE, 64));
                    var10.setInventorySlotContents(23, new ItemStack(Items.WHEAT_SEEDS, 64));
                    var10.setInventorySlotContents(24, new ItemStack(Items.WATER_BUCKET));
                    var10.setInventorySlotContents(25, new ItemStack(Items.ENDER_EYE, 64));
                    var10.setInventorySlotContents(26, new ItemStack(Items.SPIDER_EYE, 64));
                    var10.setInventorySlotContents(27, new ItemStack(Items.REDSTONE, 64));
                }
            }

            --itemStack.stackSize;
            return EnumActionResult.SUCCESS;
        }
    }
}
