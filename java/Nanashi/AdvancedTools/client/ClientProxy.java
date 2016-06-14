package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.CommonProxy;
import Nanashi.AdvancedTools.entity.*;
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
}