package Nanashi.AdvancedTools.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEnhancer extends Item
{
    private int id;

    public ItemEnhancer(int var1)
    {
        super();
        this.id = var1;
    }
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister par1IconRegister)
//    {
//    	if(id == 0)
//    		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "EnhancerR");
//    	else
//    		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "EnhancerB");
//    }
    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return true;
    }
    @Override
    public EnumRarity getRarity(ItemStack var1)
    {
        if(this.id == 0)
        	return EnumRarity.uncommon;
        else
        	return EnumRarity.rare;
    }
}
