package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.CommonProxy;
import Nanashi.AdvancedTools.entity.*;
import Nanashi.AdvancedTools.utils.Items;
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
        registerItemModel(Items.BlueEnhancer, "blueenhancer");
        registerItemModel(Items.AsmoSlasher, "asmoslasher");
        registerItemModel(Items.BlazeBlade, "blazeblade");
        registerItemModel(Items.CrossBow, "crossbow");
        registerItemModel(Items.DevilSword, "devilsword");
        registerItemModel(Items.GenocideBlade, "genocideblade");
        registerItemModel(Items.HolySaber, "holysaber");
        registerItemModel(Items.IceHold, "icehold");
        registerItemModel(Items.InfiniteAxe, "infiniteaxe");
        registerItemModel(Items.InfiniteHoe, "infinitehoe");
        registerItemModel(Items.InfinitePickaxe, "infinitepickaxe");
        registerItemModel(Items.InfiniteShovel, "infiniteshovel");
        registerItemModel(Items.InfiniteSword, "infinitesword");
        registerItemModel(Items.LuckLuck, "luckluck");
        registerItemModel(Items.NEGI, "negi");
        registerItemModel(Items.PlanetGuardian, "planetguardian");
        registerItemModel(Items.PoisonKnife, "poisonknife");
        registerItemModel(Items.RedEnhancer, "redenhancer");
        registerItemModel(Items.SmashBat, "smashbat");
        registerItemModel(Items.StormBringer, "stormbringer");
        registerItemModel(Items.ThrowingKnife, "throwingknife");
        registerItemModel(Items.UGDiamondAxe, "ugdiamondaxe");
        registerItemModel(Items.UGDiamondPickaxe, "ugdiamondpickaxe");
        registerItemModel(Items.UGDiamondShovel, "ugdiamondshovel");
        registerItemModel(Items.UGGoldAxe, "uggoldaxe");
        registerItemModel(Items.UGGoldPickaxe, "uggoldpickaxe");
        registerItemModel(Items.UGGoldShovel, "uggoldshovel");
        registerItemModel(Items.UGIronAxe, "ugironaxe");
        registerItemModel(Items.UGIronPickaxe, "ugironpickaxe");
        registerItemModel(Items.UGIronShovel, "ugironshovel");
        registerItemModel(Items.UGWoodAxe, "ugwoodaxe");
        registerItemModel(Items.UGWoodPickaxe, "ugwoodpickaxe");
        registerItemModel(Items.UGWoodShovel, "ugwoodshovel");
        registerItemModel(Items.UGStoneAxe, "ugstoneaxe");
        registerItemModel(Items.UGStonePickaxe, "ugstonepickaxe");
        registerItemModel(Items.UGStoneShovel, "ugstoneshovel");
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