package Nanashi.AdvancedTools.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Entity_FireZombie extends EntityZombie {
    private static final ItemStack defaultHeldItem = new ItemStack(Items.STONE_AXE, 1);

    public Entity_FireZombie(World var1) {
        super(var1);
        this.isImmuneToFire = true;
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, defaultHeldItem);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean canRenderOnFire() {
        return !this.isWet();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 2);
        } else {
            this.setFire(1);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity var1) {
        var1.setFire(5);
        return super.attackEntityAsMob(var1);
    }

    @Override
    public void onDeath(DamageSource var1) {
        super.onDeath(var1);

        if (var1.getTrueSource() instanceof EntityPlayer && this.rand.nextFloat() <= 0.05F) {
            ItemStack var2 = new ItemStack(Items.STONE_AXE, 1);

            if (this.rand.nextFloat() <= 0.5F) {
                var2.addEnchantment(Enchantments.EFFICIENCY, 1 + this.rand.nextInt(4));
            }

            if (this.rand.nextFloat() <= 0.5F) {
                var2.addEnchantment(Enchantments.UNBREAKING, 1);
            }

            this.entityDropItem(var2, 1.0F);
        }
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) {
        super.dropFewItems(var1, var2);

        if (this.rand.nextFloat() <= 0.2F + 0.1F * (float) var2) {
            this.dropItem(Nanashi.AdvancedTools.utils.Items.RedEnhancer, 1);
        }
    }
}
