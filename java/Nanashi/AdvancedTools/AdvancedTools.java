package Nanashi.AdvancedTools;

import Nanashi.AdvancedTools.entity.*;
import Nanashi.AdvancedTools.item.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Mod(modid = AdvancedTools.MOD_ID,
        name = AdvancedTools.MOD_NAME,
        version = AdvancedTools.MOD_VERSION,
        dependencies = AdvancedTools.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = AdvancedTools.MOD_MC_VERSION)
public class AdvancedTools {
    public static final String MOD_ID = "AdvancedTools";
    public static final String MOD_NAME = "AdvancedTools";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[11.14.0.1237,)";
    public static final String MOD_MC_VERSION = "[1.9,1.10.99]";

    public static int UGTools_DestroyRangeLV;
    public static int UGTools_SafetyCounter;
    public static String[] addBlockForPickaxe;
    public static String[] addBlockForShovel;
    public static String[] addBlockForAxe;
    public static boolean spawnHiGradeMob;
    public static boolean hasMultiToolHolder;
    public static boolean dropGather;
    public static int digUnder;

    public static boolean spawnFireZombie;
    public static boolean spawnGoldCreeper;
    public static boolean spawnHighSkeleton;
    public static boolean spawnHighSpeedCreeper;
    public static boolean spawnSkeletonSniper;
    public static boolean spawnZombieWarrior;

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
    public static Item spawnItem;

    public static final String textureDomain = "advancedtools:";
    public static final String textureassets = "advancedtools";

