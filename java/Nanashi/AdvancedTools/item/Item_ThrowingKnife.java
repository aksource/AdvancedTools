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

public class Item_ThrowingKnife extends Item {
    public boolean addPoison;

    public Item_ThrowingKnife(boolean var2) {
        super();
        this.maxStackSize = 16;
        this.addPoison = var2;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer, EnumHand hand) {
        if (entityPlayer.capabilities.isCreativeMode || entityPlayer.inventory.hasItemStack(itemStack)) {
            Entity_ThrowingKnife var4 = new Entity_ThrowingKnife(world, entityPlayer, 1.0F, this.addPoison);
            world.playSound(entityPlayer, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL,
                    1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1.0f * 0.5F);
            --itemStack.stackSize;

            if (!world.isRemote) {
                world.spawnEntityInWorld(var4);
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
        if (this.addPoison && var3 instanceof EntityPlayer) {
            var2.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("poison"), 60, 1));
            --var1.stackSize;

            if (!((EntityPlayer) var3).inventory.addItemStackToInventory(new ItemStack(AdvancedTools.ThrowingKnife))) {
                var3.dropItem(AdvancedTools.ThrowingKnife, 1);
            }
        }

        return true;
    }
}
