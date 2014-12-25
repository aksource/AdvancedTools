package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.CommonProxy;
import Nanashi.AdvancedTools.entity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import static Nanashi.AdvancedTools.AdvancedTools.*;

public class ClientProxy extends CommonProxy
{
    private Minecraft mc = Minecraft.getMinecraft();
	@Override
	public void registerRenderInformation()
	{
        RenderManager renderManager = mc.getRenderManager();
        RenderingRegistry.registerEntityRenderingHandler(Entity_BBFireBall.class, new Render_UQMagics(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_IHFrozenMob.class, new Render_UQFreezer(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_PGPowerBomb.class, new Render_UQMagics(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_SBWindEdge.class, new Render_UQMagics(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_ThrowingKnife.class, new RenderThrowingKnife(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_HighSkeleton.class, new RenderAdvSkeleton(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_SkeletonSniper.class, new RenderAdvSkeleton(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_ZombieWarrior.class, new RenderAdvZombie(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_FireZombie.class, new RenderAdvZombie(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_HighSpeedCreeper.class, new RenderHCreeper(renderManager));
	    RenderingRegistry.registerEntityRenderingHandler(Entity_GoldCreeper.class, new RenderGCreeper(renderManager));

        registerItemModel(BlueEnhancer, "blueenhancer");
        registerItemModel(AsmoSlasher, "asmoslasher");
        registerItemModel(BlazeBlade, "blazeblade");
        registerItemModel(CrossBow, "crossbow");
        registerItemModel(DevilSword, "devilsword");
        registerItemModel(GenocideBlade, "genocideblade");
        registerItemModel(HolySaber, "holysaber");
        registerItemModel(IceHold, "icehold");
        registerItemModel(InfiniteAxe, "infiniteaxe");
        registerItemModel(InfiniteHoe, "infinitehoe");
        registerItemModel(InfinitePickaxe, "infinitepickaxe");
        registerItemModel(InfiniteShovel, "infiniteshovel");
        registerItemModel(InfiniteSword, "infinitesword");
        registerItemModel(LuckLuck, "luckluck");
        registerItemModel(NEGI, "negi");
        registerItemModel(PlanetGuardian, "planetguardian");
        registerItemModel(PoisonKnife, "poisonknife");
        registerItemModel(RedEnhancer, "redenhancer");
        registerItemModel(SmashBat, "smashbat");
        registerItemModel(StormBringer, "stormbringer");
        registerItemModel(ThrowingKnife, "throwingknife");
        registerItemModel(UGDiamondAxe, "ugdiamondaxe");
        registerItemModel(UGDiamondPickaxe, "ugdiamondpickaxe");
        registerItemModel(UGDiamondShovel, "ugdiamondshovel");
        registerItemModel(UGGoldAxe, "uggoldaxe");
        registerItemModel(UGGoldPickaxe, "uggoldpickaxe");
        registerItemModel(UGGoldShovel, "uggoldshovel");
        registerItemModel(UGIronAxe, "ugironaxe");
        registerItemModel(UGIronPickaxe, "ugironpickaxe");
        registerItemModel(UGIronShovel, "ugironshovel");
        registerItemModel(UGWoodAxe, "ugwoodaxe");
        registerItemModel(UGWoodPickaxe, "ugwoodpickaxe");
        registerItemModel(UGWoodShovel, "ugwoodshovel");
        registerItemModel(UGStoneAxe, "ugstoneaxe");
        registerItemModel(UGStonePickaxe, "ugstonepickaxe");
        registerItemModel(UGStoneShovel, "ugstoneshovel");
        registerItemModel(spawnItem, "spawn_item");
	}

    private void registerItemModel(Item item, String registeredName) {
        ItemModelMesher itemModelMesher = mc.getRenderItem().getItemModelMesher();
        itemModelMesher.register(item, 0, new ModelResourceLocation(MOD_ID + ":" + registeredName, "inventory"));
    }
}