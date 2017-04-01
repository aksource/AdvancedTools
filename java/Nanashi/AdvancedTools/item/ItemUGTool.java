package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * アップグレードツールの基底クラス。
 */
public abstract class ItemUGTool extends ItemTool {

    private static final String KEY_SIDE = "advancedtools|side";
    private static final Set<Block> REDSTONE_SET = ImmutableSet.of(Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE);
    private static final Set<Block> DIRT_SET = ImmutableSet.of(Blocks.DIRT, Blocks.GRASS);

    private final float durationModifier;
    private final String toolKind;
    private String BaseName;
    private int[] rangeArray = new int[]{2, 4, 7, 9, 9};

    /**
     * コンストラクタ
     * @param attackDamageIn 攻撃力
     * @param attackSpeedIn 攻撃速度
     * @param toolMaterial 素材
     * @param effectiveBlocksIn 破壊適用ブロック
     * @param durationModifier 耐久値係数
     * @param toolKind 道具種別
     */
    ItemUGTool(float attackDamageIn, float attackSpeedIn, ToolMaterial toolMaterial,
                         Set<Block> effectiveBlocksIn, float durationModifier, String toolKind) {
        super(attackDamageIn, attackSpeedIn, toolMaterial, effectiveBlocksIn);
        this.durationModifier = durationModifier;
        this.toolKind = toolKind;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return MathHelper.floor(durationModifier * super.getMaxDamage(stack));
    }

    public abstract boolean doChainDestruction(IBlockState state);

    public boolean isProperTool(IBlockState state) {
        return Optional.ofNullable(state.getBlock().getHarvestTool(state)).orElse("").equals(this.toolKind);
    }

    public int getConnectedDistance() {
        return 1;
    }

    public boolean ignoreMeta() {
        return false;
    }

    @Override
    public void onCreated(ItemStack itemStack, World worldIn, EntityPlayer playerIn) {
        getRange(itemStack);
    }

    @Override
    @Nonnull
    public Item setUnlocalizedName(@Nonnull String unlocalizedName) {
        super.setUnlocalizedName(unlocalizedName);

        if (this.BaseName == null) {
            this.BaseName = unlocalizedName;
        }

        return this;
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack itemStack, World worldIn, @Nonnull IBlockState blockState, @Nonnull BlockPos pos, @Nonnull EntityLivingBase breaker) {
        super.onBlockDestroyed(itemStack, worldIn, blockState, pos, breaker);
        if (!worldIn.isRemote) {
            itemStack.damageItem(1, breaker);
            int range = getRange(itemStack);
            if (range != 0 && breaker instanceof EntityPlayer && ForgeHooks.canHarvestBlock(blockState.getBlock(), (EntityPlayer) breaker, worldIn, pos)) {
                Set<BlockPos> searchedBlockPosSet = this.getSearchingBlockPosSet(itemStack, worldIn, getSide(itemStack), blockState, pos);
                List<BlockPos> connectedBlockPosList = this.getConnectedBlockList(pos, searchedBlockPosSet);
                for (BlockPos breakingBlockPos : connectedBlockPosList) {
                    this.checkAndDestroy(worldIn, breakingBlockPos, blockState, itemStack,(EntityPlayer) breaker);
                }
            }
            return true;
        }
        return false;
    }

    /*@Deprecated
    private boolean destroyAroundBlock(ItemStack var1, World world, IBlockState state, BlockPos blockPos, EntityPlayer var6, int range) {
        EnumFacing side = getSide(var1);
        this.searchAndDestroyBlock(world, blockPos, side, state, var1, var6, range);
        return true;
    }*/

