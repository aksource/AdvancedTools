package Nanashi.AdvancedTools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Entity_HighSkeleton extends EntitySkeleton
{
	public Entity_HighSkeleton(World var1)
	{
		super(var1);
		this.experienceValue = 10;
	}
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

		if (var1.getEntity() instanceof EntityPlayer)
		{
			boolean var2 = false;
			ItemStack var3 = ((EntityPlayer)var1.getEntity()).getCurrentEquippedItem();

			if (var3 != null)
			{
				var2 = var3.getItem() == AdvancedTools.LuckLuck && this.rand.nextFloat() < 0.5F;
			}

			if (var2 || this.rand.nextFloat() < 0.05F)
			{
				this.dropItem(AdvancedTools.CrossBow, 1);
			}
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.rand.nextFloat() <= 0.1F + 0.1F * (float)var2)
		{
			this.dropItem(AdvancedTools.BlueEnhancer, 1);
		}
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity var1, float var2)
	{
		if (var2 < 15.0F)
		{
			double var3 = var1.posX - this.posX;
			double var5 = var1.posZ - this.posZ;

			if (this.attackTime <= 10 && this.attackTime % 2 == 0)
			{
				EntityArrow var7 = new EntityArrow(this.worldObj, this, 1.0F);
				double var8 = var1.posY + (double)var1.getEyeHeight() - 0.699999988079071D - var7.posY;
				float var10 = MathHelper.sqrt_double(var3 * var3 + var5 * var5) * 0.2F;
				this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
				this.worldObj.spawnEntityInWorld(var7);
				var7.setThrowableHeading(var3, var8 + (double)var10, var5, 1.6F, 12.0F);

				if (this.attackTime <= 0)
				{
					this.attackTime = 30;
				}
			}

			this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
			this.hasAttacked = true;
		}
		else
		{
			this.attackTime = 20;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound var1)
	{
		super.writeEntityToNBT(var1);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound var1)
	{
		super.readEntityFromNBT(var1);
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected Item getDropItem()
	{
		return Items.arrow;
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere()
	{
		return this.posY < 50.0D && super.getCanSpawnHere();
	}
}
