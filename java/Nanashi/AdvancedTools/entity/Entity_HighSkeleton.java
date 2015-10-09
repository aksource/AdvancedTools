package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Entity_HighSkeleton extends EntitySkeleton {
    private EntityAIArrowAttack aiArrowAttackHigh = new EntityAIArrowAttack(this, 1.0D, 20, 20, 15.0F);

    public Entity_HighSkeleton(World var1) {
        super(var1);
        this.experienceValue = 10;
        ObfuscationReflectionHelper.setPrivateValue(EntitySkeleton.class, this, aiArrowAttackHigh, 0);
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D/*2.0D*/);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource var1) {
        super.onDeath(var1);

        if (var1.getEntity() instanceof EntityPlayer) {
            boolean var2 = false;
            ItemStack var3 = ((EntityPlayer) var1.getEntity()).getCurrentEquippedItem();

            if (var3 != null) {
                var2 = var3.getItem() == AdvancedTools.LuckLuck && this.rand.nextFloat() < 0.5F;
            }

            if (var2 || this.rand.nextFloat() < 0.05F) {
                this.dropItem(AdvancedTools.CrossBow, 1);
            }
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    @Override
    protected void dropFewItems(boolean var1, int var2) {
        super.dropFewItems(var1, var2);

        if (this.rand.nextFloat() <= 0.1F + 0.1F * (float) var2) {
            this.dropItem(AdvancedTools.BlueEnhancer, 1);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        return this.posY < 50.0D && super.getCanSpawnHere();
    }
}
