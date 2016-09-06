package Nanashi.AdvancedTools.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemUQLuckies extends ItemUniqueArms {
    private int dmg;

    public ItemUQLuckies(ToolMaterial var2) {
        super(var2);
    }

    public ItemUQLuckies(ToolMaterial var2, int var3) {
        super(var2, var3);
        dmg = var3;
    }

    public void onCreated(ItemStack stack, World world, EntityPlayer entityPlayer) {
        super.onCreated(stack, world, entityPlayer);
        stack.addEnchantment(Enchantments.LOOTING, 7);
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHand) {
        super.onUpdate(stack, world, entity, slot, isHand);

        if (!stack.hasTagCompound() || stack.getEnchantmentTagList() == null) {
            stack.addEnchantment(Enchantments.LOOTING, 7);
        }
    }


    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity) {
        int var2 = this.dmg;
        if (entity instanceof EntityLiving) {
            EntityLiving var3 = (EntityLiving) entity;
            int var4 = MathHelper.floor_float(var3.getHealth());

            if (var4 <= var2 && var4 > 0 && var3.hurtTime <= 0) {
                int exp = ObfuscationReflectionHelper.getPrivateValue(EntityLiving.class, var3, 1);
                int var5 = (int) (2.0F * (float) exp);
                ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, var3, var5, 1);
            }
        }
        return false;
    }
}
