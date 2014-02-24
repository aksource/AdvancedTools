package Nanashi.AdvancedTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemUQDevilSword extends ItemUniqueArms
{
	private int dmg = 0;
	protected ItemUQDevilSword(ToolMaterial var2)
	{
		super(var2);
	}

	protected ItemUQDevilSword(ToolMaterial var2, int var3)
	{
		super(var2, var3);
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "GenocideBlade");
//	}
	@Override
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (var3 instanceof EntityPlayer)
		{
			EntityPlayer var6 = (EntityPlayer)var3;

			if (var6.getCurrentEquippedItem() != null && var6.getCurrentEquippedItem().getItem() == this && !var6.isPotionActive(Potion.damageBoost))
			{
				if (var6.getHealth() > 1)
				{
					var6.heal(-1);
					var6.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 59, 1));
				}
				else
				{
					var6.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 19, 1));
				}
			}
		}
	}
	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		int var4 = MathHelper.ceiling_float_int(var3.getHealth());

		if (var4 > 1)
		{
			var3.attackEntityFrom(DamageSource.generic, var4 - 1);
		}

		return var1;
	}

	@Override
	public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3)
	{
		if (var2.hurtTime == 0)
		{
			var3.heal(2);
		}
		if(var2 instanceof EntityPlayer)
		{
			ItemStack itemstack = ((EntityPlayer)var2).inventory.getCurrentItem();
			int var4 = MathHelper.ceiling_float_int(var2.getMaxHealth() - var2.getHealth());

			if (itemstack.getItem() instanceof ItemUQDevilSword)
			{
				this.dmg = 0;
				if (var4 >= 19)
				{
					this.dmg += 10;
				}
				else if (var4 >= 10)
				{
					++this.dmg;
				}
			}
		}
		var1.damageItem(1, var3);
		return true;
	}
}
