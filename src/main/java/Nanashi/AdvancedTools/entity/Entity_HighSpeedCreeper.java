package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Entity_HighSpeedCreeper extends EntityCreeper {
    public Entity_HighSpeedCreeper(World var1) {
        super(var1);
        this.experienceValue = 15;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.365D);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        Potion speed = Potion.getPotionFromResourceLocation("speed");
        if (speed != null && !this.isPotionActive(speed) && this.getHealth() <= 10) {
            this.addPotionEffect(new PotionEffect(speed, 20, 1));
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damageSource, float amount) {
        return !damageSource.damageType.equals("arrow") && super.attackEntityFrom(damageSource, amount);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        super.dropFewItems(wasRecentlyHit, lootingModifier);

        if (this.rand.nextFloat() <= 0.25F + 0.1F * (float) lootingModifier) {
            this.dropItem(AdvancedTools.RedEnhancer, 1);
        }

        if (this.rand.nextFloat() <= 0.1F + 0.1F * (float) lootingModifier) {
            this.dropItem(AdvancedTools.BlueEnhancer, 1);
        }
    }
}
