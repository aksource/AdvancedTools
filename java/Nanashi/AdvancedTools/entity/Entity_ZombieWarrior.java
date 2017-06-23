package Nanashi.AdvancedTools.entity;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Entity_ZombieWarrior extends EntityZombie {

    public Entity_ZombieWarrior(World var1) {
        super(var1);
        this.experienceValue = 10;
        this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(AdvancedTools.DevilSword, 1));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    }

    @Override
    public void onDeath(DamageSource var1) {
        super.onDeath(var1);

        if (var1.getTrueSource() instanceof EntityPlayer) {
            boolean var2 = false;
            ItemStack heldItemMainhand = ((EntityPlayer) var1.getTrueSource()).getHeldItemMainhand();

            if (!heldItemMainhand.isEmpty()) {
                var2 = heldItemMainhand.getItem() == AdvancedTools.LuckLuck && this.rand.nextFloat() < 0.5F;
            }

            if (var2 || this.rand.nextFloat() < 0.05F) {
                this.dropItem(AdvancedTools.DevilSword, 1);
            }
        }
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) {
        super.dropFewItems(var1, var2);

        if (this.rand.nextFloat() <= 0.1F + 0.1F * (float) var2) {
            this.dropItem(AdvancedTools.BlueEnhancer, 1);
        }
    }

    @Override
    public void knockBack(Entity var1, float var2, double var3, double var5) {
        this.motionY += 0.4000000059604645D;

        if (this.motionY > 0.4000000059604645D) {
            this.motionY = 0.4000000059604645D;
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.posY < 50.0D && super.getCanSpawnHere();
    }
}
