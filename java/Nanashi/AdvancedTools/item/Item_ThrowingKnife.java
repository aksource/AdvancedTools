package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_ThrowingKnife;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Item_ThrowingKnife extends Item {
    public boolean addPoison;

    /**
     * @param addPoison 毒付きかそうでないか
     */
    public Item_ThrowingKnife(boolean addPoison) {
        super();
        this.maxStackSize = 16;
        this.addPoison = addPoison;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItemStack(itemStack)) {
            worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,
                    1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0f * 0.5F);
            itemStack.shrink(1);

            if (!worldIn.isRemote) {
                Entity_ThrowingKnife entityThrowingKnife = new Entity_ThrowingKnife(worldIn, playerIn, 1.0F, this.addPoison);
                entityThrowingKnife.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0F, 0.7F, 1.0F);
                worldIn.spawnEntity(entityThrowingKnife);
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker) {
        if (this.addPoison && attacker instanceof EntityPlayer) {
            Potion poison = Potion.getPotionFromResourceLocation("poison");
            if (poison != null) {
                target.addPotionEffect(new PotionEffect(poison, 60, 1));
            }
            itemStack.shrink(1);

            if (!((EntityPlayer) attacker).inventory.addItemStackToInventory(new ItemStack(AdvancedTools.ThrowingKnife))) {
                attacker.dropItem(AdvancedTools.ThrowingKnife, 1);
            }
        }

        return true;
    }
}
