package Nanashi.AdvancedTools.item;

import Nanashi.AdvancedTools.AdvancedTools;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;

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
	public int side;

	protected ItemUGTool(int var2, ToolMaterial var3, Set var4, float var5) {
		super(var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SafetyCounter;
		this.saftyCount = AdvancedTools.UGTools_SafetyCounter;
		this.setMaxDamage((int)(var5 * (float)this.getMaxDamage()));
	}

	public abstract boolean doChainDestruction(Block var1, int var2);

    public abstract boolean isProperTool(Block block, int meta);

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
	public boolean onBlockDestroyed(ItemStack item, World world, Block block, int x, int y, int z, EntityLivingBase breaker) {
		if (!world.isRemote){
			item.damageItem(1, breaker);
			this.breakcount = 0;
			int range = getRange(item);
            int meta = world.getBlockMetadata(x, y, z);
			if (range != 0 && breaker instanceof EntityPlayer && ForgeHooks.canHarvestBlock(block, (EntityPlayer) breaker, meta)) {
                this.destroyAroundBlock(item, world, block, meta, x, y, z, (EntityPlayer) breaker, range);
                item.damageItem(this.breakcount, breaker);
            }
            return true;
		}
		return false;
	}

	private boolean destroyAroundBlock(ItemStack var1, World world, Block block, int meta, int x, int y, int z, EntityPlayer var6, int range) {
		this.searchAndDestroyBlock(world,x, y, z, side, block, meta, var1, var6, range);
		return true;
	}

	protected void searchAndDestroyBlock(World world, int x, int y, int z, int side, Block block, int meta, ItemStack var6, EntityPlayer var7, int range) {
		ArrayList<ChunkPosition> var8 = new ArrayList<>();
		var8.add(new ChunkPosition(x, y, z));
		int minX, minY, minZ, maxX, maxY, maxZ;

		if (!this.doChainDestruction(block, meta)){
			minX = x - range;
			minY = y - range;
			minZ = z - range;
			maxX = x + range;
			maxY = y + range;
			maxZ = z + range;

			if (side == 0 || side == 1){
				minY = y;
				maxY = y;
			}

			if (side == 2 || side == 3){
				minZ = z;
				maxZ = z;
				minY += range - AdvancedTools.digUnder;
				maxY += range - AdvancedTools.digUnder;
			}

			if (side == 4 || side == 5){
				minX = x;
				maxX = x;
				minY += range - AdvancedTools.digUnder;
				maxY += range - AdvancedTools.digUnder;
			}
		}else{
			minX = x - this.cDestroyRange;
			minY = y - this.cDestroyRange;
			minZ = z - this.cDestroyRange;
			maxX = x + this.cDestroyRange;
			maxY = y + this.cDestroyRange;
			maxZ = z + this.cDestroyRange;
		}

		ChunkPosition minChunkPos = new ChunkPosition(minX, minY, minZ);
		ChunkPosition maxChunkPos = new ChunkPosition(maxX, maxY, maxZ);

		for (int var17 = 0; var17 < this.saftyCount; ++var17){
			ArrayList<ChunkPosition> var18 = new ArrayList<>();

			for (ChunkPosition chunkPosition : var8) {
				var18.addAll(this.searchAroundBlock(world, chunkPosition, minChunkPos, maxChunkPos, block, var6, var7));
			}

			if (var18.isEmpty()){
				break;
			}

			var8.clear();
			var8.addAll(var18);
		}
	}

	protected ArrayList<ChunkPosition> searchAroundBlock(World world,ChunkPosition var1, ChunkPosition minChunkPos, ChunkPosition maxChunkPos, Block var4, ItemStack var5, EntityPlayer var6) {
		ArrayList<ChunkPosition> var7 = new ArrayList<>();

        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            int directionIndex = direction.ordinal();
            ChunkPosition chunkpos;
            if (checkBlockPositionInRange(var1, minChunkPos, maxChunkPos, directionIndex)) {
                chunkpos = new ChunkPosition(var1.chunkPosX + direction.offsetX, var1.chunkPosY + direction.offsetY, var1.chunkPosZ + direction.offsetZ);
                if (this.destroyBlock(world, chunkpos, var4, var5, var6)) {
                    var7.add(chunkpos);
                }
            }
        }
		return var7;
	}

    public boolean checkBlockPositionInRange(ChunkPosition check, ChunkPosition min, ChunkPosition max, int index) {
        switch (index) {
            case 0 : return check.chunkPosY > min.chunkPosY;
            case 1 : return check.chunkPosY < max.chunkPosY;
            case 2 : return check.chunkPosZ > min.chunkPosZ;
            case 3 : return check.chunkPosZ < max.chunkPosZ;
            case 4 : return check.chunkPosX > min.chunkPosX;
            case 5 : return check.chunkPosX < max.chunkPosX;
            default : return false;
        }
    }

	protected boolean destroyBlock(World world, ChunkPosition var1, Block block, ItemStack var3, EntityPlayer var4) {
		Block var5 = world.getBlock(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ);

		if (var5 == Blocks.air){
			return false;
		}
        List<Block> rsList = Arrays.asList(Blocks.redstone_ore, Blocks.lit_redstone_ore);
        List<Block> dirtList = Arrays.asList(Blocks.dirt, Blocks.grass);

        if (rsList.contains(block) && !rsList.contains(var5)) {
            return false;
        }

        if (dirtList.contains(block) && !dirtList.contains(var5)) {
            return false;
        }

        if (!rsList.contains(block) && !dirtList.contains(block) && var5 != block) {
            return false;
        }

        return this.checkAndDestroy(world, var1, var5, var3, var4);
	}

	private boolean checkAndDestroy(World world, ChunkPosition var1, Block var2, ItemStack var3, EntityPlayer var4) {
		int var5 = world.getBlockMetadata(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ);
        var2.onBlockHarvested(world, var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ, var5, var4);
		if (var2.removedByPlayer(world, var4, var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ, true)){
			var2.onBlockDestroyedByPlayer(world, var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ, var5);
			if(AdvancedTools.dropGather){
				var2.harvestBlock(world, var4, MathHelper.ceiling_double_int( var4.posX), MathHelper.ceiling_double_int( var4.posY), MathHelper.ceiling_double_int( var4.posZ), var5);
			}else{
				var2.harvestBlock(world, var4, var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ, var5);
			}
            int exp = var2.getExpDrop(world, var5, EnchantmentHelper.getFortuneModifier(var4));
            var2.dropXpOnBlockBreak(world, MathHelper.ceiling_double_int(var4.posX), MathHelper.ceiling_double_int(var4.posY), MathHelper.ceiling_double_int(var4.posZ), exp);

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
}
