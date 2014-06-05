package Nanashi.AdvancedTools.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUQAsmoSlasher extends ItemUniqueArms
{
//	public ItemUQAsmoSlasher(ToolMaterial var2)
//	{
//		super(var2);
//	}

	public ItemUQAsmoSlasher(ToolMaterial var2, int var3)
	{
		super(var2);
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "AsmoSlasher");
//	}
	@Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
		if (par2EntityLivingBase instanceof EntityCreeper)
		{
			par2EntityLivingBase.getDataWatcher().updateObject(17, (byte)1);
		}

		if (par2EntityLivingBase instanceof EntityPig)
		{
			par2EntityLivingBase.onStruckByLightning(null);
		}
    	return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }
	@Override
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);
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

			if (var7 >= 1.0F && var5 > 7)
			{
				for (int var18 = 0; var18 < 8; ++var18)
				{
					double var9 = Math.PI * (double)var18 / 4.0D;
					double var11 = var3.posX + 5.5D * Math.sin(var9);
					double var13 = var3.posZ + 5.5D * Math.cos(var9);
					double var15 = (double)var2.getHeightValue((int)(var11 - 0.5D), (int)(var13 - 0.5D));
					EntityLightningBolt var17 = new EntityLightningBolt(var2, var11, var15, var13);

//					if (!var2.isRemote)
//					{
						var2.spawnEntityInWorld(var17);
//					}
				}

				if (!var3.capabilities.isCreativeMode)
				{
					var3.getFoodStats().addStats(-1, 1.0f);
				}
			}
			else
			{
				double var8 = var3.posX;
				double var10 = var3.posZ;
				var8 -= 6.0D * Math.sin((double)(var3.rotationYaw / 180.0F) * Math.PI);
				var10 += 6.0D * Math.cos((double)(var3.rotationYaw / 180.0F) * Math.PI);
				double var12 = (double)var2.getHeightValue((int)(var8 - 0.5D), (int)(var10 - 0.5D));
				EntityLightningBolt var14 = new EntityLightningBolt(var2, var8, var12, var10);

//				if (!var2.isRemote)
//				{
					var2.spawnEntityInWorld(var14);
//				}

				if (!var3.capabilities.isCreativeMode)
				{
					var3.getFoodStats().addStats(-1, 1.0f);
				}
			}

			var1.damageItem(1, var3);
			var3.swingItem();
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
	}
	@Override
	public EnumAction getItemUseAction(ItemStack var1)
	{
		return EnumAction.bow;
	}
	@SideOnly(Side.CLIENT)
	@Override
    @SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Ability : Lightning Caller");
	}
	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		int var4 = var3.getFoodStats().getFoodLevel();

		if (var4 > 6 && var2.canBlockSeeTheSky((int)(var3.posX - 0.5D), (int)var3.posY, (int)(var3.posZ - 0.5D)))
		{
			var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
		}

		return var1;
	}
}
