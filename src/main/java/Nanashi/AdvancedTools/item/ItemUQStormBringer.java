package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_SBWindEdge;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemUQStormBringer extends ItemUniqueArms {
    private int coolTime;
    private int safetyCnt;

    public ItemUQStormBringer(ToolMaterial toolMaterial, int attackDamage) {
        super(toolMaterial, attackDamage);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isHand) {
        super.onUpdate(itemStack, world, entity, slot, isHand);

        if (this.coolTime > 0) {
            --this.coolTime;
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int var5 = getFoodStat(entityLiving);

        if (var5 > 6) {
            int var6 = this.getMaxItemUseDuration(itemStack) - timeLeft;
            float var7 = (float) var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if (var7 > 1.0F) {
                int var9;
                double var12;

                for (int var8 = 0; var8 < 3; ++var8) {
                    for (var9 = 0; var9 < 100; ++var9) {
//                        double var10 = (double) entityLiving.rotationYaw / 180.0D * Math.PI;
//                        var12 = (double) (-entityLiving.rotationPitch) / 180.0D * Math.PI;
                        float var14 = 0.0628F * (float) var9;
                        double var15 = 0.9D * Math.cos((double) var14);
                        double var17 = 0.9D * -Math.sin((double) var14);
                        worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entityLiving.posX, entityLiving.posY - 0.85D + 0.5D * (double) var8, entityLiving.posZ, var15, 0.0D, var17);
                    }
                }

                List<EntityLivingBase> var19 = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, entityLiving.getEntityBoundingBox().expand(8.0D, 2.0D, 8.0D));

                for (EntityLivingBase entityLivingBase : var19) {
                    if (entityLivingBase != entityLiving) {
                        DamageSource var11 = DamageSource.causeMobDamage(entityLiving);
                        entityLivingBase.attackEntityFrom(var11, 0);
                        var12 = entityLivingBase.posX - entityLiving.posX;
                        double var22 = entityLivingBase.posZ - entityLiving.posZ;
                        double var16 = Math.atan2(var22, var12);
                        entityLivingBase.addVelocity(Math.cos(var16) * 8.0D, entityLivingBase.motionY * 1.7D, Math.sin(var16) * 8.0D);
                    }
                }
            } else {
                Entity_SBWindEdge var20 = new Entity_SBWindEdge(worldIn, entityLiving, var7 * 2.0F);

                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(var20);
                }
            }

            itemStack.damageItem(1, entityLiving);
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);

            if (!(entityLiving instanceof EntityPlayer) || !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
                this.coolTime += 20;

                if (this.coolTime > 100) {
                    if (this.coolTime > 500) {
                        this.coolTime = 500;
                    }

                    if (this.coolTime > 350) {
                        this.safetyCnt += 3;
                    } else if (this.coolTime > 200) {
                        this.safetyCnt += 2;
                    } else {
                        ++this.safetyCnt;
                    }

                    if (this.safetyCnt >= 3) {
                        if (entityLiving instanceof EntityPlayer) {
                            ((EntityPlayer) entityLiving).getFoodStats().addStats(-1, 1.0f);
                        }
                        this.safetyCnt = 0;
                    }
                } else {
                    this.safetyCnt = 0;
                }
            }

            entityLiving.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.BOW;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Ability : Gale Impact");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        return setActiveHand(worldIn, playerIn, hand);
    }
}
