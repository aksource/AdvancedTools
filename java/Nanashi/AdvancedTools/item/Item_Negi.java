package Nanashi.AdvancedTools.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class Item_Negi extends ItemFood {
    public Item_Negi(int var2, float var3, boolean var4) {
        super(var2, var3, var4);
        this.setMaxDamage(87);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    protected void onFoodEaten(ItemStack var1, World var2, EntityPlayer var3) {
        super.onFoodEaten(var1, var2, var3);
        float var4 = 0.5F * (float) var1.getItemDamage() / (float) this.getMaxDamage();

        if (var4 > 0.0F) {
            this.setPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"), 30, 0), var4);
        }

        if (0.65F - var4 >= var2.rand.nextFloat()) {
            var3.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("heal"), 1, 0));
        }
    }

    @Override
    public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
        var1.damageItem(1, var3);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        stack.damageItem(2, entityLiving);
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(),
                    new AttributeModifier("Weapon modifier", (double) 3, 0));
        }
        return multimap;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
        par3List.add("You can eat this.");
    }
}
