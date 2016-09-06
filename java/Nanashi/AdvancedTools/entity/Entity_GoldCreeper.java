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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Entity_GoldCreeper extends EntityCreeper {

    private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean>createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
    public Entity_GoldCreeper(World var1) {
        super(var1);
        this.experienceValue = 40;
        changeAITask();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @SuppressWarnings("unchecked")
    private void changeAITask() {
        EntityAITasks.EntityAITaskEntry oldEntry = null;
        EntityAITasks.EntityAITaskEntry replacingEntry = new EntityAITasks(this.worldObj.theProfiler)
                .new EntityAITaskEntry(1, new EntityAIHurtByTarget(this, false));
        for (EntityAITasks.EntityAITaskEntry entityAITaskEntry : this.targetTasks.taskEntries) {
            if (entityAITaskEntry.priority == 1 && entityAITaskEntry.action instanceof EntityAINearestAttackableTarget) {
                oldEntry = entityAITaskEntry;
            }
        }
        this.targetTasks.taskEntries.remove(oldEntry);
        this.targetTasks.taskEntries.add(replacingEntry);
    }

    @Override
    public void onUpdate() {
        if (this.getAITarget() == null && this.getPowered()) {
            this.dataManager.set(POWERED, Boolean.valueOf(false));
        }


        super.onUpdate();
        Potion speed = Potion.getPotionFromResourceLocation("speed");
        Potion jump_boost = Potion.getPotionFromResourceLocation("jump_boost");
        if (!this.isPotionActive(speed) && this.getPowered()) {
            this.addPotionEffect(new PotionEffect(speed, 20, 1));
            this.addPotionEffect(new PotionEffect(jump_boost, 20, 1));
        }
    }


    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float var2) {
        if (!this.getPowered()) {
            this.dataManager.set(POWERED, Boolean.valueOf(true));
        }
        if (!this.worldObj.isRemote) {
            if (damageSource.getEntity() instanceof EntityPlayer && var2 > 0) {
                boolean hasLuckLuck = false;
                ItemStack heldItemMainhand = ((EntityPlayer) damageSource.getEntity()).getHeldItemMainhand();

                if (heldItemMainhand != null) {
                    hasLuckLuck = heldItemMainhand.getItem() == AdvancedTools.LuckLuck;
                }

                if (hasLuckLuck || this.rand.nextFloat() < 0.3F && this.getHealth() > 0) {
                    this.dropItem(Items.GOLD_NUGGET, 1);
                }
            }
        }
        return super.attackEntityFrom(damageSource, var2);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (damageSource.getEntity() instanceof EntityPlayer) {
            boolean hasLuckLuck = false;
            ItemStack heldItemMainhand = ((EntityPlayer) damageSource.getEntity()).getHeldItemMainhand();

            if (heldItemMainhand != null) {
                hasLuckLuck = heldItemMainhand.getItem() == AdvancedTools.LuckLuck;
            }

            if (hasLuckLuck || this.rand.nextFloat() < 0.3F) {
                this.dropItem(AdvancedTools.list.get(this.rand.nextInt(29)), 1);
            }
        }
    }

}
