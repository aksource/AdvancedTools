package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class Entity_GoldCreeper extends EntityCreeper
{
	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	private int lastActiveTime;

	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;

	/** Explosion radius for this creeper. */
	private int explosionRadius = 3;
	public Entity_GoldCreeper(World var1)
	{
		super(var1);
		this.experienceValue = 40;
        changeAITask();
//		this.tasks.addTask(1, new EntityAISwimming(this));
//		this.tasks.addTask(2, new Entity_AIGCreeperSwell(this));
//        this.tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate()
//        {
//            public boolean func_179958_a(Entity p_179958_1_)
//            {
//                return p_179958_1_ instanceof EntityOcelot;
//            }
//            public boolean apply(Object p_apply_1_)
//            {
//                return this.func_179958_a((Entity)p_apply_1_);
//            }
//        }, 6.0F, 1.0D, 1.2D));
//		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.25F, false));
//		this.tasks.addTask(5, new EntityAIWander(this, 0.2F));
//		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//		this.tasks.addTask(6, new EntityAILookIdle(this));
//		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}
	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}
    @SuppressWarnings("unchecked")
    private void changeAITask() {
        for (EntityAITasks.EntityAITaskEntry entityAITaskEntry : (List<EntityAITasks.EntityAITaskEntry>)this.targetTasks.taskEntries) {
            if (entityAITaskEntry.priority == 1 && entityAITaskEntry.action instanceof EntityAINearestAttackableTarget) {
                entityAITaskEntry.action = new EntityAIHurtByTarget(this, false);
            }
        }
    }

//	@Override
//	public void fall(float par1, float par2)
//	{
//		super.fall(par1, par2);
//		this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + par1 * 1.5F);
//
//		if (this.timeSinceIgnited > this.fuseTime - 5)
//		{
//			this.timeSinceIgnited = this.fuseTime - 5;
//		}
//	}
//	@Override
//	protected void entityInit()
//	{
//		super.entityInit();
//		this.dataWatcher.addObject(16,(byte) - 1);
//		this.dataWatcher.addObject(17, (byte)0);
//	}
//	@Override
//	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
//	{
//		super.writeEntityToNBT(par1NBTTagCompound);
//
//		if (this.dataWatcher.getWatchableObjectByte(17) == 1)
//		{
//			par1NBTTagCompound.setBoolean("powered", true);
//		}
//
//		par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
//		par1NBTTagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
//	}
//
//	@Override
//	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
//	{
//		super.readEntityFromNBT(par1NBTTagCompound);
//		this.dataWatcher.updateObject(17, (byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0));
//
//		if (par1NBTTagCompound.hasKey("Fuse"))
//		{
//			this.fuseTime = par1NBTTagCompound.getShort("Fuse");
//		}
//
//		if (par1NBTTagCompound.hasKey("ExplosionRadius"))
//		{
//			this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
//		}
//	}
	@Override
	public void onUpdate()
	{
		if (this.getAITarget() == null && this.getPowered()) {
			this.dataWatcher.updateObject(17, (byte)0);
		}

//		if (this.isEntityAlive())
//		{
//			this.lastActiveTime = this.timeSinceIgnited;
//			int var1 = this.getCreeperState();
//
//			if (var1 > 0 && this.timeSinceIgnited == 0)
//			{
//				this.playSound("random.fuse", 1.0F, 0.5F);
//			}
//
//			this.timeSinceIgnited += var1;
//
//			if (this.timeSinceIgnited < 0)
//			{
//				this.timeSinceIgnited = 0;
//			}
//
//			if (this.timeSinceIgnited >= this.fuseTime)
//			{
//				this.timeSinceIgnited = this.fuseTime;
//
//				if (!this.worldObj.isRemote)
//				{
//					boolean var2 = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
//
//					if (this.getPowered())
//					{
//						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), var2);
//					}
//					else
//					{
//						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, var2);
//					}
//
//					this.setDead();
//				}
//			}
//		}
		super.onUpdate();

		if (!this.isPotionActive(Potion.moveSpeed) && this.getPowered())
		{
			this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1));
			this.addPotionEffect(new PotionEffect(Potion.jump.id, 20, 1));
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource var1, float var2)
	{
		if (!this.getPowered())
		{
			this.dataWatcher.updateObject(17, (byte)1);
		}
		if (!this.worldObj.isRemote)
		{
			if (var1.getEntity() instanceof EntityPlayer && var2 > 0)
			{
				boolean var3 = false;
				ItemStack var4 = ((EntityPlayer)var1.getEntity()).getCurrentEquippedItem();

				if (var4 != null)
				{
					var3 = var4.getItem() == AdvancedTools.LuckLuck;
				}

				if (var3 || this.rand.nextFloat() < 0.3F && this.getHealth() > 0)
				{
					this.dropItem(Items.gold_nugget, 1);
				}
			}
		}
		return super.attackEntityFrom(var1, var2);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
//	@Override
//	protected String getHurtSound()
//	{
//		return "mob.creeper";
//	}

	/**
	 * Returns the sound this mob makes on death.
	 */
//	@Override
//	protected String getDeathSound()
//	{
//		return "mob.creeperdeath";
//	}

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
			ItemStack var3 = ((EntityPlayer)var1.getEntity()).getHeldItem();

			if (var3 != null)
			{
				var2 = var3.getItem() == AdvancedTools.LuckLuck;
			}

			if (var2 || this.rand.nextFloat() < 0.3F)
			{
				this.dropItem(AdvancedTools.list.get(this.rand.nextInt(29)), 1);
			}
		}
	}
//	@Override
//	public boolean attackEntityAsMob(Entity par1Entity)
//	{
//		return true;
//	}
//	public boolean getPowered()
//	{
//		return this.dataWatcher.getWatchableObjectByte(17) == 1;
//	}
//	@SideOnly(Side.CLIENT)
//	public float getCreeperFlashIntensity(float par1)
//	{
//		return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * par1) / (float)(this.fuseTime - 2);
//	}

//	public int getCreeperState()
//	{
//		return this.dataWatcher.getWatchableObjectByte(16);
//	}
//
//	public void setCreeperState(int par1)
//	{
//		this.dataWatcher.updateObject(16, (byte)par1);
//	}
//
//	@Override
//	public void onStruckByLightning(EntityLightningBolt var1)
//	{
//		super.onStruckByLightning(var1);
//	}
}
