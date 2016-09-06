package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_BBFireBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemUQBlazeBlade extends ItemUniqueArms {
    private int coolTime;
    private int saftyCnt;

    public ItemUQBlazeBlade(ToolMaterial var2) {
        super(var2);
    }

    public ItemUQBlazeBlade(ToolMaterial var2, int var3) {
        super(var2);
    }

    @Override
    public void onUpdate(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
        super.onUpdate(var1, var2, var3, var4, var5);

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
                worldIn.spawnEntityInWorld(var8);
            }

            if (! (entityLiving instanceof EntityPlayer) || !((EntityPlayer)entityLiving).capabilities.isCreativeMode) {
                this.coolTime += 20;

                if (this.coolTime > 100) {
                    if (this.coolTime > 500) {
                        this.coolTime = 500;
                    }

                    if (this.coolTime > 350) {
                        this.saftyCnt += 3;
                    } else if (this.coolTime > 200) {
                        this.saftyCnt += 2;
                    } else {
                        ++this.saftyCnt;
                    }

                    if (this.saftyCnt >= 3) {
                        if (entityLiving instanceof EntityPlayer) {
                            ((EntityPlayer) entityLiving).getFoodStats().addStats(-1, 1.0f);
                        }
                        this.saftyCnt = 0;
                    }
                } else {
                    this.saftyCnt = 0;
                }
            }

            itemStack.damageItem(1, entityLiving);
            entityLiving.swingArm(EnumHand.MAIN_HAND);
            worldIn.playSound(null, entityLiving.posX, entityLiving.posY, entityLiving.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack var1) {
        return EnumAction.BOW;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
        par3List.add("Ability : Fire Ball");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        int var4 = playerIn.getFoodStats().getFoodLevel();

        if (var4 > 6) {
//			playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
            playerIn.setActiveHand(hand);
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override//暫定処置
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        entity.setFire(4);
        return false;
    }
}
