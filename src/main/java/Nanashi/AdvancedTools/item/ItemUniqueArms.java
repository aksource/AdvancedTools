package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.utils.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;

public class ItemUniqueArms extends ItemSword {
    public ItemUniqueArms(ToolMaterial toolMaterial) {
        super(toolMaterial);
    }

    public ItemUniqueArms(ToolMaterial toolMaterial, int attackDamage) {
        super(toolMaterial);
        ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, (float) attackDamage, 0);
    }

    @Override
    public void onCreated(ItemStack var1, World var2, EntityPlayer var3) {
        super.onCreated(var1, var2, var3);

        if (var1.getItem() == Items.SmashBat) {
            var1.addEnchantment(Enchantments.KNOCKBACK, 10);
        }
    }

    @Override
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
        super.onUpdate(var1, var2, var3, var4, var5);

        if (!var1.hasTagCompound() || !var1.isItemEnchanted()) {
            if (var1.getItem() == Items.SmashBat) {
                var1.addEnchantment(Enchantments.KNOCKBACK, 10);
            }
        }
    }

    protected int getFoodStat(EntityLivingBase entityLivingBase) {
        return (entityLivingBase instanceof EntityPlayer) ? ((EntityPlayer) entityLivingBase).getFoodStats().getFoodLevel() : 1;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    protected ActionResult<ItemStack> setActiveHand(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        ItemStack itemStack = playerIn.getHeldItem(hand);
        int foodLevel = playerIn.getFoodStats().getFoodLevel();

        if (foodLevel > 6) {
            playerIn.setActiveHand(hand);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }
}
