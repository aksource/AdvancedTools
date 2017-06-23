package Nanashi.AdvancedTools;

import Nanashi.AdvancedTools.entity.*;
import Nanashi.AdvancedTools.item.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;
import java.util.Set;

/**
 * ユーティリティクラス。初期化処理とか
 * Created by A.K. on 2017/01/21.
 */
public class AdvToolsUtil {
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
        EntityRegistry.registerModEntity(ENTITY_THROWINGKNIFE_RL, Entity_ThrowingKnife.class, "ThrowingKnife", 0, advancedTools, 250, 5, true);
        EntityRegistry.registerModEntity(ENTITY_HIGHSKELETON_RL, Entity_HighSkeleton.class, "HighSkeleton", 1, advancedTools, 250, 1, true, 0x0000FF, 0x0000FF);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.HighSkeleton", 0x0000FF);
        EntityRegistry.registerModEntity(ENTITY_SKELETONSNIPER_RL, Entity_SkeletonSniper.class, "SkeletonSniper", 2, advancedTools, 250, 1, true, 0x000000, 0x000000);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.SkeletonSniper", 0x000000);
        EntityRegistry.registerModEntity(ENTITY_ZOMBIEWARRIOR_RL, Entity_ZombieWarrior.class, "ZombieWarrior", 3, advancedTools, 250, 1, true, 0xCC00CC, 0xCC00CC);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.ZombieWarrior", 0xCC00CC);
        EntityRegistry.registerModEntity(ENTITY_FIREZOMBIE_RL, Entity_FireZombie.class, "FireZombie", 4, advancedTools, 250, 1, true, 0xCC0000, 0xCC0000);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.FireZombie", 0xCC0000);
        EntityRegistry.registerModEntity(ENTITY_HIGHSPEEDCREEPER_RL, Entity_HighSpeedCreeper.class, "HighSpeedCreeper", 5, advancedTools, 250, 1, true, 0x0033FF, 0x0033FF);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.HighSpeedCreeper", 0x0033FF);
        EntityRegistry.registerModEntity(ENTITY_GOLDCREEPER_RL, Entity_GoldCreeper.class, "GoldCreeper", 6, advancedTools, 250, 1, true,0xFFFF00,0xFFFF00);
//        ItemSpawnATMob.registerMobColor("AdvancedTools.GoldCreeper", 0xFFFF00);
        EntityRegistry.registerModEntity(ENTITY_BBFIREBALL_RL, Entity_BBFireBall.class, "BBFireBall", 7, advancedTools, 250, 1, true);
        EntityRegistry.registerModEntity(ENTITY_IHFROZENMOB_RL, Entity_IHFrozenMob.class, "IHFrozenMob", 8, advancedTools, 250, 1, true);
        EntityRegistry.registerModEntity(ENTITY_PGPOWERBOMB_RL, Entity_PGPowerBomb.class, "PGPowerBomb", 9, advancedTools, 250, 1, true);
        EntityRegistry.registerModEntity(ENTITY_SBWINDEDGE_RL, Entity_SBWindEdge.class, "SBWindEdge", 10, advancedTools, 250, 1, true);
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

