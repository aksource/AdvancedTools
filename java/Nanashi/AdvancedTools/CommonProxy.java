package Nanashi.AdvancedTools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public CommonProxy(){}
	public void registerRenderInformation(){}

	public void registerTileEntitySpecialRenderer(){}

	//returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{

		return null;
	}
	public World getClientWorld()
	{
		return null;
	}
}