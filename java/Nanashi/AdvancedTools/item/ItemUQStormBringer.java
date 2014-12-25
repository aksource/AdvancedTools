package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_SBWindEdge;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class ItemUQStormBringer extends ItemUniqueArms
{
	private int coolTime;
	private int safetyCnt;

	public ItemUQStormBringer(ToolMaterial var2)
	{
		super(var2);
	}

	public ItemUQStormBringer(ToolMaterial var2, int var3)
	{
		super(var2);
	}

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
				int var9;
				double var12;

				for (int var8 = 0; var8 < 3; ++var8)
				{
					for (var9 = 0; var9 < 100; ++var9)
					{
						double var10 = (double)var3.rotationYaw / 180.0D * Math.PI;
						var12 = (double)(-var3.rotationPitch) / 180.0D * Math.PI;
						float var14 = 0.0628F * (float)var9;
						double var15 = 0.9D * Math.cos((double)var14);
						double var17 = 0.9D * -Math.sin((double)var14);
						var2.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, var3.posX, var3.posY - 0.85D + 0.5D * (double)var8, var3.posZ, var15, 0.0D, var17);
					}
				}

                @SuppressWarnings("unchecked")
				List<EntityLivingBase> var19 = var2.getEntitiesWithinAABB(EntityLivingBase.class, var3.getEntityBoundingBox().expand(8.0D, 2.0D, 8.0D));

				for (EntityLivingBase entityLivingBase : var19)
				{
					if (entityLivingBase != var3)
					{
						DamageSource var11 = DamageSource.causePlayerDamage(var3);
                        entityLivingBase.attackEntityFrom(var11, 0);
						var12 = entityLivingBase.posX - var3.posX;
						double var22 = entityLivingBase.posZ - var3.posZ;
						double var16 = Math.atan2(var22, var12);
                        entityLivingBase.addVelocity(Math.cos(var16) * 8.0D, entityLivingBase.motionY * 1.7D, Math.sin(var16) * 8.0D);
					}
				}
			}
			else
			{
				Entity_SBWindEdge var20 = new Entity_SBWindEdge(var2, var3, var7 * 2.0F);

				if (!var2.isRemote)
				{
					var2.spawnEntityInWorld(var20);
				}
			}

			var1.damageItem(1, var3);
			var2.playSoundAtEntity(var3, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

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
						this.safetyCnt += 3;
					}
					else if (this.coolTime > 200)
					{
						this.safetyCnt += 2;
					}
					else
					{
						++this.safetyCnt;
					}

					if (this.safetyCnt >= 3)
					{
						var3.getFoodStats().addStats(-1, 1.0f);
						this.safetyCnt = 0;
					}
				}
				else
				{
					this.safetyCnt = 0;
				}
			}

			var3.swingItem();
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
		par3List.add("Ability : Gale Impact");
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
