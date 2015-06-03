package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemUniqueArms extends ItemSword
{
	public ItemUniqueArms(ToolMaterial var2)
	{
		super(var2);
	}

	public ItemUniqueArms(ToolMaterial var2, int var3)
	{
		super(var2);
		ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, this, (float)var3, 0);
	}

	@Override
	public void onCreated(ItemStack var1, World var2, EntityPlayer var3)
	{
		super.onCreated(var1, var2, var3);

		if (var1.getItem() == AdvancedTools.SmashBat)
		{
			var1.addEnchantment(Enchantment.knockback, 10);
		}
	}
	@Override
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (!var1.hasTagCompound() || !var1.isItemEnchanted())
		{
			if (var1.getItem() == AdvancedTools.SmashBat)
			{
				var1.addEnchantment(Enchantment.knockback, 10);
			}
		}
	}
}
