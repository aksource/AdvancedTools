package Nanashi.AdvancedTools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import ak.MultiToolHolders.ItemMultiToolHolder;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerClickHook
{
	@SubscribeEvent
	public void clickEvent(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		ItemStack holdItem = event.entityPlayer.getCurrentEquippedItem();
		if (event.action == Action.LEFT_CLICK_BLOCK && holdItem != null) {
			if (AdvancedTools.hasMultiToolHolder && holdItem.getItem() instanceof ItemMultiToolHolder) {
				holdItem = ((ItemMultiToolHolder) holdItem.getItem()).tools.getStackInSlot(((ItemMultiToolHolder) holdItem.getItem()).SlotNum);
			}
			if (holdItem != null && holdItem.getItem() instanceof ItemUGTool) {
				ItemUGTool ugTool = (ItemUGTool) holdItem.getItem();
				ugTool.side = event.face;
			}
		}
	}
}