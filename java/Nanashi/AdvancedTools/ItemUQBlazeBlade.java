package Nanashi.AdvancedTools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUQBlazeBlade extends ItemUniqueArms
{
	private int coolTime;
	private int saftyCnt;

	protected ItemUQBlazeBlade(ToolMaterial var2)
	{
		super(var2);
	}

	protected ItemUQBlazeBlade(ToolMaterial var2, int var3)
	{
		super(var2);
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//		this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "BlazeBlade");
//	}
	@Override
	public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
	{
		super.onUpdate(var1, var2, var3, var4, var5);

		if (this.coolTime > 0)
		{
			--this.coolTime;
		}
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

			if (var7 > 1.0F)
			{
				var7 = 1.0F;
			}

			Entity_BBFireBall var8 = new Entity_BBFireBall(var2, var3, var7 * 2.0F, var7 == 1.0F);

			if (!var2.isRemote)
			{
				var2.spawnEntityInWorld(var8);
			}

			if (!var3.capabilities.isCreativeMode)
			{
				this.coolTime += 20;

				if (this.coolTime > 100)
				{
					if (this.coolTime > 500)
					{
						this.coolTime = 500;
					}

					if (this.coolTime > 350)
					{
						this.saftyCnt += 3;
					}
					else if (this.coolTime > 200)
					{
						this.saftyCnt += 2;
					}
					else
					{
						++this.saftyCnt;
					}

					if (this.saftyCnt >= 3)
					{
						var3.getFoodStats().addStats(-1, 1.0f);
						this.saftyCnt = 0;
					}
				}
				else
				{
					this.saftyCnt = 0;
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
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		par3List.add("Ability : Fire Ball");
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
	@Override//暫定処置
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        entity.setFire(4);
		return false;
    }
}