    @Nonnull
    private List<BlockPos> getConnectedBlockList(BlockPos origin, @Nonnull Set<BlockPos> searchedBlockSet) {
        List<BlockPos> connectedBlockList = Lists.newArrayList();
        connectedBlockList.add(origin);
        boolean check = true;
        while(check) {
            check = false;
            Iterator<BlockPos> iterator = searchedBlockSet.iterator();
            while(iterator.hasNext()) {
                BlockPos blockPos = iterator.next();
                for (BlockPos listedBlockPos : connectedBlockList) {
                    if (isConnected(listedBlockPos, blockPos)) {
                        connectedBlockList.add(blockPos);
                        iterator.remove();
                        check = true;
                        break;
                    }
                }
            }
        }
        connectedBlockList.remove(origin);
        return connectedBlockList;
    }

    @Nonnull
    private Set<BlockPos> getSearchingBlockPosSet(@Nonnull ItemStack tool, World world, @Nonnull EnumFacing side, IBlockState originState, BlockPos blockPos) {
        int range = this.getRange(tool);
        int minX, minY, minZ, maxX, maxY, maxZ;

        if (!this.doChainDestruction(originState)) {
            minX = blockPos.getX() - range;
            minY = blockPos.getY() - range;
            minZ = blockPos.getZ() - range;
            maxX = blockPos.getX() + range;
            maxY = blockPos.getY() + range;
            maxZ = blockPos.getZ() + range;

            if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
                minY = blockPos.getY();
                maxY = blockPos.getY();
            } else {
                minY += range - AdvancedTools.digUnder;
                maxY += range - AdvancedTools.digUnder;
            }

            if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH) {
                minZ = blockPos.getZ();
                maxZ = blockPos.getZ();
            }

