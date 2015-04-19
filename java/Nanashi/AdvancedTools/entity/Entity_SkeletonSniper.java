package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Entity_SkeletonSniper extends EntitySkeleton
{
	private static final ItemStack mainHeldItem = new ItemStack(Items.bow, 1);
	private static final ItemStack subHeldItem = new ItemStack(AdvancedTools.SmashBat, 1);

	public Entity_SkeletonSniper(World var1)
	{
		super(var1);
		this.experienceValue = 7;
	}
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(2.0D);
    }
	/**
	 * Returns true if the newer Entity AI code should be run
	 */
    @Override
	public boolean isAIEnabled()
	{
		return false;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
    @Override
	public void onDeath(DamageSource var1)
	{
		super.onDeath(var1);

		if (var1.getEntity() instanceof EntityPlayer && this.rand.nextFloat() <= 0.05F)
		{
			ItemStack var2 = new ItemStack(AdvancedTools.SmashBat, 1);
			var2.addEnchantment(Enchantment.knockback, 5 + this.rand.nextInt(5));

			if (this.rand.nextFloat() <= 0.5F)
			{
				var2.addEnchantment(Enchantment.smite, 1 + this.rand.nextInt(2));
			}

			this.entityDropItem(var2, 1.0F);
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
    @Override
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.rand.nextFloat() <= 0.2F + 0.1F * (float)var2)
		{
			this.dropItem(AdvancedTools.RedEnhancer, 1);
		}
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
    @Override
	protected void attackEntity(Entity var1, float var2)
	{
		double var3 = var1.posX - this.posX;
		double var5 = var1.posZ - this.posZ;

		if (var2 > 4.0F && var2 < 25.0F)
		{
			if (this.attackTime <= 0)
			{
				for (int var12 = 0; var12 < 5; ++var12)
				{
					EntityArrow var8 = new EntityArrow(this.worldObj, this, 1.5F);
					double var9 = var1.posY + (double)var1.getEyeHeight() - 0.699999988079071D - var8.posY;
					float var11 = MathHelper.sqrt_double(var3 * var3 + var5 * var5) * 0.2F;
					this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
					this.worldObj.spawnEntityInWorld(var8);
					var8.setThrowableHeading(var3, var9 + (double)var11, var5, 1.6F, 12.0F);
				}

				this.attackTime = 50;
			}

			this.hasAttacked = true;
		}
		else if (this.attackTime <= 0)
		{
			if (this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY)
			{
				this.attackTime = 20;
				this.attackEntityAsMob(var1);
				if(this.worldObj.isRemote)
				{
					double var7 = Math.atan2(var5, var3);
					var1.setVelocity(Math.cos(var7) * 3.0D, var1.motionY * 1.7D, Math.sin(var7) * 3.0D);
				}
			}
		}
		else if (this.attackTime > 20)
		{
			this.attackTime = 20;
		}

		this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
	}

	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
    @Override
	public ItemStack getHeldItem()
	{
		return !this.hasAttacked && this.entityToAttack != null ? subHeldItem : mainHeldItem;
	}
    static {
        subHeldItem.addEnchantment(Enchantment.knockback, 10);
    }
}
