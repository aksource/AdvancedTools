package Nanashi.AdvancedTools.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Entity_SkeletonSniper extends EntitySkeleton {
    private final ItemStack mainHeldItem = new ItemStack(Items.BOW);
    private final ItemStack subHeldItem = new ItemStack(Nanashi.AdvancedTools.utils.Items.SmashBat);
    private EntityAIAttackRangedBow aiArrowAttackSniper = new EntityAIAttackRangedBow(this, 1.0D, 30, 15.0F);

    public Entity_SkeletonSniper(World var1) {
        super(var1);
        this.experienceValue = 7;
        subHeldItem.addEnchantment(Enchantments.KNOCKBACK, 10);
        ObfuscationReflectionHelper.setPrivateValue(AbstractSkeleton.class, this, aiArrowAttackSniper, 1);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D/*2.0D*/);
    }

    @Override
    public void onDeath(DamageSource var1) {
        super.onDeath(var1);

        if (var1.getTrueSource() instanceof EntityPlayer && this.rand.nextFloat() <= 0.05F) {
            ItemStack var2 = new ItemStack(Nanashi.AdvancedTools.utils.Items.SmashBat, 1);
            var2.addEnchantment(Enchantments.KNOCKBACK, 5 + this.rand.nextInt(5));

            if (this.rand.nextFloat() <= 0.5F) {
                var2.addEnchantment(Enchantments.SMITE, 1 + this.rand.nextInt(2));
            }

            this.entityDropItem(var2, 1.0F);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.getEntityWorld().getTotalWorldTime() % 10L == 0L) {
            if (this.nearTarget()) {
                this.setCurrentItemIfDiff(subHeldItem);
            } else {
                this.setCurrentItemIfDiff(mainHeldItem);
            }
        }
    }

    private void setCurrentItemIfDiff(ItemStack itemStack) {
        ItemStack itemStack1 = this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
        if (itemStack != null && !itemStack.isItemEqual(itemStack1)) {
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemStack);
        }
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) {
        super.dropFewItems(var1, var2);

        if (this.rand.nextFloat() <= 0.2F + 0.1F * (float) var2) {
            this.dropItem(Nanashi.AdvancedTools.utils.Items.RedEnhancer, 1);
        }
    }

    public boolean nearTarget() {
        EntityLivingBase target = this.getAttackTarget();
        return target != null && this.getDistanceToEntity(target) < 4.0D;
    }
}
