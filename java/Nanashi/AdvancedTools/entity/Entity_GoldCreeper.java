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

import javax.annotation.Nonnull;

public class Entity_GoldCreeper extends EntityCreeper {

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

    private void changeAITask() {
        EntityAITasks.EntityAITaskEntry oldEntry = null;
        EntityAITasks.EntityAITaskEntry replacingEntry = new EntityAITasks(this.getEntityWorld().theProfiler)
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
            this.dataManager.set(POWERED, false);
        }


        super.onUpdate();
        Potion speed = Potion.getPotionFromResourceLocation("speed");
        Potion jump_boost = Potion.getPotionFromResourceLocation("jump_boost");
        if (speed != null && jump_boost != null && !this.isPotionActive(speed) && this.getPowered()) {
            this.addPotionEffect(new PotionEffect(speed, 20, 1));
            this.addPotionEffect(new PotionEffect(jump_boost, 20, 1));
        }
    }


    @Override
    public boolean attackEntityFrom(@Nonnull  DamageSource damageSource, float amount) {
        if (!this.getPowered()) {
            this.dataManager.set(POWERED, true);
        }
        if (!this.getEntityWorld().isRemote) {
            if (damageSource.getEntity() instanceof EntityPlayer && amount > 0) {
                boolean hasLuckLuck = false;
                ItemStack heldItemMainhand = ((EntityPlayer) damageSource.getEntity()).getHeldItemMainhand();

                if (!heldItemMainhand.isEmpty()) {
                    hasLuckLuck = heldItemMainhand.getItem() == AdvancedTools.LuckLuck;
                }

                if (hasLuckLuck || this.rand.nextFloat() < 0.3F && this.getHealth() > 0) {
                    this.dropItem(Items.GOLD_NUGGET, 1);
                }
            }
        }
        return super.attackEntityFrom(damageSource, amount);
    }

    @Override
    public void onDeath(@Nonnull DamageSource damageSource) {
        super.onDeath(damageSource);

        if (damageSource.getEntity() instanceof EntityPlayer) {
            boolean hasLuckLuck = false;
            ItemStack heldItemMainhand = ((EntityPlayer) damageSource.getEntity()).getHeldItemMainhand();

            if (!heldItemMainhand.isEmpty()) {
                hasLuckLuck = heldItemMainhand.getItem() == AdvancedTools.LuckLuck;
            }

            if (hasLuckLuck || this.rand.nextFloat() < 0.3F) {
                this.dropItem(AdvancedTools.list.get(this.rand.nextInt(29)), 1);
            }
        }
    }

}
