package Nanashi.AdvancedTools.utils;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.CreativeTabAT;
import Nanashi.AdvancedTools.entity.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Set;

import static Nanashi.AdvancedTools.utils.Items.*;

/**
 * ユーティリティクラス。初期化処理とか
 * Created by A.K. on 2017/01/21.
 */
public class AdvToolsUtil {
    public static final CreativeTabs tabsAT = new CreativeTabAT("AdvancedTools");
    private static final ResourceLocation ENTITY_THROWINGKNIFE_RL = new ResourceLocation(AdvancedTools.MOD_ID, "throwingknife");
    private static final ResourceLocation ENTITY_HIGHSKELETON_RL = new ResourceLocation(AdvancedTools.MOD_ID, "highskeleton");
    private static final ResourceLocation ENTITY_SKELETONSNIPER_RL = new ResourceLocation(AdvancedTools.MOD_ID, "skeletonsniper");
    private static final ResourceLocation ENTITY_ZOMBIEWARRIOR_RL = new ResourceLocation(AdvancedTools.MOD_ID, "zombiewarrior");
    private static final ResourceLocation ENTITY_FIREZOMBIE_RL = new ResourceLocation(AdvancedTools.MOD_ID, "firezombie");
    private static final ResourceLocation ENTITY_HIGHSPEEDCREEPER_RL = new ResourceLocation(AdvancedTools.MOD_ID, "highspeedcreeper");
    private static final ResourceLocation ENTITY_GOLDCREEPER_RL = new ResourceLocation(AdvancedTools.MOD_ID, "goldcreeper");
    private static final ResourceLocation ENTITY_BBFIREBALL_RL = new ResourceLocation(AdvancedTools.MOD_ID, "bbfireball");
    private static final ResourceLocation ENTITY_IHFROZENMOB_RL = new ResourceLocation(AdvancedTools.MOD_ID, "ihfrozenmob");
    private static final ResourceLocation ENTITY_PGPOWERBOMB_RL = new ResourceLocation(AdvancedTools.MOD_ID, "pgpowerbomb");
    private static final ResourceLocation ENTITY_SBWINDEDGE_RL = new ResourceLocation(AdvancedTools.MOD_ID, "sbwindedge");
    public static final String RL_NAME_SUFFIX = "_advancedtools";
    private static final String RL_NAME_SUFFIX_CAPITAL =  "AdvancedTools.";

