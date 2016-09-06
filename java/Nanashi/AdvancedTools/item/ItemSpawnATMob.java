package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

/**
 * 追加モブ用スポーンエッグアイテム
 * Created by A.K. on 14/12/16.
 */
public class ItemSpawnATMob extends Item implements IItemColor{
    private static final Map<String, Integer> COLOR_MAP = Maps.newHashMap();

    public ItemSpawnATMob() {
        super();
        this.setHasSubtypes(true);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return EnumActionResult.PASS;
        if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return EnumActionResult.FAIL;
        }
        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("mob_name")) {
            return EnumActionResult.FAIL;
        }
        Entity entity = EntityList.createEntityByName(stack.getTagCompound().getString("mob_name"), worldIn);
        if (entity == null) {
            AdvancedTools.LOGGER.warning("illegal mob name!!");
            return EnumActionResult.FAIL;
        }
        entity.moveToBlockPosAndAngles(pos.offset(side), MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
        worldIn.spawnEntityInWorld(entity);
        if (!playerIn.capabilities.isCreativeMode) {
            stack.stackSize--;
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("mob_name")) {
            String mobName = stack.getTagCompound().getString("mob_name");
            if (COLOR_MAP.containsKey(mobName)) {
                return COLOR_MAP.get(mobName);
            }
        }
        return 0;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("mob_name")) {
            String mobName = stack.getTagCompound().getString("mob_name");
            String s = ("" + I18n.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
            mobName = I18n.translateToLocal(String.format("entity.%s.name", mobName));
            return String.format("%s : %s", s, mobName);
        }
        return super.getItemStackDisplayName(stack);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        ItemStack stack;
        for (String name : COLOR_MAP.keySet()) {
            stack = new ItemStack(itemIn);
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setString("mob_name", name);
            subItems.add(stack);
        }
    }

    public static void registerMobColor(String mobName, int color) {
        if (COLOR_MAP.containsKey(mobName)) {
            AdvancedTools.LOGGER.warning(String.format("%s has already registered", mobName));
        }
        COLOR_MAP.put(mobName, color);
    }
}
