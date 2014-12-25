package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_PGPowerBomb;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemUQPlanetGuardian extends ItemUniqueArms
{
	public ItemUQPlanetGuardian(ToolMaterial var2)
	{
		super(var2);
	}

	public ItemUQPlanetGuardian(ToolMaterial var2, int var3)
	{
		super(var2);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4)
	{
		int var5 = var3.getFoodStats().getFoodLevel();

		if (var5 > 6)
		{
			int var6 = this.getMaxItemUseDuration(var1) - var4;
			float var7 = (float)var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

			if ((double)var7 < 0.1D)
			{
				return;
			}

			if (var7 > 1.0F)
			{
				var7 = 1.0F;
			}

			Entity_PGPowerBomb var8 = new Entity_PGPowerBomb(var2, var3, var7);

			if (!var2.isRemote)
			{
				var2.spawnEntityInWorld(var8);
			}

			if (!var3.capabilities.isCreativeMode)
			{
				var3.getFoodStats().addStats(-1, 1.0f);
			}

			var1.damageItem(1, var3);
			var3.swingItem();
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
	}
	@Override
	public EnumAction getItemUseAction(ItemStack var1)
	{
		return EnumAction.BOW;
	}

	@SideOnly(Side.CLIENT)
	@Override
    @SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Ability : Ground Banish");
	}
	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		int var4 = var3.getFoodStats().getFoodLevel();

		if (var4 > 6)
		{
			var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
		}

		return var1;
	}
}
