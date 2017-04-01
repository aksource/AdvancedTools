package Nanashi.AdvancedTools.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemEnhancer extends Item {
    private int id;

    public ItemEnhancer(int var1) {
        super();
        this.id = var1;
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }

    @Override
    @Nonnull
    public EnumRarity getRarity(ItemStack itemStack) {
        if (this.id == 0)
            return EnumRarity.UNCOMMON;
        else
            return EnumRarity.RARE;
    }
}
