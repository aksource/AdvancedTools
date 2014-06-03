package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Entity_FireZombie extends EntityZombie
{
	private static final ItemStack defaultHeldItem = new ItemStack(Items.stone_axe, 1);

	public Entity_FireZombie(World var1)
	{
		super(var1);
		this.isImmuneToFire = true;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRenderOnFire()
	{
		return !this.isWet();
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.drown, 2);
		} else {
			this.setFire(1);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity var1)
	{
		var1.setFire(5);
		return super.attackEntityAsMob(var1);
	}

	@Override
	protected void attackEntity(Entity var1, float var2)
	{
		if (this.attackTime <= 0 && var2 < 2.0F && var1.boundingBox.maxY > this.boundingBox.minY
				&& var1.boundingBox.minY < this.boundingBox.maxY) {
			var1.setFire(5);
			this.attackTime = 20;
			this.attackEntityAsMob(var1);
		}
	}

	@Override
	public void onDeath(DamageSource var1)
	{
		super.onDeath(var1);

		if (var1.getEntity() instanceof EntityPlayer && this.rand.nextFloat() <= 0.05F) {
			ItemStack var2 = new ItemStack(Items.stone_axe, 1);

			if (this.rand.nextFloat() <= 0.5F) {
				var2.addEnchantment(Enchantment.efficiency, 1 + this.rand.nextInt(4));
			}

			if (this.rand.nextFloat() <= 0.5F) {
				var2.addEnchantment(Enchantment.unbreaking, 1);
			}

			this.entityDropItem(var2, 1.0F);
		}
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		if (this.rand.nextFloat() <= 0.2F + 0.1F * (float) var2) {
			this.dropItem(AdvancedTools.RedEnhancer, 1);
		}
	}

	@Override
	public ItemStack getHeldItem()
	{
		return defaultHeldItem;
	}
}
