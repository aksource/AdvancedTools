package Nanashi.AdvancedTools.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class Item_Negi extends ItemFood
{
	public Item_Negi(int var2, float var3, boolean var4)
	{
		super(var2, var3, var4);
		this.setMaxDamage(87);
		this.setMaxStackSize(1);
	}
	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	protected void onFoodEaten(ItemStack var1, World var2, EntityPlayer var3)
	{
        super.onFoodEaten(var1, var2, var3);
		float var4 = 0.5F * (float)var1.getItemDamage() / (float)this.getMaxDamage();

		if (var4 > 0.0F)
		{
			this.setPotionEffect(Potion.hunger.id, 30, 0, var4);
		}

		if (0.65F - var4 >= var2.rand.nextFloat())
		{
			var3.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 0));
		}
	}
	@Override
	public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3)
	{
		var1.damageItem(1, var3);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1, World par2, Block par3, BlockPos blockPos, EntityLivingBase par7)
	{
		par1.damageItem(2, par7);
		return true;
	}

	public float func_82803_g()
	{
		return 3;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Multimap getAttributeModifiers(ItemStack itemStack)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double)3, 0));
		return multimap;
	}
	@SideOnly(Side.CLIENT)
	@Override
    @SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("You can eat this.");
	}
}