    @SidedProxy(clientSide = "Nanashi.AdvancedTools.client.ClientProxy", serverSide = "Nanashi.AdvancedTools.CommonProxy")
    public static CommonProxy proxy;
    public static final CreativeTabs tabsAT = new CreativeTabAT("AdvancedTools");
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
        digUnder = MathHelper.clamp_int(digUnder, 0, 256);

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
        this.itemSetup();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerClickHook());
        this.addRecipe();
        this.entitySetup();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        hasMultiToolHolder = Loader.isModLoaded("MultiToolHolders");
    }

    public void entitySetup() {
        EntityRegistry.registerModEntity(Entity_ThrowingKnife.class, "ThrowingKnife", 0, this, 250, 5, true);
        EntityRegistry.registerModEntity(Entity_HighSkeleton.class, "HighSkeleton", 1, this, 250, 1, true);
        ItemSpawnATMob.registerMobColor("AdvancedTools.HighSkeleton", 0x0000FF);
        EntityRegistry.registerModEntity(Entity_SkeletonSniper.class, "SkeletonSniper", 2, this, 250, 1, true);
        ItemSpawnATMob.registerMobColor("AdvancedTools.SkeletonSniper", 0x000000);
        EntityRegistry.registerModEntity(Entity_ZombieWarrior.class, "ZombieWarrior", 3, this, 250, 1, true);
        ItemSpawnATMob.registerMobColor("AdvancedTools.ZombieWarrior", 0xCC00CC);
        EntityRegistry.registerModEntity(Entity_FireZombie.class, "FireZombie", 4, this, 250, 1, true);
        ItemSpawnATMob.registerMobColor("AdvancedTools.FireZombie", 0xCC0000);
        EntityRegistry.registerModEntity(Entity_HighSpeedCreeper.class, "HighSpeedCreeper", 5, this, 250, 1, true);
        ItemSpawnATMob.registerMobColor("AdvancedTools.HighSpeedCreeper", 0x0033FF);
        EntityRegistry.registerModEntity(Entity_GoldCreeper.class, "GoldCreeper", 6, this, 250, 1, true);
        ItemSpawnATMob.registerMobColor("AdvancedTools.GoldCreeper", 0xFFFF00);
        EntityRegistry.registerModEntity(Entity_BBFireBall.class, "BBFireBall", 7, this, 250, 1, true);
        EntityRegistry.registerModEntity(Entity_IHFrozenMob.class, "IHFrozenMob", 8, this, 250, 1, true);
        EntityRegistry.registerModEntity(Entity_PGPowerBomb.class, "PGPowerBomb", 9, this, 250, 1, true);
        EntityRegistry.registerModEntity(Entity_SBWindEdge.class, "SBWindEdge", 10, this, 250, 1, true);
        if (spawnHiGradeMob) {
            for (Biome biome : Biome.REGISTRY) {
                if (biome != null
                        && isSpawnableBiomeType(biome)
                        && biome.getSpawnableList(EnumCreatureType.MONSTER).size() >= 5) {
                    if (spawnHighSkeleton)
                        EntityRegistry.addSpawn(Entity_HighSkeleton.class, 2, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (spawnSkeletonSniper)
                        EntityRegistry.addSpawn(Entity_SkeletonSniper.class, 3, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (spawnZombieWarrior)
                        EntityRegistry.addSpawn(Entity_ZombieWarrior.class, 2, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (spawnFireZombie)
                        EntityRegistry.addSpawn(Entity_FireZombie.class, 3, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (spawnHighSpeedCreeper)
                        EntityRegistry.addSpawn(Entity_HighSpeedCreeper.class, 3, 4, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (spawnGoldCreeper)
                        EntityRegistry.addSpawn(Entity_GoldCreeper.class, 1, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                }
            }
        }
    }

    private boolean isSpawnableBiomeType(Biome biome) {
        BiomeDictionary.Type[] types = BiomeDictionary.getTypesForBiome(biome);
        for (BiomeDictionary.Type type : types) {
            if (type == BiomeDictionary.Type.NETHER
                    || type == BiomeDictionary.Type.MUSHROOM
                    || type == BiomeDictionary.Type.END) {
                return false;
            }
        }
        return true;
    }

    public void itemSetup() {
        RedEnhancer = (new ItemEnhancer(0)).setRegistryName("redenhancer").setUnlocalizedName("RedEnhancer").setCreativeTab(tabsAT);
        GameRegistry.register(RedEnhancer);
        list.add(RedEnhancer);
        BlueEnhancer = (new ItemEnhancer(1)).setRegistryName("blueenhancer").setUnlocalizedName("BlueEnhancer").setCreativeTab(tabsAT);
        GameRegistry.register(BlueEnhancer);
        list.add(BlueEnhancer);
        UGWoodShovel = (new ItemUGShovel(ToolMaterial.WOOD, 1.0F)).setRegistryName("ugwoodshovel").setUnlocalizedName("UpgradedWoodenShovel")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGWoodShovel);
        list.add(UGWoodShovel);
        UGStoneShovel = (new ItemUGShovel(ToolMaterial.STONE, 1.5F)).setRegistryName("ugstoneshovel").setUnlocalizedName("UpgradedStoneShovel")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGStoneShovel);
        list.add(UGStoneShovel);
        UGIronShovel = (new ItemUGShovel(ToolMaterial.IRON, 2.0F)).setRegistryName("ugironshovel").setUnlocalizedName("UpgradedIronShovel")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGIronShovel);
        list.add(UGIronShovel);
        UGDiamondShovel = (new ItemUGShovel(ToolMaterial.DIAMOND, 3.0F)).setRegistryName("ugdiamondshovel").setUnlocalizedName("UpgradedDiamondShovel")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGDiamondShovel);
        list.add(UGDiamondShovel);
        UGGoldShovel = (new ItemUGShovel(ToolMaterial.GOLD, 2.5F)).setRegistryName("uggoldshovel").setUnlocalizedName("UpgradedGoldenShovel")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGGoldShovel);
        list.add(UGGoldShovel);
        UGWoodPickaxe = (new ItemUGPickaxe(ToolMaterial.WOOD, 1.0F)).setRegistryName("ugwoodpickaxe").setUnlocalizedName("UpgradedWoodenPickaxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGWoodPickaxe);
        list.add(UGWoodPickaxe);
        UGStonePickaxe = (new ItemUGPickaxe(ToolMaterial.STONE, 1.5F)).setRegistryName("ugstonepickaxe").setUnlocalizedName("UpgradedStonePickaxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGStonePickaxe);
        list.add(UGStonePickaxe);
        UGIronPickaxe = (new ItemUGPickaxe(ToolMaterial.IRON, 2.0F)).setRegistryName("ugironpickaxe").setUnlocalizedName("UpgradedIronPickaxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGIronPickaxe);
        list.add(UGIronPickaxe);
        UGDiamondPickaxe = (new ItemUGPickaxe(ToolMaterial.DIAMOND, 3.0F)).setRegistryName("ugdiamondpickaxe").setUnlocalizedName("UpgradedDiamondPickaxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGDiamondPickaxe);
        list.add(UGDiamondPickaxe);
        UGGoldPickaxe = (new ItemUGPickaxe(ToolMaterial.GOLD, 2.5F)).setRegistryName("uggoldpickaxe").setUnlocalizedName("UpgradedGoldenPickaxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGGoldPickaxe);
        list.add(UGGoldPickaxe);
        UGWoodAxe = (new ItemUGAxe(ToolMaterial.WOOD, ItemAxe.ATTACK_DAMAGES[0], ItemAxe.ATTACK_SPEEDS[0], 1.0F)).setRegistryName("ugwoodaxe").setUnlocalizedName("UpgradedWoodenAxe").setCreativeTab(tabsAT);
        GameRegistry.register(UGWoodAxe);
        list.add(UGWoodAxe);
        UGStoneAxe = (new ItemUGAxe(ToolMaterial.STONE, ItemAxe.ATTACK_DAMAGES[1], ItemAxe.ATTACK_SPEEDS[1], 1.5F)).setRegistryName("ugstoneaxe").setUnlocalizedName("UpgradedStoneAxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGStoneAxe);
        list.add(UGStoneAxe);
        UGIronAxe = (new ItemUGAxe(ToolMaterial.IRON, ItemAxe.ATTACK_DAMAGES[2], ItemAxe.ATTACK_SPEEDS[2], 2.0F)).setRegistryName("ugironaxe").setUnlocalizedName("UpgradedIronAxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGIronAxe);
        list.add(UGIronAxe);
        UGDiamondAxe = (new ItemUGAxe(ToolMaterial.DIAMOND, ItemAxe.ATTACK_DAMAGES[3], ItemAxe.ATTACK_SPEEDS[3], 3.0F)).setRegistryName("ugdiamondaxe").setUnlocalizedName("UpgradedDiamondAxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGDiamondAxe);
        list.add(UGDiamondAxe);
        UGGoldAxe = (new ItemUGAxe(ToolMaterial.GOLD, ItemAxe.ATTACK_DAMAGES[4], ItemAxe.ATTACK_SPEEDS[4], 2.5F)).setRegistryName("uggoldaxe").setUnlocalizedName("UpgradedGoldenAxe")
                .setCreativeTab(tabsAT);
        GameRegistry.register(UGGoldAxe);
        list.add(UGGoldAxe);
        BlazeBlade = (new ItemUQBlazeBlade(ToolMaterial.DIAMOND, 4)).setRegistryName("blazeblade").setUnlocalizedName("BlazeBlade")
                .setMaxDamage(1200).setCreativeTab(tabsAT);
        GameRegistry.register(BlazeBlade);
        list.add(BlazeBlade);
        IceHold = (new ItemUQIceHold(ToolMaterial.DIAMOND, 4)).setRegistryName("icehold").setUnlocalizedName("FreezeHold").setMaxDamage(1200)
                .setCreativeTab(tabsAT);
        GameRegistry.register(IceHold);
        list.add(IceHold);
        AsmoSlasher = (new ItemUQAsmoSlasher(ToolMaterial.DIAMOND, 4)).setRegistryName("asmoslasher").setUnlocalizedName("AsmoSlasher")
                .setMaxDamage(1200).setCreativeTab(tabsAT);
        GameRegistry.register(AsmoSlasher);
        list.add(AsmoSlasher);
        PlanetGuardian = (new ItemUQPlanetGuardian(ToolMaterial.DIAMOND, 4)).setRegistryName("planetguardian").setUnlocalizedName("Planet Guardian")
                .setMaxDamage(1200).setCreativeTab(tabsAT);
        GameRegistry.register(PlanetGuardian);
        list.add(PlanetGuardian);
        StormBringer = (new ItemUQStormBringer(ToolMaterial.DIAMOND, 4)).setRegistryName("stormbringer").setUnlocalizedName("StormBringer")
                .setMaxDamage(1200).setCreativeTab(tabsAT);
        GameRegistry.register(StormBringer);
        list.add(StormBringer);
        NEGI = (new Item_Negi(1, 0.1F, false)).setRegistryName("negi").setUnlocalizedName("NEGI").setCreativeTab(tabsAT);
        GameRegistry.register(NEGI);
        list.add(NEGI);
        LuckLuck = (new ItemUQLuckies(ToolMaterial.GOLD, 2)).setRegistryName("luckluck").setUnlocalizedName("Lucky&Lucky").setMaxDamage(77)
                .setCreativeTab(tabsAT);
        GameRegistry.register(LuckLuck);
        list.add(LuckLuck);
        SmashBat = (new ItemUniqueArms(ToolMaterial.WOOD, 1)).setRegistryName("smashbat").setUnlocalizedName("SmashBat").setMaxDamage(95)
                .setCreativeTab(tabsAT);
        GameRegistry.register(SmashBat);
        list.add(SmashBat);
        DevilSword = (new ItemUQDevilSword(ToolMaterial.DIAMOND, 1)).setRegistryName("devilsword").setUnlocalizedName("DevilSword").setMaxDamage(427)
                .setCreativeTab(tabsAT);
        GameRegistry.register(DevilSword);
        list.add(DevilSword);
        HolySaber = (new ItemUQHolySaber(ToolMaterial.GOLD, 5)).setRegistryName("holysaber").setUnlocalizedName("HolySaber").setMaxDamage(280)
                .setCreativeTab(tabsAT);
        GameRegistry.register(HolySaber);
        list.add(HolySaber);
        ThrowingKnife = (new Item_ThrowingKnife(false)).setRegistryName("throwingknife").setUnlocalizedName("ThrowingKnife").setCreativeTab(tabsAT)
				/*.setTextureName(AdvancedTools.textureDomain + "ThrowingKnife")*/;
        GameRegistry.register(ThrowingKnife);
        list.add(ThrowingKnife);
        PoisonKnife = (new Item_ThrowingKnife(true)).setRegistryName("poisonknife").setUnlocalizedName("PoisonKnife").setCreativeTab(tabsAT);
        GameRegistry.register(PoisonKnife);
        list.add(PoisonKnife);
        CrossBow = (new Item_CrossBow()).setRegistryName("crossbow").setUnlocalizedName("A_CrossBow").setCreativeTab(tabsAT);
        GameRegistry.register(CrossBow);
        list.add(CrossBow);
        InfiniteSword = (new ItemUniqueArms(ToolMaterial.GOLD, 8)).setRegistryName("infinitesword").setUnlocalizedName("InfiniteSword").setMaxDamage(0)
                .setCreativeTab(tabsAT);
        GameRegistry.register(InfiniteSword);
        list.add(InfiniteSword);
        InfinitePickaxe = (new ItemUGPickaxe(ToolMaterial.DIAMOND, 1.0F)).setRegistryName("infinitepickaxe").setUnlocalizedName("InfinityPickaxe")
                .setMaxDamage(0).setCreativeTab(tabsAT);
        GameRegistry.register(InfinitePickaxe);
        list.add(InfinitePickaxe);
        InfiniteAxe = (new ItemUGAxe(ToolMaterial.GOLD, ItemAxe.ATTACK_DAMAGES[4], ItemAxe.ATTACK_SPEEDS[4], 1.0F)).setRegistryName("infiniteaxe").setUnlocalizedName("InfinityAxe").setMaxDamage(0)
                .setCreativeTab(tabsAT);
        GameRegistry.register(InfiniteAxe);
        list.add(InfiniteAxe);
        InfiniteShovel = (new ItemUGShovel(ToolMaterial.GOLD, 1.0F)).setRegistryName("infiniteshovel").setUnlocalizedName("InfinityShovel").setMaxDamage(0)
                .setCreativeTab(tabsAT);
        GameRegistry.register(InfiniteShovel);
        list.add(InfiniteShovel);
        InfiniteHoe = (new ItemInfHoe(ToolMaterial.GOLD)).setRegistryName("infinitehoe").setUnlocalizedName("InfinityHoe").setMaxDamage(0)
                .setCreativeTab(tabsAT);
        GameRegistry.register(InfiniteHoe);
        list.add(InfiniteHoe);
        GenocideBlade = (new ItemUniqueArms(ToolMaterial.DIAMOND, 10000)).setRegistryName("genocideblade").setUnlocalizedName("GenocideBlade")
                .setMaxDamage(0).setCreativeTab(tabsAT);
        GameRegistry.register(GenocideBlade);
        spawnItem = new ItemSpawnATMob().setRegistryName("spawn_item").setUnlocalizedName("spawnItem").setCreativeTab(tabsAT);
        GameRegistry.register(spawnItem);
    }

    public void addRecipe() {
        Item[][] var1 = new Item[][]{
                {UGWoodShovel, UGStoneShovel, UGIronShovel, UGDiamondShovel, UGGoldShovel,
                        InfiniteShovel},
                {UGWoodPickaxe, UGStonePickaxe, UGIronPickaxe, UGDiamondPickaxe, UGGoldPickaxe,
                        InfinitePickaxe},
                {UGWoodAxe, UGStoneAxe, UGIronAxe, UGDiamondAxe, UGGoldAxe, InfiniteAxe}};
        Object[] var2 = new Object[]{"stickWood", RedEnhancer, RedEnhancer, BlueEnhancer, BlueEnhancer,
                Items.ENDER_EYE};
        Object[] var3 = new Object[]{"logWood", "cobblestone", Items.IRON_INGOT, Items.DIAMOND,
                Items.GOLD_INGOT, Blocks.END_STONE};
        String[][] var4 = new String[][]{{"X", "#", "Z"}, {"XXX", " # ", " Z "}, {"XX", "X#", " Z"}};
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InfiniteSword, 1), " #X", "XY#", "ZX ", 'X',
                Blocks.END_STONE, 'Y', Items.ENDER_EYE, 'Z', "stickWood", '#', Blocks.GLOWSTONE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(InfiniteHoe, 1), "XX", " Y", " Z", 'X', Blocks.END_STONE,
                'Y', Items.ENDER_EYE, 'Z', "stickWood"));
        GameRegistry.addRecipe(new ItemStack(RedEnhancer, 2), " X ", "XZX", " X ", 'X',
                Items.GOLD_NUGGET, 'Z', Items.REDSTONE);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.REDSTONE), RedEnhancer, RedEnhancer);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.GOLD_INGOT), RedEnhancer, RedEnhancer,
                RedEnhancer, RedEnhancer, RedEnhancer, RedEnhancer, RedEnhancer, RedEnhancer, RedEnhancer);
        GameRegistry.addRecipe(new ItemStack(BlueEnhancer, 2), "RXR", "XZX", "RXR", 'X',
                Items.GOLD_INGOT, 'Z', Items.DIAMOND, 'R', Items.REDSTONE);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.GOLD_INGOT), BlueEnhancer, BlueEnhancer);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DIAMOND), BlueEnhancer, BlueEnhancer,
                BlueEnhancer, BlueEnhancer, BlueEnhancer, BlueEnhancer, BlueEnhancer, BlueEnhancer, BlueEnhancer);

        for (int var5 = 0; var5 < var1[0].length; ++var5) {
            for (int var6 = 0; var6 < var1.length; ++var6) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(var1[var6][var5]), var4[var6], 'X', var3[var5],
                        '#', var2[var5], 'Z', "stickWood"));
            }
        }

        GameRegistry.addRecipe(new ItemStack(BlazeBlade), " P#", "XEP", "IX ", 'I', Items.BLAZE_ROD,
                'X', Items.DIAMOND, '#', Items.IRON_INGOT, 'E', BlueEnhancer, 'P', Items.BLAZE_POWDER);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IceHold), " P#", "XEP", "IX ", 'I', "stickWood", 'X',
                Items.IRON_INGOT, '#', Items.WATER_BUCKET, 'E', BlueEnhancer, 'P', Blocks.SNOW));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AsmoSlasher), " P#", "XEP", "IX ", 'I', "stickWood", 'X',
                Items.GOLD_INGOT, '#', Items.IRON_INGOT, 'E', BlueEnhancer, 'P', Items.REDSTONE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(PlanetGuardian), " ##", "#E#", "I# ", 'I', "stickWood",
                '#', Items.IRON_INGOT, 'E', BlueEnhancer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(StormBringer), "  #", "XE ", "IX ", 'I', "stickWood", 'X',
                Items.FEATHER, '#', Items.IRON_INGOT, 'E', BlueEnhancer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LuckLuck), "  #", "#E ", "I# ", 'I', "stickWood", '#',
                Items.GOLD_INGOT, 'E', RedEnhancer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SmashBat), "#", "#", "#", '#', "logWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(NEGI), Items.WATER_BUCKET, Items.WHEAT_SEEDS,
                Blocks.DIRT);
        GameRegistry.addRecipe(new ItemStack(HolySaber), "XBX", "EAC", "XDX", 'A', BlazeBlade, 'B',
                IceHold, 'C', AsmoSlasher, 'D', PlanetGuardian, 'E', StormBringer, 'X', Blocks.GLOWSTONE);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ThrowingKnife, 16), " X", "# ", 'X', Items.IRON_INGOT, '#',
                "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(PoisonKnife), ThrowingKnife, Items.SPIDER_EYE);
    }

    public static RayTraceResult getMousePoint(World world, EntityLivingBase entityLivingBase) {
        float var1 = 1F;
        double limit = 5.0D;
        double viewX = entityLivingBase.getLookVec().xCoord;
        double viewY = entityLivingBase.getLookVec().yCoord;
        double viewZ = entityLivingBase.getLookVec().zCoord;
        double dPlayerPosX = entityLivingBase.prevPosX + (entityLivingBase.posX - entityLivingBase.prevPosX) * (double) var1;
        double dPlayerPosY = entityLivingBase.prevPosY + (entityLivingBase.posY - entityLivingBase.prevPosY) * (double) var1 + 1.62D
				/*- (double) entityplayer.yOffset*/;
        double dPlayerPosZ = entityLivingBase.prevPosZ + (entityLivingBase.posZ - entityLivingBase.prevPosZ) * (double) var1;
        Vec3d vecPos = new Vec3d(dPlayerPosX, dPlayerPosY, dPlayerPosZ);
        Vec3d vecLook = vecPos.addVector(viewX * limit, viewY * limit, viewZ * limit);
        RayTraceResult mop = world.rayTraceBlocks(vecPos, vecLook, false, false, true);
        double distBlock = (mop != null && mop.typeOfHit != RayTraceResult.Type.MISS) ? mop.hitVec.distanceTo(vecPos) : limit;
        double distBlockCopy = distBlock;
        Entity pointedEntity = null;
        Vec3d mopHitVec = null;
        List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(entityLivingBase,
                entityLivingBase.getEntityBoundingBox()
                        .addCoord(viewX * limit, viewY * limit, viewZ * limit)
                        .expand(var1, var1, var1));
        for (Entity entity : entityList) {
            if (!entity.canBeCollidedWith()) continue;
            float size = entity.getCollisionBorderSize();
            AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(size, size, size);
            RayTraceResult interceptMop = aabb.calculateIntercept(vecPos, vecLook);
            Vec3d hitVec = (interceptMop != null) ? interceptMop.hitVec : null;
            double distance = (hitVec != null) ? vecPos.distanceTo(hitVec) : 0.0d;
            if ((aabb.isVecInside(vecPos) || interceptMop != null) &&
                    (distance < distBlockCopy || distBlockCopy == 0.0d)) {
                if (entity == entityLivingBase.getRidingEntity() && !entity.canRiderInteract()) {
                    if (distBlockCopy == 0.0d) {
                        pointedEntity = entity;
                        mopHitVec = (hitVec != null) ? hitVec : vecPos;
                    }
                } else {
                    pointedEntity = entity;
                    mopHitVec = (hitVec != null) ? hitVec : vecPos;
                    distBlockCopy = distance;
                }
            }
        }

        if (pointedEntity != null && distBlockCopy < distBlock) {
            mop = new RayTraceResult(pointedEntity, mopHitVec);
        }

        return mop;
    }
}
