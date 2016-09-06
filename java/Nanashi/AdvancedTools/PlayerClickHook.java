package Nanashi.AdvancedTools;

import Nanashi.AdvancedTools.item.ItemUGTool;
import ak.MultiToolHolders.ItemMultiToolHolder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerClickHook {
    @SubscribeEvent
    public void clickEvent(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack holdItem = event.getEntityPlayer().getHeldItemMainhand();
        if (holdItem != null) {
            if (AdvancedTools.hasMultiToolHolder && holdItem.getItem() instanceof ItemMultiToolHolder) {
                holdItem = ((ItemMultiToolHolder) holdItem.getItem()).getInventoryFromItemStack(holdItem).getStackInSlot(ItemMultiToolHolder.getSlotNumFromItemStack(holdItem));
            }
            if (holdItem != null && holdItem.getItem() instanceof ItemUGTool) {
                ItemUGTool ugTool = (ItemUGTool) holdItem.getItem();
				ugTool.setSide(holdItem, event.getFace());
            }
        }
    }
}