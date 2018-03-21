package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_BBFireBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemUQBlazeBlade extends ItemUniqueArms {
    private int coolTime;
    private int safetyCnt;

    public ItemUQBlazeBlade(ToolMaterial toolMaterial, int attackDamage) {
        super(toolMaterial, attackDamage);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int var4, boolean var5) {
        super.onUpdate(itemStack, world, entity, var4, var5);

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
                var7 = 1.0F;
            }

            Entity_BBFireBall var8 = new Entity_BBFireBall(worldIn, entityLiving, var7 * 2.0F, var7 == 1.0F);

            if (!worldIn.isRemote) {
                worldIn.spawnEntity(var8);
            }

            if (! (entityLiving instanceof EntityPlayer) || !((EntityPlayer)entityLiving).capabilities.isCreativeMode) {
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

            itemStack.damageItem(1, entityLiving);
            entityLiving.swingArm(EnumHand.MAIN_HAND);
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
        }
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add("Ability : Fire Ball");
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
        return setActiveHand(worldIn, playerIn, hand);
    }

    @Override//暫定処置
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        entity.setFire(4);
        return false;
    }
}
