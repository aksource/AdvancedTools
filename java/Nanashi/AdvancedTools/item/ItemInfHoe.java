package Nanashi.AdvancedTools.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemInfHoe extends ItemHoe
{
    public ItemInfHoe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, BlockPos blockPos, EnumFacing par7, float par8, float par9, float par10)
    {
        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, blockPos, par7, par8, par9, par10);
    }
}
