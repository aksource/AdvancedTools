package Nanashi.AdvancedTools.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemUQDevilSword extends ItemUniqueArms {
    public ItemUQDevilSword(ToolMaterial var2) {
        super(var2);
    }

    public ItemUQDevilSword(ToolMaterial var2, int var3) {
        super(var2, var3);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean inHand) {
        super.onUpdate(itemStack, world, entity, slot, inHand);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            Potion strength = Potion.getPotionFromResourceLocation("strength");
            if (player.getHeldItemMainhand() != null
                    && player.getHeldItemMainhand().getItem() == this
                    && !player.isPotionActive(strength)) {
                if (player.getHealth() > 1) {
                    player.heal(-1);
                    player.addPotionEffect(new PotionEffect(strength, 59, 1));
                } else {
                    player.addPotionEffect(new PotionEffect(strength, 19, 1));
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        int var4 = MathHelper.ceiling_float_int(playerIn.getHealth());

        if (var4 > 1) {
            playerIn.attackEntityFrom(DamageSource.generic, var4 - 1);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (target.hurtTime == 0) {
            attacker.heal(2);
        }
        if (target instanceof EntityPlayer) {
            int var4 = MathHelper.ceiling_float_int(target.getMaxHealth() - target.getHealth());
            float dmg = getDamageFromNBT(stack);
            if (var4 >= 19) {
                dmg += 10;
            } else if (var4 >= 10) {
                ++dmg;
            }
            ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, dmg, 0);
            setDamageToNBT(stack, dmg);
        }
        stack.damageItem(1, attacker);
        return true;
    }

    private float getDamageFromNBT(ItemStack itemStack) {
        if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
        return itemStack.getTagCompound().getFloat("AdvancedTools:Devil");
    }

    private void setDamageToNBT(ItemStack itemStack, float dmg) {
        if (!itemStack.hasTagCompound()) itemStack.setTagCompound(new NBTTagCompound());
        itemStack.getTagCompound().setFloat("AdvancedTools:Devil", dmg);
    }
}