    public void itemSetup() {
        AdvancedTools.RedEnhancer = (new ItemEnhancer(0)).setRegistryName("redenhancer").setUnlocalizedName("RedEnhancer").setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.RedEnhancer);
        AdvancedTools.list.add(AdvancedTools.RedEnhancer);
        AdvancedTools.BlueEnhancer = (new ItemEnhancer(1)).setRegistryName("blueenhancer").setUnlocalizedName("BlueEnhancer").setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.BlueEnhancer);
        AdvancedTools.list.add(AdvancedTools.BlueEnhancer);
        AdvancedTools.UGWoodShovel = (new ItemUGShovel(Item.ToolMaterial.WOOD, 1.0F)).setRegistryName("ugwoodshovel").setUnlocalizedName("UpgradedWoodenShovel")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGWoodShovel);
        AdvancedTools.list.add(AdvancedTools.UGWoodShovel);
        AdvancedTools.UGStoneShovel = (new ItemUGShovel(Item.ToolMaterial.STONE, 1.5F)).setRegistryName("ugstoneshovel").setUnlocalizedName("UpgradedStoneShovel")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGStoneShovel);
        AdvancedTools.list.add(AdvancedTools.UGStoneShovel);
        AdvancedTools.UGIronShovel = (new ItemUGShovel(Item.ToolMaterial.IRON, 2.0F)).setRegistryName("ugironshovel").setUnlocalizedName("UpgradedIronShovel")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGIronShovel);
        AdvancedTools.list.add(AdvancedTools.UGIronShovel);
        AdvancedTools.UGDiamondShovel = (new ItemUGShovel(Item.ToolMaterial.DIAMOND, 3.0F)).setRegistryName("ugdiamondshovel").setUnlocalizedName("UpgradedDiamondShovel")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGDiamondShovel);
        AdvancedTools.list.add(AdvancedTools.UGDiamondShovel);
        AdvancedTools.UGGoldShovel = (new ItemUGShovel(Item.ToolMaterial.GOLD, 2.5F)).setRegistryName("uggoldshovel").setUnlocalizedName("UpgradedGoldenShovel")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGGoldShovel);
        AdvancedTools.list.add(AdvancedTools.UGGoldShovel);
        AdvancedTools.UGWoodPickaxe = (new ItemUGPickaxe(Item.ToolMaterial.WOOD, 1.0F)).setRegistryName("ugwoodpickaxe").setUnlocalizedName("UpgradedWoodenPickaxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGWoodPickaxe);
        AdvancedTools.list.add(AdvancedTools.UGWoodPickaxe);
        AdvancedTools.UGStonePickaxe = (new ItemUGPickaxe(Item.ToolMaterial.STONE, 1.5F)).setRegistryName("ugstonepickaxe").setUnlocalizedName("UpgradedStonePickaxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGStonePickaxe);
        AdvancedTools.list.add(AdvancedTools.UGStonePickaxe);
        AdvancedTools.UGIronPickaxe = (new ItemUGPickaxe(Item.ToolMaterial.IRON, 2.0F)).setRegistryName("ugironpickaxe").setUnlocalizedName("UpgradedIronPickaxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGIronPickaxe);
        AdvancedTools.list.add(AdvancedTools.UGIronPickaxe);
        AdvancedTools.UGDiamondPickaxe = (new ItemUGPickaxe(Item.ToolMaterial.DIAMOND, 3.0F)).setRegistryName("ugdiamondpickaxe").setUnlocalizedName("UpgradedDiamondPickaxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGDiamondPickaxe);
        AdvancedTools.list.add(AdvancedTools.UGDiamondPickaxe);
        AdvancedTools.UGGoldPickaxe = (new ItemUGPickaxe(Item.ToolMaterial.GOLD, 2.5F)).setRegistryName("uggoldpickaxe").setUnlocalizedName("UpgradedGoldenPickaxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGGoldPickaxe);
        AdvancedTools.list.add(AdvancedTools.UGGoldPickaxe);
        AdvancedTools.UGWoodAxe = (new ItemUGAxe(Item.ToolMaterial.WOOD, ItemAxe.ATTACK_DAMAGES[0], ItemAxe.ATTACK_SPEEDS[0], 1.0F)).setRegistryName("ugwoodaxe").setUnlocalizedName("UpgradedWoodenAxe").setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGWoodAxe);
        AdvancedTools.list.add(AdvancedTools.UGWoodAxe);
        AdvancedTools.UGStoneAxe = (new ItemUGAxe(Item.ToolMaterial.STONE, ItemAxe.ATTACK_DAMAGES[1], ItemAxe.ATTACK_SPEEDS[1], 1.5F)).setRegistryName("ugstoneaxe").setUnlocalizedName("UpgradedStoneAxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGStoneAxe);
        AdvancedTools.list.add(AdvancedTools.UGStoneAxe);
        AdvancedTools.UGIronAxe = (new ItemUGAxe(Item.ToolMaterial.IRON, ItemAxe.ATTACK_DAMAGES[2], ItemAxe.ATTACK_SPEEDS[2], 2.0F)).setRegistryName("ugironaxe").setUnlocalizedName("UpgradedIronAxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGIronAxe);
        AdvancedTools.list.add(AdvancedTools.UGIronAxe);
        AdvancedTools.UGDiamondAxe = (new ItemUGAxe(Item.ToolMaterial.DIAMOND, ItemAxe.ATTACK_DAMAGES[3], ItemAxe.ATTACK_SPEEDS[3], 3.0F)).setRegistryName("ugdiamondaxe").setUnlocalizedName("UpgradedDiamondAxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGDiamondAxe);
        AdvancedTools.list.add(AdvancedTools.UGDiamondAxe);
        AdvancedTools.UGGoldAxe = (new ItemUGAxe(Item.ToolMaterial.GOLD, ItemAxe.ATTACK_DAMAGES[4], ItemAxe.ATTACK_SPEEDS[4], 2.5F)).setRegistryName("uggoldaxe").setUnlocalizedName("UpgradedGoldenAxe")
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.UGGoldAxe);
        AdvancedTools.list.add(AdvancedTools.UGGoldAxe);
        AdvancedTools.BlazeBlade = (new ItemUQBlazeBlade(Item.ToolMaterial.DIAMOND, 4)).setRegistryName("blazeblade").setUnlocalizedName("BlazeBlade")
                .setMaxDamage(1200).setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.BlazeBlade);
        AdvancedTools.list.add(AdvancedTools.BlazeBlade);
        AdvancedTools.IceHold = (new ItemUQIceHold(Item.ToolMaterial.DIAMOND, 4)).setRegistryName("icehold").setUnlocalizedName("FreezeHold").setMaxDamage(1200)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.IceHold);
        AdvancedTools.list.add(AdvancedTools.IceHold);
        AdvancedTools.AsmoSlasher = (new ItemUQAsmoSlasher(Item.ToolMaterial.DIAMOND, 4)).setRegistryName("asmoslasher").setUnlocalizedName("AsmoSlasher")
                .setMaxDamage(1200).setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.AsmoSlasher);
        AdvancedTools.list.add(AdvancedTools.AsmoSlasher);
        AdvancedTools.PlanetGuardian = (new ItemUQPlanetGuardian(Item.ToolMaterial.DIAMOND, 4)).setRegistryName("planetguardian").setUnlocalizedName("Planet Guardian")
                .setMaxDamage(1200).setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.PlanetGuardian);
        AdvancedTools.list.add(AdvancedTools.PlanetGuardian);
        AdvancedTools.StormBringer = (new ItemUQStormBringer(Item.ToolMaterial.DIAMOND, 4)).setRegistryName("stormbringer").setUnlocalizedName("StormBringer")
                .setMaxDamage(1200).setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.StormBringer);
        AdvancedTools.list.add(AdvancedTools.StormBringer);
        AdvancedTools.NEGI = (new Item_Negi(1, 0.1F, false)).setRegistryName("negi").setUnlocalizedName("NEGI").setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.NEGI);
        AdvancedTools.list.add(AdvancedTools.NEGI);
        AdvancedTools.LuckLuck = (new ItemUQLuckies(Item.ToolMaterial.GOLD, 2)).setRegistryName("luckluck").setUnlocalizedName("Lucky&Lucky").setMaxDamage(77)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.LuckLuck);
        AdvancedTools.list.add(AdvancedTools.LuckLuck);
        AdvancedTools.SmashBat = (new ItemUniqueArms(Item.ToolMaterial.WOOD, 1)).setRegistryName("smashbat").setUnlocalizedName("SmashBat").setMaxDamage(95)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.SmashBat);
        AdvancedTools.list.add(AdvancedTools.SmashBat);
        AdvancedTools.DevilSword = (new ItemUQDevilSword(Item.ToolMaterial.DIAMOND, 1)).setRegistryName("devilsword").setUnlocalizedName("DevilSword").setMaxDamage(427)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.DevilSword);
        AdvancedTools.list.add(AdvancedTools.DevilSword);
        AdvancedTools.HolySaber = (new ItemUQHolySaber(Item.ToolMaterial.GOLD, 5)).setRegistryName("holysaber").setUnlocalizedName("HolySaber").setMaxDamage(280)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.HolySaber);
        AdvancedTools.list.add(AdvancedTools.HolySaber);
        AdvancedTools.ThrowingKnife = (new Item_ThrowingKnife(false)).setRegistryName("throwingknife").setUnlocalizedName("ThrowingKnife").setCreativeTab(AdvancedTools.tabsAT)
				/*.setTextureName(AdvancedTools.textureDomain + "ThrowingKnife")*/;
        GameRegistry.register(AdvancedTools.ThrowingKnife);
        AdvancedTools.list.add(AdvancedTools.ThrowingKnife);
        AdvancedTools.PoisonKnife = (new Item_ThrowingKnife(true)).setRegistryName("poisonknife").setUnlocalizedName("PoisonKnife").setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.PoisonKnife);
        AdvancedTools.list.add(AdvancedTools.PoisonKnife);
        AdvancedTools.CrossBow = (new Item_CrossBow()).setRegistryName("crossbow").setUnlocalizedName("A_CrossBow").setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.CrossBow);
        AdvancedTools.list.add(AdvancedTools.CrossBow);
        AdvancedTools.InfiniteSword = (new ItemUniqueArms(Item.ToolMaterial.GOLD, 8)).setRegistryName("infinitesword").setUnlocalizedName("InfiniteSword").setMaxDamage(0)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.InfiniteSword);
        AdvancedTools.list.add(AdvancedTools.InfiniteSword);
        AdvancedTools.InfinitePickaxe = (new ItemUGPickaxe(Item.ToolMaterial.DIAMOND, 1.0F)).setRegistryName("infinitepickaxe").setUnlocalizedName("InfinityPickaxe")
                .setMaxDamage(0).setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.InfinitePickaxe);
        AdvancedTools.list.add(AdvancedTools.InfinitePickaxe);
        AdvancedTools.InfiniteAxe = (new ItemUGAxe(Item.ToolMaterial.GOLD, ItemAxe.ATTACK_DAMAGES[4], ItemAxe.ATTACK_SPEEDS[4], 1.0F)).setRegistryName("infiniteaxe").setUnlocalizedName("InfinityAxe").setMaxDamage(0)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.InfiniteAxe);
        AdvancedTools.list.add(AdvancedTools.InfiniteAxe);
        AdvancedTools.InfiniteShovel = (new ItemUGShovel(Item.ToolMaterial.GOLD, 1.0F)).setRegistryName("infiniteshovel").setUnlocalizedName("InfinityShovel").setMaxDamage(0)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.InfiniteShovel);
        AdvancedTools.list.add(AdvancedTools.InfiniteShovel);
        AdvancedTools.InfiniteHoe = (new ItemHoe(Item.ToolMaterial.GOLD)).setRegistryName("infinitehoe").setUnlocalizedName("InfinityHoe").setMaxDamage(0)
                .setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.InfiniteHoe);
        AdvancedTools.list.add(AdvancedTools.InfiniteHoe);
        AdvancedTools.GenocideBlade = (new ItemUniqueArms(Item.ToolMaterial.DIAMOND, 10000)).setRegistryName("genocideblade").setUnlocalizedName("GenocideBlade")
                .setMaxDamage(0).setCreativeTab(AdvancedTools.tabsAT);
        GameRegistry.register(AdvancedTools.GenocideBlade);
