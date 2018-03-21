package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.CommonProxy;
import Nanashi.AdvancedTools.entity.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import static Nanashi.AdvancedTools.AdvancedTools.*;
//import static Nanashi.AdvancedTools.item.ItemSpawnATMob.COLOR_MAP;

public class ClientProxy extends CommonProxy {
    private Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void preInit() {
        RenderingRegistry.registerEntityRenderingHandler(Entity_BBFireBall.class, renderManager -> new Render_UQMagics(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_IHFrozenMob.class, renderManager -> new Render_UQFreezer(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_PGPowerBomb.class, renderManager -> new Render_UQMagics(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_SBWindEdge.class, renderManager -> new Render_UQMagics(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_ThrowingKnife.class, renderManager -> new RenderThrowingKnife(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_HighSkeleton.class, renderManager -> new RenderAdvSkeleton(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_SkeletonSniper.class, renderManager -> new RenderAdvSkeleton(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_ZombieWarrior.class, renderManager -> new RenderAdvZombie(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_FireZombie.class, renderManager -> new RenderAdvZombie(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_HighSpeedCreeper.class, renderManager -> new RenderHCreeper(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(Entity_GoldCreeper.class, renderManager -> new RenderGCreeper(renderManager));
    }

    @Override
    public void init() {
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
//        registerItemModel(spawnItem, "spawn_item");
//        ItemColors itemColors = mc.getItemColors();
//        itemColors.registerItemColorHandler(((stack, tintIndex) -> {
//            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("mob_name")) {
//                String mobName = stack.getTagCompound().getString("mob_name");
//                if (COLOR_MAP.containsKey(mobName)) {
//                    return COLOR_MAP.get(mobName);
//                }
//            }
//            return 0;
//        }), spawnItem);
    }

    private void registerItemModel(Item item, String registeredName) {
        ItemModelMesher itemModelMesher = mc.getRenderItem().getItemModelMesher();
        itemModelMesher.register(item, 0, new ModelResourceLocation(MOD_ID + ":" + registeredName, "inventory"));
    }
}