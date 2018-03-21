package Nanashi.AdvancedTools.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Item_CrossBow extends ItemBow {
    public Item_CrossBow() {
        super();
        this.maxStackSize = 1;
        this.setMaxDamage(192);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack itemStack, @Nonnull World world, EntityLivingBase entityLiving, int timeLeft) {}

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack var1) {
        return EnumAction.NONE;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer entityPlayer, EnumHand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        boolean flag = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0;

        ItemStack itemArrow = this.findAmmo(entityPlayer);
        int i = 0;
        i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, world,
                entityPlayer, i, hasAmmo(entityPlayer, hand));
        if (hasAmmo(entityPlayer, hand)) {
            ItemArrow arrow = ((ItemArrow) (itemArrow.getItem() instanceof ItemArrow ? itemArrow.getItem() : Items.ARROW));
            EntityArrow entityarrow = arrow.createArrow(world, itemArrow, entityPlayer);
            itemStack.damageItem(1, entityPlayer);
            world.playSound(entityPlayer, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,
                    1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0f * 0.5F);
            if (!flag) {
                itemArrow.shrink(1);

                if (itemArrow.getCount() == 0) {
                    entityPlayer.inventory.deleteStack(itemArrow);
                }
            }

            int var6 = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemStack);

            if (var6 > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + (double) var6 * 0.5D + 0.5D);
            }

            int var7 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemStack);

            if (var7 > 0) {
                entityarrow.setKnockbackStrength(var7);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemStack) > 0) {
                entityarrow.setFire(100);
            }

            if (!world.isRemote) {
                world.spawnEntity(entityarrow);
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    private boolean hasAmmo(@Nonnull EntityPlayer entityPlayer, EnumHand hand) {
        ItemStack heldItem = entityPlayer.getHeldItem(hand);
        ItemStack itemArrow = this.findAmmo(entityPlayer);
        boolean flag = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, heldItem) > 0;
        return flag || !itemArrow.isEmpty();
    }
}
