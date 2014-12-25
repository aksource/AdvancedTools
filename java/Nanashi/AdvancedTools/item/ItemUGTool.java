package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class ItemUGTool extends ItemTool
{
	public String BaseName;
	protected int cDestroyRange;
	private int[] rangeArray = new int[]{2,4,7,9,9};
	private int saftyCount;
	private int breakcount;
	public EnumFacing side;

	protected ItemUGTool(int var2, ToolMaterial var3, Set var4, float var5) {
		super(var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SafetyCounter;
		this.saftyCount = AdvancedTools.UGTools_SafetyCounter;
		this.setMaxDamage((int)(var5 * (float)this.getMaxDamage()));
	}

	public abstract boolean doChainDestruction(IBlockState state);

    public abstract boolean isProperTool(IBlockState state);

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		getRange(par1ItemStack);
	}

	@Override
	public Item setUnlocalizedName(String var1) {
		super.setUnlocalizedName(var1);

		if (this.BaseName == null){
			this.BaseName = var1;
		}

		return this;
	}

    @Override
	public boolean onBlockDestroyed(ItemStack item, World world, Block block, BlockPos blockPos, EntityLivingBase breaker) {
		if (!world.isRemote){
			item.damageItem(1, breaker);
			this.breakcount = 0;
            IBlockState state = world.getBlockState(blockPos);
			int range = getRange(item);
			if (range != 0 && breaker instanceof EntityPlayer && ForgeHooks.canHarvestBlock(block, (EntityPlayer) breaker, world, blockPos)) {
                this.destroyAroundBlock(item, world, state, blockPos, (EntityPlayer) breaker, range);
                item.damageItem(this.breakcount, breaker);
            }
            return true;
		}
		return false;
	}

	private boolean destroyAroundBlock(ItemStack var1, World world, IBlockState state, BlockPos blockPos, EntityPlayer var6, int range) {
		this.searchAndDestroyBlock(world, blockPos, side, state, var1, var6, range);
		return true;
	}

	protected void searchAndDestroyBlock(World world, BlockPos blockPos, EnumFacing side, IBlockState state, ItemStack var6, EntityPlayer var7, int range) {
		ArrayList<BlockPos> var8 = new ArrayList<>();
		var8.add(blockPos);
		int minX, minY, minZ, maxX, maxY, maxZ;

		if (!this.doChainDestruction(state)){
			minX = blockPos.getX() - range;
			minY = blockPos.getY() - range;
			minZ = blockPos.getZ() - range;
			maxX = blockPos.getX() + range;
			maxY = blockPos.getY() + range;
			maxZ = blockPos.getZ() + range;

			if (side == EnumFacing.DOWN || side == EnumFacing.UP){
				minY = blockPos.getY();
				maxY = blockPos.getY();
			}

			if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH){
				minZ = blockPos.getZ();
				maxZ = blockPos.getZ();
				minY += range - AdvancedTools.digUnder;
				maxY += range - AdvancedTools.digUnder;
			}

			if (side == EnumFacing.WEST || side == EnumFacing.EAST){
				minX = blockPos.getX();
				maxX = blockPos.getX();
				minY += range - AdvancedTools.digUnder;
				maxY += range - AdvancedTools.digUnder;
			}
		}else{
			minX = blockPos.getX() - this.cDestroyRange;
			minY = blockPos.getY() - this.cDestroyRange;
			minZ = blockPos.getZ() - this.cDestroyRange;
			maxX = blockPos.getX() + this.cDestroyRange;
			maxY = blockPos.getY() + this.cDestroyRange;
			maxZ = blockPos.getZ() + this.cDestroyRange;
		}

        BlockPos minChunkPos = new BlockPos(minX, minY, minZ);
        BlockPos maxChunkPos = new BlockPos(maxX, maxY, maxZ);

		for (int var17 = 0; var17 < this.saftyCount; ++var17){
			ArrayList<BlockPos> var18 = new ArrayList<>();

			for (BlockPos chunkPosition : var8) {
				var18.addAll(this.searchAroundBlock(world, chunkPosition, minChunkPos, maxChunkPos, state, var6, var7));
			}

			if (var18.isEmpty()){
				break;
			}

			var8.clear();
			var8.addAll(var18);
		}
	}

	protected ArrayList<BlockPos> searchAroundBlock(World world,BlockPos var1, BlockPos minChunkPos, BlockPos maxChunkPos, IBlockState var4, ItemStack var5, EntityPlayer var6) {
		ArrayList<BlockPos> var7 = new ArrayList<>();

        for (EnumFacing direction : EnumFacing.values()) {
            int directionIndex = direction.ordinal();
            BlockPos chunkpos;
            if (checkBlockPositionInRange(var1, minChunkPos, maxChunkPos, directionIndex)) {
                chunkpos = new BlockPos(var1).add(direction.getFrontOffsetX(), direction.getFrontOffsetY(), direction.getFrontOffsetZ());
                if (this.destroyBlock(world, chunkpos, var4, var5, var6)) {
                    var7.add(chunkpos);
                }
            }
        }
		return var7;
	}

    public boolean checkBlockPositionInRange(BlockPos check, BlockPos min, BlockPos max, int index) {
        switch (index) {
            case 0 : return check.getY() > min.getY();
            case 1 : return check.getY() < max.getY();
            case 2 : return check.getZ() > min.getZ();
            case 3 : return check.getZ() < max.getZ();
            case 4 : return check.getX() > min.getX();
            case 5 : return check.getX() < max.getX();
            default : return false;
        }
    }

	protected boolean destroyBlock(World world, BlockPos var1, IBlockState state, ItemStack var3, EntityPlayer var4) {
		Block var5 = world.getBlockState(var1).getBlock();

		if (var5 == Blocks.air){
			return false;
		}
        List<Block> rsList = Arrays.asList(Blocks.redstone_ore, Blocks.lit_redstone_ore);
        List<Block> dirtList = Arrays.asList(Blocks.dirt, Blocks.grass);

        if (rsList.contains(state.getBlock()) && !rsList.contains(var5)) {
            return false;
        }

        if (dirtList.contains(state.getBlock()) && !dirtList.contains(var5)) {
            return false;
        }

        if (!rsList.contains(state.getBlock()) && !dirtList.contains(state.getBlock()) && var5 != state.getBlock()) {
            return false;
        }

        return this.checkAndDestroy(world, var1, state, var3, var4);
	}

	private boolean checkAndDestroy(World world, BlockPos var1, IBlockState var2, ItemStack var3, EntityPlayer var4) {
//		int var5 = world.getBlockMetadata(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ);
        var2.getBlock().onBlockHarvested(world, var1, var2, var4);
		if (var2.getBlock().removedByPlayer(world, var1, var4, true)){
			var2.getBlock().onBlockDestroyedByPlayer(world, var1, var2);
			if(AdvancedTools.dropGather){
				var2.getBlock().harvestBlock(world, var4, new BlockPos(var4.posX, var4.posY, var4.posZ), var2, null);
			}else{
				var2.getBlock().harvestBlock(world, var4, var1, var2, null);
			}

            if (!isSilkTouch(var3)) {
                int exp = var2.getBlock().getExpDrop(world, var1, EnchantmentHelper.getFortuneModifier(var4));
                var2.getBlock().dropXpOnBlockBreak(world, new BlockPos(var4.posX, var4.posY, var4.posZ), exp);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var3) <= 0){
				this.breakcount++;
			}

			return true;
		}
        return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		if (!var2.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) var3;
			int range = this.setRange(var1, var3);
			String chat;
			if (range == 0){
				chat = this.BaseName + " will harvest only one.";
			}else{
				chat = this.BaseName + "\'s range was changed to " + (range * 2 + 1) + "x" + (range * 2 + 1);
			}
            player.addChatMessage(new ChatComponentText(chat));
		}
		return var1;
	}

	private int setRange(ItemStack var1, EntityPlayer var3) {
		int range = getRange(var1);
		int toolMaterialOrd = this.toolMaterial.ordinal();
		if(!var3.isSneaking()) {
			range = (range + 1) % (this.rangeArray[toolMaterialOrd] + 1);
		} else {
			range = (this.rangeArray[toolMaterialOrd] + 1 + range - 1) % (this.rangeArray[toolMaterialOrd] + 1);
		}
		var1.getTagCompound().setInteger("range", range);
		return range;
	}

	private int getRange(ItemStack item) {
		int range;
		NBTTagCompound nbt;
		if(!item.hasTagCompound()) {
			nbt = new NBTTagCompound();
			item.setTagCompound(nbt);
		}
		nbt = item.getTagCompound();
		if(nbt.hasKey("range")) {
			range = item.getTagCompound().getInteger("range");
		} else {
			range = AdvancedTools.UGTools_DestroyRangeLV;
			nbt.setInteger("range", range);
		}
		return range;
	}

	@SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtype"})
	public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean par4) {
		super.addInformation(item, player, par3List, par4);
		int range = getRange(item);
		if (range == 0){
			par3List.add("Range: Only one");
		}else{
			par3List.add("Range: " + (range * 2 + 1) + "x" + (range * 2 + 1));
		}
	}

	public boolean checkArrays(Block block, String[] blockList) {
		String uIdName = AdvancedTools.getUniqueStrings(block);
        return Arrays.asList(blockList).contains(uIdName);
	}

    public boolean isSilkTouch(ItemStack itemStack) {
        return itemStack.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) > 0;
    }
}
