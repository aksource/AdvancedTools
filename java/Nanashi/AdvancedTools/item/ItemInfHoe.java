package Nanashi.AdvancedTools.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemInfHoe extends ItemHoe
{
    public ItemInfHoe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, BlockPos blockPos, EnumHand hand, EnumFacing enumFacing, float par8, float par9, float par10)
    {
        return super.onItemUse(itemStack, entityPlayer, world, blockPos, hand, enumFacing, par8, par9, par10);
    }
}
