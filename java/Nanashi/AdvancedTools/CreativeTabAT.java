package Nanashi.AdvancedTools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabAT extends CreativeTabs
{
	public CreativeTabAT(String var1)
    {
        super(var1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem()
    {
        return AdvancedTools.UGDiamondPickaxe;
    }
    @Override
    public String getTranslatedTabLabel()
    {
    	return "Adv.Tools";
    }
}