            if (side == EnumFacing.WEST || side == EnumFacing.EAST) {
                minX = blockPos.getX();
                maxX = blockPos.getX();
            }
        } else {
            minX = blockPos.getX() - range;
            minY = blockPos.getY() - range;
            minZ = blockPos.getZ() - range;
            maxX = blockPos.getX() + range;
            maxY = blockPos.getY() + range;
            maxZ = blockPos.getZ() + range;
        }

        BlockPos minChunkPos = new BlockPos(minX, minY, minZ);
        BlockPos maxChunkPos = new BlockPos(maxX, maxY, maxZ);
        Iterable<BlockPos> allBlockPos = BlockPos.getAllInBox(minChunkPos, maxChunkPos);
        return getBlockPosToBeBroken(allBlockPos, originState, world);
    }

    @Nonnull
    private Set<BlockPos> getBlockPosToBeBroken(@Nonnull Iterable<BlockPos> allBlockPos, IBlockState blockState, World world) {
        Set<BlockPos> beBrokenBlockSet = Sets.newHashSet();
        for (BlockPos blockPos : allBlockPos) {
            IBlockState state = world.getBlockState(blockPos);
            if (isSimilarBlock(blockState, state)) {
                beBrokenBlockSet.add(blockPos);
            }
        }
        return beBrokenBlockSet;
    }

    /*@Deprecated
    protected void searchAndDestroyBlock(World world, BlockPos blockPos, @Nonnull EnumFacing side, IBlockState state, ItemStack var6, EntityPlayer var7, int range) {
        ArrayList<BlockPos> var8 = new ArrayList<>();
        var8.add(blockPos);
        int minX, minY, minZ, maxX, maxY, maxZ;

        if (!this.doChainDestruction(state)) {
            minX = blockPos.getX() - range;
            minY = blockPos.getY() - range;
            minZ = blockPos.getZ() - range;
            maxX = blockPos.getX() + range;
            maxY = blockPos.getY() + range;
            maxZ = blockPos.getZ() + range;

            if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
                minY = blockPos.getY();
                maxY = blockPos.getY();
            } else {
                minY += range - AdvancedTools.digUnder;
                maxY += range - AdvancedTools.digUnder;
            }

            if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH) {
                minZ = blockPos.getZ();
                maxZ = blockPos.getZ();
            }

            if (side == EnumFacing.WEST || side == EnumFacing.EAST) {
                minX = blockPos.getX();
                maxX = blockPos.getX();
            }
        } else {
            minX = blockPos.getX() - this.cDestroyRange;
            minY = blockPos.getY() - this.cDestroyRange;
            minZ = blockPos.getZ() - this.cDestroyRange;
            maxX = blockPos.getX() + this.cDestroyRange;
            maxY = blockPos.getY() + this.cDestroyRange;
            maxZ = blockPos.getZ() + this.cDestroyRange;
        }

        BlockPos minChunkPos = new BlockPos(minX, minY, minZ);
        BlockPos maxChunkPos = new BlockPos(maxX, maxY, maxZ);

        for (int var17 = 0; var17 < this.safetyCount; ++var17) {
            ArrayList<BlockPos> var18 = new ArrayList<>();

            for (BlockPos chunkPosition : var8) {
                var18.addAll(this.searchAroundBlock(world, chunkPosition, minChunkPos, maxChunkPos, state, var6, var7));
            }

            if (var18.isEmpty()) {
                break;
            }

            var8.clear();
            var8.addAll(var18);
        }
    }*/

    /*@Deprecated
    protected ArrayList<BlockPos> searchAroundBlock(World world, BlockPos var1, BlockPos minChunkPos, BlockPos maxChunkPos, IBlockState var4, ItemStack var5, EntityPlayer var6) {
        ArrayList<BlockPos> var7 = new ArrayList<>();

        for (EnumFacing direction : EnumFacing.values()) {
            int directionIndex = direction.ordinal();
            BlockPos chunkPos;
            if (checkBlockPositionInRange(var1, minChunkPos, maxChunkPos, directionIndex)) {
                chunkPos = new BlockPos(var1).add(direction.getFrontOffsetX(), direction.getFrontOffsetY(), direction.getFrontOffsetZ());
                if (this.destroyBlock(world, chunkPos, var4, var5, var6)) {
                    var7.add(chunkPos);
                }
            }
        }
        return var7;
    }*/

    /*@Deprecated
    public boolean checkBlockPositionInRange(BlockPos check, BlockPos min, BlockPos max, int index) {
        switch (index) {
            case 0:
                return check.getY() > min.getY();
            case 1:
                return check.getY() < max.getY();
            case 2:
                return check.getZ() > min.getZ();
            case 3:
                return check.getZ() < max.getZ();
            case 4:
                return check.getX() > min.getX();
            case 5:
                return check.getX() < max.getX();
            default:
                return false;
        }
    }*/

