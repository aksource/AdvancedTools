package Nanashi.AdvancedTools;

import Nanashi.AdvancedTools.utils.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CreativeTabAT extends CreativeTabs {
    public CreativeTabAT(String var1) {
        super(var1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.UGDiamondPickaxe);
    }

    @Override
    @Nonnull
    public String getTranslatedTabLabel() {
        return "Adv.Tools";
    }
}