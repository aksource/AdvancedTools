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

    public ItemUQHolySaber(ToolMaterial toolMaterial) {
        super(toolMaterial);
        this.baseDmgValue = 4.0F + toolMaterial.getDamageVsEntity();
    }

    public ItemUQHolySaber(ToolMaterial toolMaterial, int damageValue) {
        super(toolMaterial, damageValue);
        this.baseDmgValue = damageValue;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean ishand) {
        super.onUpdate(stack, world, entity, slot, ishand);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (player.getHealth() < player.getMaxHealth()
                    && player.getHeldItemMainhand() != null
                    && player.getHeldItemMainhand().getItem() == this) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"), 40, 0));
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity var1) {
        byte var2 = 0;

        if (var1 instanceof EntityLiving) {
            EntityLiving var3 = (EntityLiving) var1;

            if (var3.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
                var2 = 7;
            } else if (var1 instanceof EntityEnderman) {
                var2 = 10;
            }
        }
        ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, this.baseDmgValue + var2, 0);
        player.getAttributeMap().applyAttributeModifiers(itemstack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
        return false;
    }
}
