package Nanashi.AdvancedTools.client;

import net.minecraft.world.World;
import Nanashi.AdvancedTools.CommonProxy;
import Nanashi.AdvancedTools.Entity_BBFireBall;
import Nanashi.AdvancedTools.Entity_FireZombie;
import Nanashi.AdvancedTools.Entity_GoldCreeper;
import Nanashi.AdvancedTools.Entity_HighSkeleton;
import Nanashi.AdvancedTools.Entity_HighSpeedCreeper;
import Nanashi.AdvancedTools.Entity_IHFrozenMob;
import Nanashi.AdvancedTools.Entity_PGPowerBomb;
import Nanashi.AdvancedTools.Entity_SBWindEdge;
import Nanashi.AdvancedTools.Entity_SkeletonSniper;
import Nanashi.AdvancedTools.Entity_ThrowingKnife;
import Nanashi.AdvancedTools.Entity_ZombieWarrior;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
	    RenderingRegistry.registerEntityRenderingHandler(Entity_BBFireBall.class, new Render_UQMagics());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_IHFrozenMob.class, new Render_UQFreezer());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_PGPowerBomb.class, new Render_UQMagics());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_SBWindEdge.class, new Render_UQMagics());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_ThrowingKnife.class, new RenderThrowingKnife());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_HighSkeleton.class, new RenderAdvSkeleton());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_SkeletonSniper.class, new RenderAdvSkeleton());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_ZombieWarrior.class, new RenderAdvZombie());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_FireZombie.class, new RenderAdvZombie());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_HighSpeedCreeper.class, new RenderHCreeper());
	    RenderingRegistry.registerEntityRenderingHandler(Entity_GoldCreeper.class, new RenderGCreeper());
	}

	@Override
	public void registerTileEntitySpecialRenderer()
	{

	}

	@Override
	public World getClientWorld()
	{
	return FMLClientHandler.instance().getClient().theWorld;
	}
}