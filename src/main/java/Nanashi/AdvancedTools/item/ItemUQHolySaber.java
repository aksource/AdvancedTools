package Nanashi.AdvancedTools.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemUQHolySaber extends ItemUniqueArms {
    private float baseDmgValue;

    public ItemUQHolySaber(ToolMaterial toolMaterial, int damageValue) {
        super(toolMaterial, damageValue);
        this.baseDmgValue = damageValue;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean ishand) {
        super.onUpdate(stack, world, entity, slot, ishand);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            Potion regeneration = Potion.getPotionFromResourceLocation("regeneration");

            if (player.getHealth() < player.getMaxHealth()
                    && !player.getHeldItemMainhand().isEmpty()
                    && player.getHeldItemMainhand().getItem() == this
                    && regeneration != null) {
                player.addPotionEffect(new PotionEffect(regeneration, 40, 0));
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer entityPlayer, Entity entity) {
        byte var2 = 0;

        if (entity instanceof EntityLiving) {
            EntityLiving var3 = (EntityLiving) entity;

            if (var3.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                var2 = 7;
            } else if (entity instanceof EntityEnderman) {
                var2 = 10;
            }
        }
        ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, this.baseDmgValue + var2, 0);
        entityPlayer.getAttributeMap().applyAttributeModifiers(itemStack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
        return false;
    }
}
