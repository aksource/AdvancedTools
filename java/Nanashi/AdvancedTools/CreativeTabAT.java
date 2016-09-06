package Nanashi.AdvancedTools;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class CreativeTabAT extends CreativeTabs
{
	public CreativeTabAT(String var1)
    {
        super(var1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public Item getTabIconItem()
    {
        return AdvancedTools.UGDiamondPickaxe;
    }
    @Override
    @Nonnull
    public String getTranslatedTabLabel()
    {
    	return "Adv.Tools";
    }
}