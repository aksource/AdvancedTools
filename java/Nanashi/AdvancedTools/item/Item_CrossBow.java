package Nanashi.AdvancedTools.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Item_CrossBow extends ItemBow {
    public Item_CrossBow() {
        super();
        this.maxStackSize = 1;
        this.setMaxDamage(192);
    }

    public boolean isFull3D() {
        return true;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityLivingBase entityLiving, int timeLeft) {}


    public EnumAction getItemUseAction(ItemStack var1) {
        return EnumAction.NONE;
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer, EnumHand hand) {
        boolean flag = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0;

        ItemStack itemArrow = this.findAmmo(entityPlayer);
        int i = 0;
        i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, world,
                entityPlayer, i, itemArrow != null || flag);
        if (flag || itemArrow != null) {
            ItemArrow itemarrow = ((ItemArrow) (itemArrow.getItem() instanceof ItemArrow ? itemArrow.getItem() : Items.ARROW));
            EntityArrow entityarrow = itemarrow.createArrow(world, itemArrow, entityPlayer);
            itemStack.damageItem(1, entityPlayer);
            world.playSound(entityPlayer, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,
                    1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0f * 0.5F);
            if (!flag) {
                --itemArrow.stackSize;

                if (itemArrow.stackSize == 0) {
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
                world.spawnEntityInWorld(entityarrow);
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }
}