    public static RayTraceResult getMousePoint(World world, EntityLivingBase entityLivingBase) {
        float var1 = 1F;
        double limit = 5.0D;
        double viewX = entityLivingBase.getLookVec().x;
        double viewY = entityLivingBase.getLookVec().y;
        double viewZ = entityLivingBase.getLookVec().z;
        double dPlayerPosX = entityLivingBase.prevPosX + (entityLivingBase.posX - entityLivingBase.prevPosX) * (double) var1;
        double dPlayerPosY = entityLivingBase.prevPosY + (entityLivingBase.posY - entityLivingBase.prevPosY) * (double) var1 + 1.62D;
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
                        .contract(viewX * limit, viewY * limit, viewZ * limit)
                        .expand(var1, var1, var1));
        for (Entity entity : entityList) {
            if (!entity.canBeCollidedWith()) continue;
            float size = entity.getCollisionBorderSize();
            AxisAlignedBB aabb = entity.getEntityBoundingBox().expand(size, size, size);
            RayTraceResult interceptMop = aabb.calculateIntercept(vecPos, vecLook);
            Vec3d hitVec = (interceptMop != null) ? interceptMop.hitVec : null;
            double distance = (hitVec != null) ? vecPos.distanceTo(hitVec) : 0.0d;
            if ((aabb.contains(vecPos) || interceptMop != null) &&
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

    public void entitySetup(AdvancedTools advancedTools) {
        EntityRegistry.registerModEntity(ENTITY_THROWINGKNIFE_RL, Entity_ThrowingKnife.class, RL_NAME_SUFFIX_CAPITAL + "ThrowingKnife", 0, advancedTools, 250, 5, true);
        EntityRegistry.registerModEntity(ENTITY_HIGHSKELETON_RL, Entity_HighSkeleton.class, RL_NAME_SUFFIX_CAPITAL + "HighSkeleton", 1, advancedTools, 250, 1, true, 0x0000FF, 0x0000FF);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.HighSkeleton", 0x0000FF);
        EntityRegistry.registerModEntity(ENTITY_SKELETONSNIPER_RL, Entity_SkeletonSniper.class, RL_NAME_SUFFIX_CAPITAL + "SkeletonSniper", 2, advancedTools, 250, 1, true, 0x000000, 0x000000);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.SkeletonSniper", 0x000000);
        EntityRegistry.registerModEntity(ENTITY_ZOMBIEWARRIOR_RL, Entity_ZombieWarrior.class, RL_NAME_SUFFIX_CAPITAL + "ZombieWarrior", 3, advancedTools, 250, 1, true, 0xCC00CC, 0xCC00CC);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.ZombieWarrior", 0xCC00CC);
        EntityRegistry.registerModEntity(ENTITY_FIREZOMBIE_RL, Entity_FireZombie.class, RL_NAME_SUFFIX_CAPITAL + "FireZombie", 4, advancedTools, 250, 1, true, 0xCC0000, 0xCC0000);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.FireZombie", 0xCC0000);
        EntityRegistry.registerModEntity(ENTITY_HIGHSPEEDCREEPER_RL, Entity_HighSpeedCreeper.class, RL_NAME_SUFFIX_CAPITAL + "HighSpeedCreeper", 5, advancedTools, 250, 1, true, 0x0033FF, 0x0033FF);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.HighSpeedCreeper", 0x0033FF);
        EntityRegistry.registerModEntity(ENTITY_GOLDCREEPER_RL, Entity_GoldCreeper.class, RL_NAME_SUFFIX_CAPITAL + "GoldCreeper", 6, advancedTools, 250, 1, true,0xFFFF00,0xFFFF00);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.GoldCreeper", 0xFFFF00);
        EntityRegistry.registerModEntity(ENTITY_BBFIREBALL_RL, Entity_BBFireBall.class, RL_NAME_SUFFIX_CAPITAL + "BBFireBall", 7, advancedTools, 250, 1, true);
        EntityRegistry.registerModEntity(ENTITY_IHFROZENMOB_RL, Entity_IHFrozenMob.class, RL_NAME_SUFFIX_CAPITAL + "IHFrozenMob", 8, advancedTools, 250, 1, true);
        EntityRegistry.registerModEntity(ENTITY_PGPOWERBOMB_RL, Entity_PGPowerBomb.class, RL_NAME_SUFFIX_CAPITAL + "PGPowerBomb", 9, advancedTools, 250, 1, true);
        EntityRegistry.registerModEntity(ENTITY_SBWINDEDGE_RL, Entity_SBWindEdge.class, RL_NAME_SUFFIX_CAPITAL + "SBWindEdge", 10, advancedTools, 250, 1, true);
        if (AdvancedTools.spawnHiGradeMob) {
            for (Biome biome : Biome.REGISTRY) {
                if (biome != null
                        && isSpawnableBiomeType(biome)
                        && biome.getSpawnableList(EnumCreatureType.MONSTER).size() >= 5) {
                    if (AdvancedTools.spawnHighSkeleton)
                        EntityRegistry.addSpawn(Entity_HighSkeleton.class, 2, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (AdvancedTools.spawnSkeletonSniper)
                        EntityRegistry.addSpawn(Entity_SkeletonSniper.class, 3, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (AdvancedTools.spawnZombieWarrior)
                        EntityRegistry.addSpawn(Entity_ZombieWarrior.class, 2, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (AdvancedTools.spawnFireZombie)
                        EntityRegistry.addSpawn(Entity_FireZombie.class, 3, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (AdvancedTools.spawnHighSpeedCreeper)
                        EntityRegistry.addSpawn(Entity_HighSpeedCreeper.class, 3, 4, 4, EnumCreatureType.MONSTER,
                                biome);
                    if (AdvancedTools.spawnGoldCreeper)
                        EntityRegistry.addSpawn(Entity_GoldCreeper.class, 1, 1, 4, EnumCreatureType.MONSTER,
                                biome);
                }
            }
        }
    }

    private boolean isSpawnableBiomeType(Biome biome) {
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
        for (BiomeDictionary.Type type : types) {
            if (type == BiomeDictionary.Type.NETHER
                    || type == BiomeDictionary.Type.MUSHROOM
                    || type == BiomeDictionary.Type.END) {
                return false;
            }
        }
        return true;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void itemSetup(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(RedEnhancer);
        AdvancedTools.list.add(RedEnhancer);
        registry.register(BlueEnhancer);
        AdvancedTools.list.add(BlueEnhancer);
        registry.register(UGWoodShovel);
        AdvancedTools.list.add(UGWoodShovel);
        registry.register(UGStoneShovel);
        AdvancedTools.list.add(UGStoneShovel);
        registry.register(UGIronShovel);
        AdvancedTools.list.add(UGIronShovel);
        registry.register(UGDiamondShovel);
        AdvancedTools.list.add(UGDiamondShovel);
        registry.register(UGGoldShovel);
        AdvancedTools.list.add(UGGoldShovel);
        registry.register(UGWoodPickaxe);
        AdvancedTools.list.add(UGWoodPickaxe);
        registry.register(UGStonePickaxe);
        AdvancedTools.list.add(UGStonePickaxe);
        registry.register(UGIronPickaxe);
        AdvancedTools.list.add(UGIronPickaxe);
        registry.register(UGDiamondPickaxe);
        AdvancedTools.list.add(UGDiamondPickaxe);
        registry.register(UGGoldPickaxe);
        AdvancedTools.list.add(UGGoldPickaxe);
        registry.register(UGWoodAxe);
        AdvancedTools.list.add(UGWoodAxe);
        registry.register(UGStoneAxe);
        AdvancedTools.list.add(UGStoneAxe);
        registry.register(UGIronAxe);
        AdvancedTools.list.add(UGIronAxe);
        registry.register(UGDiamondAxe);
        AdvancedTools.list.add(UGDiamondAxe);
        registry.register(UGGoldAxe);
        AdvancedTools.list.add(UGGoldAxe);
        registry.register(BlazeBlade);
        AdvancedTools.list.add(BlazeBlade);
        registry.register(IceHold);
        AdvancedTools.list.add(IceHold);
        registry.register(AsmoSlasher);
        AdvancedTools.list.add(AsmoSlasher);
        registry.register(PlanetGuardian);
        AdvancedTools.list.add(PlanetGuardian);
        registry.register(StormBringer);
        AdvancedTools.list.add(StormBringer);
        registry.register(NEGI);
        AdvancedTools.list.add(NEGI);
        registry.register(LuckLuck);
        AdvancedTools.list.add(LuckLuck);
        registry.register(SmashBat);
        AdvancedTools.list.add(SmashBat);
        registry.register(DevilSword);
        AdvancedTools.list.add(DevilSword);
        registry.register(HolySaber);
        AdvancedTools.list.add(HolySaber);
        registry.register(ThrowingKnife);
        AdvancedTools.list.add(ThrowingKnife);
        registry.register(PoisonKnife);
        AdvancedTools.list.add(PoisonKnife);
        registry.register(CrossBow);
        AdvancedTools.list.add(CrossBow);
        registry.register(InfiniteSword);
        AdvancedTools.list.add(InfiniteSword);
        registry.register(InfinitePickaxe);
        AdvancedTools.list.add(InfinitePickaxe);
        registry.register(InfiniteAxe);
        AdvancedTools.list.add(InfiniteAxe);
        registry.register(InfiniteShovel);
        AdvancedTools.list.add(InfiniteShovel);
        registry.register(InfiniteHoe);
        AdvancedTools.list.add(InfiniteHoe);
        registry.register(GenocideBlade);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void addRecipe(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
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
        registry.register(new ShapedOreRecipe(InfiniteSword.getRegistryName(),
                new ItemStack(InfiniteSword, 1),
                " #X", "XY#", "ZX ",
                'X', Blocks.END_STONE,
                'Y', Items.ENDER_EYE,
                'Z', "stickWood",
                '#', Blocks.GLOWSTONE).setRegistryName(InfiniteSword.getRegistryName()));
        registry.register(new ShapedOreRecipe(InfiniteHoe.getRegistryName(),
                new ItemStack(InfiniteHoe, 1),
                "XX", " Y", " Z",
                'X', Blocks.END_STONE,
                'Y', Items.ENDER_EYE,
                'Z', "stickWood").setRegistryName(InfiniteHoe.getRegistryName()));
        registry.register(new ShapedOreRecipe(RedEnhancer.getRegistryName(),
                new ItemStack(RedEnhancer, 2),
                " X ", "XZX", " X ",
                'X', Items.GOLD_NUGGET,
                'Z', Items.REDSTONE).setRegistryName(RedEnhancer.getRegistryName()));
        registry.register(new ShapelessOreRecipe(Items.REDSTONE.getRegistryName(),
                new ItemStack(Items.REDSTONE),
                RedEnhancer, RedEnhancer)
        .setRegistryName(AdvancedTools.MOD_ID, Items.REDSTONE.getRegistryName().getResourcePath() + RL_NAME_SUFFIX));
        registry.register(new ShapelessOreRecipe(Items.GOLD_INGOT.getRegistryName(),
                new ItemStack(Items.GOLD_INGOT),
                RedEnhancer,
                RedEnhancer,
                RedEnhancer,
                RedEnhancer,
                RedEnhancer,
                RedEnhancer,
                RedEnhancer,
                RedEnhancer,
                RedEnhancer).setRegistryName(AdvancedTools.MOD_ID, Items.GOLD_INGOT.getRegistryName().getResourcePath() + RL_NAME_SUFFIX));
        registry.register(new ShapedOreRecipe(BlueEnhancer.getRegistryName(),
                new ItemStack(BlueEnhancer, 2),
                "RXR", "XZX", "RXR",
                'X', Items.GOLD_INGOT,
                'Z', Items.DIAMOND,
                'R', Items.REDSTONE).setRegistryName(BlueEnhancer.getRegistryName()));
        registry.register(new ShapelessOreRecipe(Items.GOLD_INGOT.getRegistryName(),
                new ItemStack(Items.GOLD_INGOT),
                BlueEnhancer,
                BlueEnhancer).setRegistryName(AdvancedTools.MOD_ID, Items.GOLD_INGOT.getRegistryName().getResourcePath() + RL_NAME_SUFFIX + "2"));
        registry.register(new ShapelessOreRecipe(Items.DIAMOND.getRegistryName(),
                new ItemStack(Items.DIAMOND),
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer,
                BlueEnhancer).setRegistryName(AdvancedTools.MOD_ID, Items.DIAMOND.getRegistryName().getResourcePath() + RL_NAME_SUFFIX));

        for (int var5 = 0; var5 < var1[0].length; ++var5) {
            for (int var6 = 0; var6 < var1.length; ++var6) {
                registry.register(new ShapedOreRecipe(var1[var6][var5].getRegistryName(),
                        new ItemStack(var1[var6][var5]),
                        var4[var6],
                        'X', var3[var5],
                        '#', var2[var5],
                        'Z', "stickWood").setRegistryName(var1[var6][var5].getRegistryName()));
            }
        }

        registry.register(new ShapedOreRecipe(BlazeBlade.getRegistryName(),
                new ItemStack(BlazeBlade),
                " P#", "XEP", "IX ",
                'I', Items.BLAZE_ROD,
                'X', Items.DIAMOND,
                '#', Items.IRON_INGOT,
                'E', BlueEnhancer,
                'P', Items.BLAZE_POWDER).setRegistryName(BlazeBlade.getRegistryName()));
        registry.register(new ShapedOreRecipe(IceHold.getRegistryName(),
                new ItemStack(IceHold),
                " P#", "XEP", "IX ",
                'I', "stickWood",
                'X', Items.IRON_INGOT,
                '#', Items.WATER_BUCKET,
                'E', BlueEnhancer,
                'P', Blocks.SNOW).setRegistryName(IceHold.getRegistryName()));
        registry.register(new ShapedOreRecipe(AsmoSlasher.getRegistryName(),
                new ItemStack(AsmoSlasher),
                " P#", "XEP", "IX ",
                'I', "stickWood",
                'X', Items.GOLD_INGOT,
                '#', Items.IRON_INGOT,
                'E', BlueEnhancer,
                'P', Items.REDSTONE).setRegistryName(AsmoSlasher.getRegistryName()));
        registry.register(new ShapedOreRecipe(PlanetGuardian.getRegistryName(),
                new ItemStack(PlanetGuardian),
                " ##", "#E#", "I# ",
                'I', "stickWood",
                '#', Items.IRON_INGOT,
                'E', BlueEnhancer).setRegistryName(PlanetGuardian.getRegistryName()));
        registry.register(new ShapedOreRecipe(StormBringer.getRegistryName(),
                new ItemStack(StormBringer),
                "  #", "XE ", "IX ",
                'I', "stickWood",
                'X', Items.FEATHER,
                '#', Items.IRON_INGOT,
                'E', BlueEnhancer).setRegistryName(StormBringer.getRegistryName()));
        registry.register(new ShapedOreRecipe(LuckLuck.getRegistryName(),
                new ItemStack(LuckLuck),
                "  #", "#E ", "I# ",
                'I', "stickWood",
                '#', Items.GOLD_INGOT,
                'E', RedEnhancer).setRegistryName(LuckLuck.getRegistryName()));
        registry.register(new ShapedOreRecipe(SmashBat.getRegistryName(),
                new ItemStack(SmashBat),
                "#", "#", "#",
                '#', "logWood").setRegistryName(SmashBat.getRegistryName()));
        registry.register(new ShapelessOreRecipe(NEGI.getRegistryName(),
                new ItemStack(NEGI),
                Items.WATER_BUCKET, Items.WHEAT_SEEDS, Blocks.DIRT).setRegistryName(NEGI.getRegistryName()));
        registry.register(new ShapedOreRecipe(HolySaber.getRegistryName(),
                new ItemStack(HolySaber),
                "XBX", "EAC", "XDX",
                'A', BlazeBlade,
                'B', IceHold,
                'C', AsmoSlasher,
                'D', PlanetGuardian,
                'E', StormBringer,
                'X', Blocks.GLOWSTONE).setRegistryName(HolySaber.getRegistryName()));
        registry.register(new ShapedOreRecipe(ThrowingKnife.getRegistryName(),
                new ItemStack(ThrowingKnife, 16),
                " X", "# ",
                'X', Items.IRON_INGOT, '#', "stickWood").setRegistryName(ThrowingKnife.getRegistryName()));
        registry.register(new ShapelessOreRecipe(PoisonKnife.getRegistryName(),
                new ItemStack(PoisonKnife), ThrowingKnife, Items.SPIDER_EYE).setRegistryName(PoisonKnife.getRegistryName()));
    }
}
