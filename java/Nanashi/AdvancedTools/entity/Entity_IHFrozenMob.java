package Nanashi.AdvancedTools.entity;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Entity_IHFrozenMob extends Entity
{
	EntityLiving frozen;
	EntityPlayer player;
	EntityAITasks entityTasks;
	int FrozenRest;

	public Entity_IHFrozenMob(World var1)
	{
		super(var1);
	}

	public Entity_IHFrozenMob(World var1, EntityLiving var2, EntityPlayer var3)
	{
		super(var1);
		if (var2 == null){
			this.setDead();
			this.frozen = null;
		}else{
			this.frozen = var2;
			this.player = var3;
			this.setSize(this.frozen.width, this.frozen.height);
			this.frozen.ridingEntity = null;
			this.setPosition(this.frozen.posX, this.frozen.posY, this.frozen.posZ);
			this.rotationYaw = this.frozen.rotationYaw;

			if (this.frozen.isBurning()){
				this.setDead();
				this.frozen.setFire(0);
				this.frozen = null;
			}else{
				this.frozen.setFire(0);
//				this.originalMethodDoubleEntity(var1);
				if (this.frozen instanceof EntityMob){
//					this.altenativeMethodForAI();
					this.originalMethodForAI();
				}
				this.FrozenRest = 200;
			}
		}
	}
//	private void originalMethodDoubleEntity(World var1)
//	{
//		List var3 = var1.getEntitiesWithinAABB(Entity_IHFrozenMob.class, this.boundingBox.expand(1.0D, 1.0D, 1.0D));
//
//		for (Object object : var3)
//		{
//			if (object instanceof Entity_IHFrozenMob)
//			{
//				this.setDead();
//				this.frozen = null;
//				return;
//			}
//		}
//	}

	private void originalMethodForAI()
	{
		this.entityTasks = this.frozen.tasks;
		ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this.frozen, new EntityAITasks(this.worldObj.theProfiler), 7);
	}

//	private void altenativeMethodForAI()
//	{
//		this.frozen.attackTime = 200;
//		this.frozen.addPotionEffect(new PotionEffect(15,200));
//		this.frozen.setAttackTarget(this.frozen);
//		this.frozen.setLastAttacker(this.frozen);
//	}

	public void onUpdate()
	{
		if(!this.worldObj.isRemote){
			--this.FrozenRest;

			if (this.frozen == null){
				this.setDead();
			}else if (!this.frozen.isBurning() && this.FrozenRest >0 && (this.frozen.hurtTime == 0 || this.FrozenRest >= 190)){
				if (this.frozen.getHealth() > 0 && !(this.frozen instanceof EntityEnderman)){
					this.frozen.setPosition(this.posX, this.frozen.posY, this.posZ);
					this.rotationYaw = this.frozen.rotationYaw;
					this.frozen.onGround = false;
					if (this.frozen instanceof EntityMob){
						((EntityMob)this.frozen).attackTime = 20;
						this.frozen.setAttackTarget(this.frozen);
						this.frozen.setLastAttacker(this.frozen);
					}

					for (int var1 = 0; var1 < 2; ++var1){
						double var2 = this.rand.nextDouble() * (double)this.width;
						double var4 = this.rand.nextDouble() * Math.PI * 1.0D;
						double var6 = this.posX + var2 * Math.sin(var4);
						double var8 = this.posY + (double)this.height * this.rand.nextDouble();
						double var10 = this.posZ + var2 * Math.cos(var4);
						this.worldObj.spawnParticle("explode", var6, var8, var10, 0.0D, 0.0D, 0.0D);
					}
				}else{
					this.setDead();
				}
			}else{
				this.setDead();
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.glass", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	 public void setDead()
	 {
		 if(this.entityTasks != null){
			 ObfuscationReflectionHelper.setPrivateValue(EntityLiving.class, this.frozen, this.entityTasks, 7);
			 this.frozen.setAttackTarget(this.player);
			 this.frozen.setLastAttacker(this.player);
		 }
		 super.setDead();
	 }

	 public float getBrightness(float var1)
	 {
		 return 1.0f;
	 }

	 public boolean canBeCollidedWith()
	 {
		 return false;
	 }
	 protected void entityInit() {}
	 protected void readEntityFromNBT(NBTTagCompound var1) {
		 this.FrozenRest = var1.getInteger("frozenRest");
	 }
	 protected void writeEntityToNBT(NBTTagCompound var1) {
		 var1.setInteger("frozenRest", FrozenRest);
	 }
}
