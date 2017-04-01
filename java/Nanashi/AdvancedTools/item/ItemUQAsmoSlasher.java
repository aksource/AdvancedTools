package Nanashi.AdvancedTools.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemUQAsmoSlasher extends ItemUniqueArms {

    public ItemUQAsmoSlasher(ToolMaterial toolMaterial, int attackDamage) {
        super(toolMaterial, attackDamage);
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
        if (target instanceof EntityCreeper) {
            target.onStruckByLightning(null);
        }

        if (target instanceof EntityPig) {
            target.onStruckByLightning(null);
        }
        return super.hitEntity(itemStack, target, attacker);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int var5 = getFoodStat(entityLiving);

        if (var5 > 6) {
            int var6 = this.getMaxItemUseDuration(itemStack) - timeLeft;
            float var7 = (float) var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if (var7 >= 1.0F && var5 > 7) {
                for (int var18 = 0; var18 < 8; ++var18) {
                    double var9 = Math.PI * (double) var18 / 4.0D;
                    double var11 = entityLiving.posX + 5.5D * Math.sin(var9);
                    double var13 = entityLiving.posZ + 5.5D * Math.cos(var9);
                    double var15 = (double) worldIn.getHeight(new BlockPos(var11 - 0.5D, 0, var13 - 0.5D)).getY();
                    EntityLightningBolt var17 = new EntityLightningBolt(worldIn, var11, var15, var13, false);

                    worldIn.spawnEntity(var17);
                }

                if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
                    ((EntityPlayer) entityLiving).getFoodStats().addStats(-1, 1.0f);
                }
            } else {
                double var8 = entityLiving.posX;
                double var10 = entityLiving.posZ;
                var8 -= 6.0D * Math.sin((double) (entityLiving.rotationYaw / 180.0F) * Math.PI);
                var10 += 6.0D * Math.cos((double) (entityLiving.rotationYaw / 180.0F) * Math.PI);
                double var12 = (double) worldIn.getHeight(new BlockPos(var8 - 0.5D, 0, var10 - 0.5D)).getY();
                EntityLightningBolt var14 = new EntityLightningBolt(worldIn, var8, var12, var10, false);

                worldIn.spawnEntity(var14);

                if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
                    ((EntityPlayer) entityLiving).getFoodStats().addStats(-1, 1.0f);
                }
            }

            itemStack.damageItem(1, entityLiving);
            if (entityLiving instanceof EntityPlayer) {
                entityLiving.swingArm(EnumHand.MAIN_HAND);
            }
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
        }
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack var1) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add("Ability : Lightning Caller");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        return setActiveHand(worldIn, playerIn, hand);
    }
}
