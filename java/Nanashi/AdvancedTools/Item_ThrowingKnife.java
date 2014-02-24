package Nanashi.AdvancedTools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Item_ThrowingKnife extends Item
{
	public boolean addPoison;

	public Item_ThrowingKnife(boolean var2)
	{
		super();
		this.maxStackSize = 16;
		this.addPoison = var2;
	}
	@Override
	public boolean isFull3D()
	{
		return true;
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//		if(this.getUnlocalizedName().equals("item.ThrowingKnife"))
//			this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "ThrowingKnife");
//		else if(this.getUnlocalizedName().equals("item.PoisonKnife"))
//			this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "PoisonKnife");
//	}
	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		if (var3.capabilities.isCreativeMode || var3.inventory.hasItem(this))
		{
			Entity_ThrowingKnife var4 = new Entity_ThrowingKnife(var2, var3, 1.0F, this.addPoison);
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
			--var1.stackSize;

			if (!var2.isRemote)
			{
				var2.spawnEntityInWorld(var4);
			}
		}

		return var1;
	}

	@Override
	public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3)
	{
		if (this.addPoison && var3 instanceof EntityPlayer)
		{
			var2.addPotionEffect(new PotionEffect(Potion.poison.id, 60, 1));
			--var1.stackSize;

			if (!((EntityPlayer)var3).inventory.addItemStackToInventory(new ItemStack(AdvancedTools.ThrowingKnife)))
			{
				var3.dropItem(AdvancedTools.ThrowingKnife, 1);
			}
		}

		return true;
	}
}
