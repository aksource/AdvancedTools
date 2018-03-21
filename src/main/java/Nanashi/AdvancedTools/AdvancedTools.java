package Nanashi.AdvancedTools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.logging.Logger;

@Mod(modid = AdvancedTools.MOD_ID,
        name = AdvancedTools.MOD_NAME,
        version = AdvancedTools.MOD_VERSION,
        dependencies = AdvancedTools.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = AdvancedTools.MOD_MC_VERSION)
public class AdvancedTools {
    public static final String MOD_ID = "advancedtools";
    public static final String MOD_NAME = "AdvancedTools";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:forge@[13.19.1,)";
    public static final String MOD_MC_VERSION = "[1.11,1.99.99]";

    public static int UGTools_DestroyRangeLV;
    public static int UGTools_SafetyCounter;
    public static String[] addBlockForPickaxe;
    public static String[] addBlockForShovel;
    public static String[] addBlockForAxe;
    static boolean spawnHiGradeMob;
    static boolean hasMultiToolHolder;
    public static boolean dropGather;
    public static int digUnder;

    static boolean spawnFireZombie;
    static boolean spawnGoldCreeper;
    static boolean spawnHighSkeleton;
    static boolean spawnHighSpeedCreeper;
    static boolean spawnSkeletonSniper;
    static boolean spawnZombieWarrior;

    public static Item RedEnhancer;
    public static Item BlueEnhancer;
    public static Item UGWoodShovel;
    public static Item UGStoneShovel;
    public static Item UGIronShovel;
    public static Item UGDiamondShovel;
    public static Item UGGoldShovel;
    public static Item UGWoodPickaxe;
    public static Item UGStonePickaxe;
    public static Item UGIronPickaxe;
    public static Item UGDiamondPickaxe;
    public static Item UGGoldPickaxe;
    public static Item UGWoodAxe;
    public static Item UGStoneAxe;
    public static Item UGIronAxe;
    public static Item UGDiamondAxe;
    public static Item UGGoldAxe;
    public static Item BlazeBlade;
    public static Item IceHold;
    public static Item AsmoSlasher;
    public static Item PlanetGuardian;
    public static Item StormBringer;
    public static Item NEGI;
    public static Item LuckLuck;
    public static Item SmashBat;
    public static Item DevilSword;
    public static Item HolySaber;
    public static Item ThrowingKnife;
    public static Item PoisonKnife;
    public static Item CrossBow;
    public static Item InfiniteSword;
    public static Item InfinitePickaxe;
    public static Item InfiniteAxe;
    public static Item InfiniteShovel;
    public static Item InfiniteHoe;
    public static Item GenocideBlade;
//    public static Item spawnItem;

    public static final String TEXTURE_ASSETS = "advancedtools";

    @SidedProxy(clientSide = "Nanashi.AdvancedTools.client.ClientProxy", serverSide = "Nanashi.AdvancedTools.CommonProxy")
    public static CommonProxy proxy;
    public AdvToolsUtil util = new AdvToolsUtil();
    static final CreativeTabs tabsAT = new CreativeTabAT("AdvancedTools");
    public static final ArrayList<Item> list = new ArrayList<>();
    public static final Logger LOGGER = Logger.getLogger("AdvancedTools");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        UGTools_DestroyRangeLV = config.get(Configuration.CATEGORY_GENERAL, "Destroy Range Lv", 1).getInt();
        UGTools_SafetyCounter = config.get(Configuration.CATEGORY_GENERAL, "Safety Counter", 100).getInt();
        spawnHiGradeMob = config.get(Configuration.CATEGORY_GENERAL, "Spawn Hi-Grade Mobs", true).getBoolean(true);
        dropGather = config.get(Configuration.CATEGORY_GENERAL, "dropGather", false,
                "drop block gather under player's foot").getBoolean(false);
        digUnder = config.get(Configuration.CATEGORY_GENERAL, "digUnder", 1, "How depth to dig at digging block side. default:1").getInt();
        digUnder = MathHelper.clamp(digUnder, 0, 256);

        addBlockForPickaxe = config.get(
                Configuration.CATEGORY_GENERAL,
                "blocklistForUGPickaxe",
                new String[]{"minecraft:diamond_ore", "minecraft:gold_ore", "minecraft:iron_ore",
                        "minecraft:coal_ore", "minecraft:lapis_ore", "minecraft:redstone_ore",
                        "minecraft:lit_redstone_ore", "minecraft:quartz_ore", "minecraft:DIAMOND_ore"})
                .getStringList();
        addBlockForShovel = config.get(Configuration.CATEGORY_GENERAL, "blocklistForUGShovel",
                new String[]{"minecraft:clay", "minecraft:gravel"}).getStringList();
        addBlockForAxe = config.get(Configuration.CATEGORY_GENERAL, "blocklistForUGAxe",
                new String[]{"minecraft:log", "minecraft:log2"}).getStringList();

        spawnFireZombie = config.get("Mob Spawn Setting", "FireZombie", true).getBoolean(true);
        spawnGoldCreeper = config.get("Mob Spawn Setting", "GoldCreeper", true).getBoolean(true);
        spawnHighSkeleton = config.get("Mob Spawn Setting", "HighSkeleton", true).getBoolean(true);
        spawnHighSpeedCreeper = config.get("Mob Spawn Setting", "HighSpeedCreeper", true).getBoolean(true);
        spawnSkeletonSniper = config.get("Mob Spawn Setting", "SkeletonSniper", true).getBoolean(true);
        spawnZombieWarrior = config.get("Mob Spawn Setting", "ZombieWarrior", true).getBoolean(true);

        config.save();
        this.util.itemSetup();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerClickHook());
        this.util.addRecipe();
        this.util.entitySetup(this);
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        hasMultiToolHolder = Loader.isModLoaded("multiToolHolders");
    }

}