//        spawnItem = new ItemSpawnATMob().setRegistryName("spawn_item").setUnlocalizedName("spawnItem").setCreativeTab(tabsAT);
//        GameRegistry.register(spawnItem);
    }

    public void addRecipe() {
        Item[][] var1 = new Item[][]{
                {AdvancedTools.UGWoodShovel, AdvancedTools.UGStoneShovel, AdvancedTools.UGIronShovel, AdvancedTools.UGDiamondShovel, AdvancedTools.UGGoldShovel,
                        AdvancedTools.InfiniteShovel},
                {AdvancedTools.UGWoodPickaxe, AdvancedTools.UGStonePickaxe, AdvancedTools.UGIronPickaxe, AdvancedTools.UGDiamondPickaxe, AdvancedTools.UGGoldPickaxe,
                        AdvancedTools.InfinitePickaxe},
                {AdvancedTools.UGWoodAxe, AdvancedTools.UGStoneAxe, AdvancedTools.UGIronAxe, AdvancedTools.UGDiamondAxe, AdvancedTools.UGGoldAxe, AdvancedTools.InfiniteAxe}};
        Object[] var2 = new Object[]{"stickWood", AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer, AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer,
                Items.ENDER_EYE};
        Object[] var3 = new Object[]{"logWood", "cobblestone", Items.IRON_INGOT, Items.DIAMOND,
                Items.GOLD_INGOT, Blocks.END_STONE};
        String[][] var4 = new String[][]{{"X", "#", "Z"}, {"XXX", " # ", " Z "}, {"XX", "X#", " Z"}};
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.InfiniteSword, 1), " #X", "XY#", "ZX ", 'X',
                Blocks.END_STONE, 'Y', Items.ENDER_EYE, 'Z', "stickWood", '#', Blocks.GLOWSTONE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.InfiniteHoe, 1), "XX", " Y", " Z", 'X', Blocks.END_STONE,
                'Y', Items.ENDER_EYE, 'Z', "stickWood"));
        GameRegistry.addRecipe(new ItemStack(AdvancedTools.RedEnhancer, 2), " X ", "XZX", " X ", 'X',
                Items.GOLD_NUGGET, 'Z', Items.REDSTONE);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.REDSTONE), AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.GOLD_INGOT), AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer,
                AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer, AdvancedTools.RedEnhancer);
        GameRegistry.addRecipe(new ItemStack(AdvancedTools.BlueEnhancer, 2), "RXR", "XZX", "RXR", 'X',
                Items.GOLD_INGOT, 'Z', Items.DIAMOND, 'R', Items.REDSTONE);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.GOLD_INGOT), AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DIAMOND), AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer,
                AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer, AdvancedTools.BlueEnhancer);

        for (int var5 = 0; var5 < var1[0].length; ++var5) {
            for (int var6 = 0; var6 < var1.length; ++var6) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(var1[var6][var5]), var4[var6], 'X', var3[var5],
                        '#', var2[var5], 'Z', "stickWood"));
            }
        }

        GameRegistry.addRecipe(new ItemStack(AdvancedTools.BlazeBlade), " P#", "XEP", "IX ", 'I', Items.BLAZE_ROD,
                'X', Items.DIAMOND, '#', Items.IRON_INGOT, 'E', AdvancedTools.BlueEnhancer, 'P', Items.BLAZE_POWDER);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.IceHold), " P#", "XEP", "IX ", 'I', "stickWood", 'X',
                Items.IRON_INGOT, '#', Items.WATER_BUCKET, 'E', AdvancedTools.BlueEnhancer, 'P', Blocks.SNOW));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.AsmoSlasher), " P#", "XEP", "IX ", 'I', "stickWood", 'X',
                Items.GOLD_INGOT, '#', Items.IRON_INGOT, 'E', AdvancedTools.BlueEnhancer, 'P', Items.REDSTONE));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.PlanetGuardian), " ##", "#E#", "I# ", 'I', "stickWood",
                '#', Items.IRON_INGOT, 'E', AdvancedTools.BlueEnhancer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.StormBringer), "  #", "XE ", "IX ", 'I', "stickWood", 'X',
                Items.FEATHER, '#', Items.IRON_INGOT, 'E', AdvancedTools.BlueEnhancer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.LuckLuck), "  #", "#E ", "I# ", 'I', "stickWood", '#',
                Items.GOLD_INGOT, 'E', AdvancedTools.RedEnhancer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.SmashBat), "#", "#", "#", '#', "logWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(AdvancedTools.NEGI), Items.WATER_BUCKET, Items.WHEAT_SEEDS,
                Blocks.DIRT);
        GameRegistry.addRecipe(new ItemStack(AdvancedTools.HolySaber), "XBX", "EAC", "XDX", 'A', AdvancedTools.BlazeBlade, 'B',
                AdvancedTools.IceHold, 'C', AdvancedTools.AsmoSlasher, 'D', AdvancedTools.PlanetGuardian, 'E', AdvancedTools.StormBringer, 'X', Blocks.GLOWSTONE);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AdvancedTools.ThrowingKnife, 16), " X", "# ", 'X', Items.IRON_INGOT, '#',
                "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(AdvancedTools.PoisonKnife), AdvancedTools.ThrowingKnife, Items.SPIDER_EYE);
    }
}
