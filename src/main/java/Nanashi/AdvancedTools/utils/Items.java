package Nanashi.AdvancedTools.utils;

import Nanashi.AdvancedTools.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;

import static Nanashi.AdvancedTools.utils.AdvToolsUtil.tabsAT;

/**
 * Created by A.K. on 2018/03/21.
 */
public class Items {
    public static Item RedEnhancer = new ItemEnhancer(0).setRegistryName("redenhancer").setUnlocalizedName("RedEnhancer").setCreativeTab(tabsAT);
    public static Item BlueEnhancer = new ItemEnhancer(1).setRegistryName("blueenhancer").setUnlocalizedName("BlueEnhancer").setCreativeTab(tabsAT);
    public static Item UGWoodShovel = new ItemUGShovel(Item.ToolMaterial.WOOD, 1.0F).setRegistryName("ugwoodshovel").setUnlocalizedName("UpgradedWoodenShovel").setCreativeTab(tabsAT);
    public static Item UGStoneShovel = new ItemUGShovel(Item.ToolMaterial.STONE, 1.5F).setRegistryName("ugstoneshovel").setUnlocalizedName("UpgradedStoneShovel").setCreativeTab(tabsAT);
    public static Item UGIronShovel = new ItemUGShovel(Item.ToolMaterial.IRON, 2.0F).setRegistryName("ugironshovel").setUnlocalizedName("UpgradedIronShovel").setCreativeTab(tabsAT);
    public static Item UGDiamondShovel = new ItemUGShovel(Item.ToolMaterial.DIAMOND, 3.0F).setRegistryName("ugdiamondshovel").setUnlocalizedName("UpgradedDiamondShovel").setCreativeTab(tabsAT);
    public static Item UGGoldShovel = new ItemUGShovel(Item.ToolMaterial.GOLD, 2.5F).setRegistryName("uggoldshovel").setUnlocalizedName("UpgradedGoldenShovel").setCreativeTab(tabsAT);
    public static Item UGWoodPickaxe = new ItemUGPickaxe(Item.ToolMaterial.WOOD, 1.0F).setRegistryName("ugwoodpickaxe").setUnlocalizedName("UpgradedWoodenPickaxe").setCreativeTab(tabsAT);
    public static Item UGStonePickaxe = new ItemUGPickaxe(Item.ToolMaterial.STONE, 1.5F).setRegistryName("ugstonepickaxe").setUnlocalizedName("UpgradedStonePickaxe").setCreativeTab(tabsAT);
    public static Item UGIronPickaxe = new ItemUGPickaxe(Item.ToolMaterial.IRON, 2.0F).setRegistryName("ugironpickaxe").setUnlocalizedName("UpgradedIronPickaxe").setCreativeTab(tabsAT);
    public static Item UGDiamondPickaxe = new ItemUGPickaxe(Item.ToolMaterial.DIAMOND, 3.0F).setRegistryName("ugdiamondpickaxe").setUnlocalizedName("UpgradedDiamondPickaxe").setCreativeTab(tabsAT);
    public static Item UGGoldPickaxe = new ItemUGPickaxe(Item.ToolMaterial.GOLD, 2.5F).setRegistryName("uggoldpickaxe").setUnlocalizedName("UpgradedGoldenPickaxe").setCreativeTab(tabsAT);
    public static Item UGWoodAxe = new ItemUGAxe(Item.ToolMaterial.WOOD, ItemAxe.ATTACK_DAMAGES[0], ItemAxe.ATTACK_SPEEDS[0], 1.0F).setRegistryName("ugwoodaxe").setUnlocalizedName("UpgradedWoodenAxe").setCreativeTab(tabsAT);
    public static Item UGStoneAxe = new ItemUGAxe(Item.ToolMaterial.STONE, ItemAxe.ATTACK_DAMAGES[1], ItemAxe.ATTACK_SPEEDS[1], 1.5F).setRegistryName("ugstoneaxe").setUnlocalizedName("UpgradedStoneAxe").setCreativeTab(tabsAT);
    public static Item UGIronAxe = new ItemUGAxe(Item.ToolMaterial.IRON, ItemAxe.ATTACK_DAMAGES[2], ItemAxe.ATTACK_SPEEDS[2], 2.0F).setRegistryName("ugironaxe").setUnlocalizedName("UpgradedIronAxe").setCreativeTab(tabsAT);
    public static Item UGDiamondAxe = new ItemUGAxe(Item.ToolMaterial.DIAMOND, ItemAxe.ATTACK_DAMAGES[3], ItemAxe.ATTACK_SPEEDS[3], 3.0F).setRegistryName("ugdiamondaxe").setUnlocalizedName("UpgradedDiamondAxe").setCreativeTab(tabsAT);
    public static Item UGGoldAxe = new ItemUGAxe(Item.ToolMaterial.GOLD, ItemAxe.ATTACK_DAMAGES[4], ItemAxe.ATTACK_SPEEDS[4], 2.5F).setRegistryName("uggoldaxe").setUnlocalizedName("UpgradedGoldenAxe").setCreativeTab(tabsAT);
    public static Item BlazeBlade = new ItemUQBlazeBlade(Item.ToolMaterial.DIAMOND, 4).setRegistryName("blazeblade").setUnlocalizedName("BlazeBlade").setMaxDamage(1200).setCreativeTab(tabsAT);
    public static Item IceHold = new ItemUQIceHold(Item.ToolMaterial.DIAMOND, 4).setRegistryName("icehold").setUnlocalizedName("FreezeHold").setMaxDamage(1200).setCreativeTab(tabsAT);
    public static Item AsmoSlasher = new ItemUQAsmoSlasher(Item.ToolMaterial.DIAMOND, 4).setRegistryName("asmoslasher").setUnlocalizedName("AsmoSlasher").setMaxDamage(1200).setCreativeTab(tabsAT);
    public static Item PlanetGuardian = new ItemUQPlanetGuardian(Item.ToolMaterial.DIAMOND, 4).setRegistryName("planetguardian").setUnlocalizedName("Planet Guardian").setMaxDamage(1200).setCreativeTab(tabsAT);
    public static Item StormBringer = new ItemUQStormBringer(Item.ToolMaterial.DIAMOND, 4).setRegistryName("stormbringer").setUnlocalizedName("StormBringer").setMaxDamage(1200).setCreativeTab(tabsAT);
    public static Item NEGI = new Item_Negi(1, 0.1F, false).setRegistryName("negi").setUnlocalizedName("NEGI").setCreativeTab(tabsAT);
    public static Item LuckLuck = new ItemUQLuckies(Item.ToolMaterial.GOLD, 2).setRegistryName("luckluck").setUnlocalizedName("Lucky&Lucky").setMaxDamage(77).setCreativeTab(tabsAT);
    public static Item SmashBat = new ItemUniqueArms(Item.ToolMaterial.WOOD, 1).setRegistryName("smashbat").setUnlocalizedName("SmashBat").setMaxDamage(95).setCreativeTab(tabsAT);
    public static Item DevilSword = new ItemUQDevilSword(Item.ToolMaterial.DIAMOND, 1).setRegistryName("devilsword").setUnlocalizedName("DevilSword").setMaxDamage(427).setCreativeTab(tabsAT);
    public static Item HolySaber = new ItemUQHolySaber(Item.ToolMaterial.GOLD, 5).setRegistryName("holysaber").setUnlocalizedName("HolySaber").setMaxDamage(280).setCreativeTab(tabsAT);
    public static Item ThrowingKnife = new Item_ThrowingKnife(false).setRegistryName("throwingknife").setUnlocalizedName("ThrowingKnife").setCreativeTab(tabsAT);
    public static Item PoisonKnife = new Item_ThrowingKnife(true).setRegistryName("poisonknife").setUnlocalizedName("PoisonKnife").setCreativeTab(tabsAT);
    public static Item CrossBow = new Item_CrossBow().setRegistryName("crossbow").setUnlocalizedName("A_CrossBow").setCreativeTab(tabsAT);
    public static Item InfiniteSword = new ItemUniqueArms(Item.ToolMaterial.GOLD, 8).setRegistryName("infinitesword").setUnlocalizedName("InfiniteSword").setMaxDamage(0).setCreativeTab(tabsAT);
    public static Item InfinitePickaxe = new ItemUGPickaxe(Item.ToolMaterial.DIAMOND, 1.0F).setRegistryName("infinitepickaxe").setUnlocalizedName("InfinityPickaxe").setMaxDamage(0).setCreativeTab(tabsAT);
    public static Item InfiniteAxe = new ItemUGAxe(Item.ToolMaterial.GOLD, ItemAxe.ATTACK_DAMAGES[4], ItemAxe.ATTACK_SPEEDS[4], 1.0F).setRegistryName("infiniteaxe").setUnlocalizedName("InfinityAxe").setMaxDamage(0).setCreativeTab(tabsAT);
    public static Item InfiniteShovel = new ItemUGShovel(Item.ToolMaterial.GOLD, 1.0F).setRegistryName("infiniteshovel").setUnlocalizedName("InfinityShovel").setMaxDamage(0).setCreativeTab(tabsAT);
    public static Item InfiniteHoe = new ItemHoe(Item.ToolMaterial.GOLD).setRegistryName("infinitehoe").setUnlocalizedName("InfinityHoe").setMaxDamage(0).setCreativeTab(tabsAT);
    public static Item GenocideBlade = new ItemUniqueArms(Item.ToolMaterial.DIAMOND, 10000).setRegistryName("genocideblade").setUnlocalizedName("GenocideBlade").setMaxDamage(0).setCreativeTab(tabsAT);
}
