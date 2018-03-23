package Nanashi.AdvancedTools;

import Nanashi.AdvancedTools.utils.AdvToolsUtil;
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
    public static final String MOD_DEPENDENCIES = "required-after:forge@[14.23.2,)";
    public static final String MOD_MC_VERSION = "[1.12,1.99.99]";

    public static int UGTools_DestroyRangeLV;
    public static int UGTools_SafetyCounter;
    public static String[] addBlockForPickaxe;
    public static String[] addBlockForShovel;
    public static String[] addBlockForAxe;
    public static boolean spawnHiGradeMob;
    static boolean hasMultiToolHolder;
    public static boolean dropGather;
    public static int digUnder;

    public static boolean spawnFireZombie;
    public static boolean spawnGoldCreeper;
    public static boolean spawnHighSkeleton;
    public static boolean spawnHighSpeedCreeper;
    public static boolean spawnSkeletonSniper;
    public static boolean spawnZombieWarrior;

    //    public static Item spawnItem;

    public static final String TEXTURE_ASSETS = "advancedtools";

    @SidedProxy(clientSide = "Nanashi.AdvancedTools.client.ClientProxy", serverSide = "Nanashi.AdvancedTools.CommonProxy")
    public static CommonProxy proxy;
    public AdvToolsUtil util = new AdvToolsUtil();
    public static final ArrayList<Item> list = new ArrayList<>();
    public static final Logger LOGGER = Logger.getLogger("AdvancedTools");

    @Mod.EventHandler
    @SuppressWarnings("unused")
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
        MinecraftForge.EVENT_BUS.register(util);
        proxy.preInit();
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerClickHook());
        this.util.entitySetup(this);
        proxy.init();
    }

    @Mod.EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
        hasMultiToolHolder = Loader.isModLoaded("multiToolHolders");
    }
}