/*    @Deprecated
    protected boolean destroyBlock(World world, BlockPos var1, IBlockState state, ItemStack var3, EntityPlayer var4) {

        Block var5 = world.getBlockState(var1).getBlock();
        boolean bool = var5 != Blocks.AIR;
        if (REDSTONE_SET.contains(state.getBlock())) {
            bool &= REDSTONE_SET.contains(var5);
        } else if (DIRT_SET.contains(state.getBlock())) {
            bool &= DIRT_SET.contains(var5);
        } else {
            bool &= state.getBlock() == var5;
        }

        return bool && this.checkAndDestroy(world, var1, state, var3, var4);
    }*/

    private boolean checkAndDestroy(World world, BlockPos blockPos, IBlockState originalState, ItemStack tool, EntityPlayer breaker) {
        originalState.getBlock().onBlockHarvested(world, blockPos, originalState, breaker);
        BlockPos breakerPos = new BlockPos(breaker.posX, breaker.posY, breaker.posZ);
        if (originalState.getBlock().removedByPlayer(originalState, world, blockPos, breaker, true)) {
            originalState.getBlock().onBlockDestroyedByPlayer(world, blockPos, originalState);
            if (AdvancedTools.dropGather) {
                originalState.getBlock().harvestBlock(world, breaker, breakerPos, originalState, null, tool);
            } else {
                originalState.getBlock().harvestBlock(world, breaker, blockPos, originalState, null, tool);
            }

            if (!isSilkTouch(tool)) {
                int exp = originalState.getBlock().getExpDrop(originalState, world, blockPos,
                        EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool));
                originalState.getBlock().dropXpOnBlockBreak(world, breakerPos, exp);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, tool) <= 0) {
                tool.damageItem(1, breaker);
            }

            return true;
        }
        return false;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            EntityPlayerMP player = (EntityPlayerMP) playerIn;
            int range = this.setRange(itemStack, playerIn);
            String chat;
            if (range == 0) {
                chat = this.BaseName + " will harvest only one.";
            } else {
                chat = this.BaseName + "\'s range was changed to " + (range * 2 + 1) + "x" + (range * 2 + 1);
            }
            player.sendMessage(new TextComponentString(chat));
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    private int setRange(ItemStack itemStack, EntityPlayer entityPlayer) {
        int range = getRange(itemStack);
        int toolMaterialOrd = this.toolMaterial.ordinal();
        if (!entityPlayer.isSneaking()) {
            range = (range + 1) % (this.rangeArray[toolMaterialOrd] + 1);
        } else {
            range = (this.rangeArray[toolMaterialOrd] + 1 + range - 1) % (this.rangeArray[toolMaterialOrd] + 1);
        }
        itemStack.getTagCompound().setInteger("range", range);
        return range;
    }

    private int getRange(ItemStack item) {
        int range;
        NBTTagCompound nbt;
        if (!item.hasTagCompound()) {
            nbt = new NBTTagCompound();
            item.setTagCompound(nbt);
        }
        nbt = item.getTagCompound();
        if (nbt.hasKey("range")) {
            range = item.getTagCompound().getInteger("range");
        } else {
            range = AdvancedTools.UGTools_DestroyRangeLV;
            nbt.setInteger("range", range);
        }
        return range;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer playerIn, List<String > tooltip, boolean advanced) {
        super.addInformation(itemStack, playerIn, tooltip, advanced);
        int range = getRange(itemStack);
        if (range == 0) {
            tooltip.add("Range: Only one");
        } else {
            tooltip.add("Range: " + (range * 2 + 1) + "x" + (range * 2 + 1));
        }
    }

    boolean checkArrays(Block block, String[] blockList) {
        String uIdName = block.getRegistryName().toString();
        return Arrays.asList(blockList).contains(uIdName);
    }

    private boolean isSilkTouch(ItemStack itemStack) {
        return itemStack.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) > 0;
    }

    public void setSide(ItemStack itemStack, EnumFacing side) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        itemStack.getTagCompound().setInteger(KEY_SIDE, side.getIndex());
    }

    private EnumFacing getSide(ItemStack itemStack) {
        EnumFacing facing = EnumFacing.DOWN;
        NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
        if (nbtTagCompound != null && nbtTagCompound.hasKey(KEY_SIDE, Constants.NBT.TAG_INT)) {
            int side = nbtTagCompound.getInteger(KEY_SIDE);
            facing = EnumFacing.getFront(side);
        }
        return facing;
    }

    private boolean isConnected(BlockPos listedBlockPos, BlockPos checkedBlockPos) {
        return this.getConnectedDistance() >= listedBlockPos.distanceSq(checkedBlockPos);
    }

    private boolean isSimilarBlock(IBlockState originState, IBlockState checkState) {
        Block block = checkState.getBlock();
        boolean bool = block != Blocks.AIR;
        if (REDSTONE_SET.contains(originState.getBlock())) {
            bool &= REDSTONE_SET.contains(block);
        } else if (DIRT_SET.contains(originState.getBlock())) {
            bool &= DIRT_SET.contains(block);
        } else {
            bool &= originState.getBlock() == block;
            if (!ignoreMeta()) {
                bool &= originState.getBlock().getMetaFromState(originState) == checkState.getBlock().getMetaFromState(checkState);
            }
        }

        return bool;
    }
}
