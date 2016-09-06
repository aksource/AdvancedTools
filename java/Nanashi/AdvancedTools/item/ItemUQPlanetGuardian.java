package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.entity.Entity_PGPowerBomb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemUQPlanetGuardian extends ItemUniqueArms {
    public ItemUQPlanetGuardian(ToolMaterial var2) {
        super(var2);
    }

    public ItemUQPlanetGuardian(ToolMaterial var2, int var3) {
        super(var2);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int var5 = getFoodStat(entityLiving);

        if (var5 > 6) {
            int var6 = this.getMaxItemUseDuration(stack) - timeLeft;
            float var7 = (float) var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if ((double) var7 < 0.1D) {
                return;
            }

            if (var7 > 1.0F) {
                var7 = 1.0F;
            }

            Entity_PGPowerBomb var8 = new Entity_PGPowerBomb(worldIn, entityLiving, var7);

            if (!worldIn.isRemote) {
                worldIn.spawnEntityInWorld(var8);
            }

            if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
                ((EntityPlayer) entityLiving).getFoodStats().addStats(-1, 1.0f);
            }

            stack.damageItem(1, entityLiving);
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
        par3List.add("Ability : Ground Banish");
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
}
