package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Item_CrossBow extends ItemBow
{
	public Item_CrossBow()
	{
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(192);
	}

	public boolean isFull3D()
	{
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "CrossBow");
	}

	public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4) {}


	public EnumAction getItemUseAction(ItemStack var1)
	{
		return EnumAction.none;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		boolean var4 = var3.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1) > 0;

		if (var4 || var3.inventory.hasItem(Items.arrow))
		{
			EntityArrow var5 = new EntityArrow(var2, var3, 0.75F);
			var1.damageItem(1, var3);
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

			if (!var4)
			{
				var3.inventory.consumeInventoryItem(Items.arrow);
			}

			int var6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1);

			if (var6 > 0)
			{
				var5.setDamage(var5.getDamage() + (double)var6 * 0.5D + 0.5D);
			}

			int var7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, var1);

			if (var7 > 0)
			{
				var5.setKnockbackStrength(var7);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) > 0)
			{
				var5.setFire(100);
			}

			if (!var2.isRemote)
			{
				var2.spawnEntityInWorld(var5);
			}
		}

		return var1;
	}
}
