package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
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
    public boolean hasEffect(ItemStack var1, int pass)
    {
        return true;
    }
    @Override
    public EnumRarity getRarity(ItemStack var1)
    {
        return this.r;
    }
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            --par5;
        }

        if (par7 == 1)
        {
            ++par5;
        }

        if (par7 == 2)
        {
            --par6;
        }

        if (par7 == 3)
        {
            ++par6;
        }

        if (par7 == 4)
        {
            --par4;
        }

        if (par7 == 5)
        {
            ++par4;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            Block var8 = par3World.getBlock(par4, par5, par6);
            Block var9 = par3World.getBlock(par4 + 1, par5, par6);

            if (var8 == Blocks.air && var9 == Blocks.air)
            {
            	par3World.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            	par3World.setBlock(par4, par5, par6, Blocks.chest);
                TileEntityChest var10 = (TileEntityChest)par3World.getTileEntity(par4, par5, par6);

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
                    var10.setInventorySlotContents(17, new ItemStack(Items.arrow, 64));
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

                par3World.setBlock(par4 + 1, par5, par6, Blocks.chest);
                var10 = (TileEntityChest)par3World.getTileEntity(par4 + 1, par5, par6);

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
                    var10.setInventorySlotContents(14, new ItemStack(Items.stick, 64));
                    var10.setInventorySlotContents(15, new ItemStack(Items.iron_ingot, 64));
                    var10.setInventorySlotContents(16, new ItemStack(Items.gold_ingot, 64));
                    var10.setInventorySlotContents(17, new ItemStack(Items.diamond, 64));
                    var10.setInventorySlotContents(18, new ItemStack(Blocks.log, 64));
                    var10.setInventorySlotContents(18, new ItemStack(Blocks.log2, 64));
                    var10.setInventorySlotContents(19, new ItemStack(Items.blaze_rod, 64));
                    var10.setInventorySlotContents(20, new ItemStack(Blocks.snow, 64));
                    var10.setInventorySlotContents(21, new ItemStack(Items.feather, 64));
                    var10.setInventorySlotContents(22, new ItemStack(Blocks.glowstone, 64));
                    var10.setInventorySlotContents(23, new ItemStack(Items.wheat_seeds, 64));
                    var10.setInventorySlotContents(24, new ItemStack(Items.water_bucket));
                    var10.setInventorySlotContents(25, new ItemStack(Items.ender_eye, 64));
                    var10.setInventorySlotContents(26, new ItemStack(Items.spider_eye, 64));
                    var10.setInventorySlotContents(27, new ItemStack(Items.redstone, 64));
                }
            }

            --par1ItemStack.stackSize;
            return true;
        }
    }
}
