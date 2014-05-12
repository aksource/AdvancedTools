package Nanashi.AdvancedTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUGTool extends ItemTool
{
	public String BaseName;
	public boolean canDestroyABlock = false;
	protected int cDestroyRange;
	private int[] rangeArray = new int[]{2,4,7,9,9};
	private int saftyCount;
	private int breakcount;
	public int side;

	protected ItemUGTool(int var2, ToolMaterial var3, Set var4)
	{
		super(var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SaftyCounter;
		this.saftyCount = AdvancedTools.UGTools_SaftyCounter;
	}

	protected ItemUGTool(int var2, ToolMaterial var3, Set var4, float var5)
	{
		super(var2, var3, var4);
		this.cDestroyRange = AdvancedTools.UGTools_SaftyCounter;
		this.saftyCount = AdvancedTools.UGTools_SaftyCounter;
		this.setMaxDamage((int)(var5 * (float)this.getMaxDamage()));
	}

	public boolean doChainDestraction(Block var1)
	{
		return false;
	}
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		getRange(par1ItemStack);
	}
	@Override
	public Item setUnlocalizedName(String var1)
	{
		super.setUnlocalizedName(var1);

		if (this.BaseName == null){
			this.BaseName = var1;
		}

		return this;
	}
	@Override
	public boolean onBlockDestroyed(ItemStack item, World world, Block blockid, int x, int y, int z, EntityLivingBase breaker)
	{
		if (!world.isRemote){
			item.damageItem(1, breaker);
			this.breakcount = 0;
			int range = getRange(item);
			if (this.canHarvestBlock(blockid, item) && range != 0 && breaker instanceof EntityPlayer){
				this.destroyAroundBlock(item, world, blockid, x, y, z, (EntityPlayer)breaker, range);
				item.damageItem(this.breakcount, breaker);
				return true;
			}else{
				return true;
			}
		}
		else return false;
	}
	private boolean destroyAroundBlock(ItemStack var1, World world, Block blockid, int x, int y, int z, EntityPlayer var6, int range)
	{
		this.searchAndDestroyBlock(world,x, y, z, side, blockid, var1, var6, range);
		return true;
	}
	protected void searchAndDestroyBlock(World world, int x, int y, int z, int side, Block blockid, ItemStack var6, EntityPlayer var7, int range)
	{
		ArrayList var8 = new ArrayList();
		var8.add(new ChunkPosition(x, y, z));
		int minX;
		int minY;
		int minZ;
		int maxX;
		int maxY;
		int maxZ;

		if (!this.doChainDestraction(blockid)){
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
				minY += range - 1;
				maxY += range - 1;
			}

			if (side == 4 || side == 5){
				minX = x;
				maxX = x;
				minY += range - 1;
				maxY += range - 1;
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
			ArrayList var18 = new ArrayList();

			for (int var19 = 0; var19 < var8.size(); ++var19){
				var18.addAll(this.searchAroundBlock(world,(ChunkPosition)var8.get(var19), minChunkPos, maxChunkPos, blockid, var6, var7));
			}

			if (var18.isEmpty()){
				break;
			}

			var8.clear();
			var8.addAll(var18);
		}
	}

	protected ArrayList searchAroundBlock(World world,ChunkPosition var1, ChunkPosition minChunkPos, ChunkPosition maxChunkPos, Block var4, ItemStack var5, EntityPlayer var6)
	{
		ArrayList var7 = new ArrayList();
		ChunkPosition[] var8 = new ChunkPosition[6];

		if (var1.chunkPosY > minChunkPos.chunkPosY){
			var8[0] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY - 1, var1.chunkPosZ);
		}

		if (var1.chunkPosY < maxChunkPos.chunkPosY){
			var8[1] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY + 1, var1.chunkPosZ);
		}

		if (var1.chunkPosZ > minChunkPos.chunkPosZ){
			var8[2] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ - 1);
		}

		if (var1.chunkPosZ < maxChunkPos.chunkPosZ){
			var8[3] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ + 1);
		}

		if (var1.chunkPosX > minChunkPos.chunkPosX){
			var8[4] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY, var1.chunkPosZ);
		}

		if (var1.chunkPosX < maxChunkPos.chunkPosX){
			var8[5] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY, var1.chunkPosZ);
		}

		for (int var9 = 0; var9 < 6; ++var9){
			if (var8[var9] != null && this.destroyBlock(world,var8[var9], var4, var5, var6)){
				var7.add(var8[var9]);
			}
		}

		return var7;
	}

	private boolean destroyChainedBlock()
	{
		return true;
	}

	protected boolean destroyBlock(World world, ChunkPosition var1, Block blockid, ItemStack var3, EntityPlayer var4)
	{
		Block var5 = world.getBlock(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ);

		if (var5 == Blocks.air){
			return false;
		}else{
			/*if (blockid == -1){
				if (!this.canHarvestBlock(Block.blocksList[var5])){
					return false;
				}
			}else */if (blockid != Blocks.redstone_ore && blockid != Blocks.lit_redstone_ore){
				if (blockid != Blocks.dirt && blockid != Blocks.grass){
					if (var5 != blockid){
						return false;
					}
				}else if (var5 != Blocks.dirt && var5 != Blocks.grass){
					return false;
				}
			}else if (var5 != Blocks.redstone_ore && var5 != Blocks.lit_redstone_ore){
				return false;
			}

			return this.checkandDestroy(world,var1, var5, var3, var4);
		}
	}

	private boolean checkandDestroy(World world,ChunkPosition var1, Block var2, ItemStack var3, EntityPlayer var4)
	{
		int var5 = world.getBlockMetadata(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ);
		boolean var6 = world.setBlockToAir(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ);

		if (var6){
			var2.onBlockDestroyedByPlayer(world, var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ, var5);
			if(AdvancedTools.dropGather){
				var2.harvestBlock(world, var4, MathHelper.ceiling_double_int( var4.posX), MathHelper.ceiling_double_int( var4.posY), MathHelper.ceiling_double_int( var4.posZ), var5);
			}else{
				var2.harvestBlock(world, var4, var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ, var5);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var3) <= 0){
				this.breakcount++;
			}

			return true;
		}else{
			return false;
		}
	}
	@Override
	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
	{
		if (!var2.isRemote) {
			EntityPlayerMP player = (EntityPlayerMP) var3;
			int range = this.setRange(var1, var3);
			String chat;
			if (range == 0){
				chat = this.BaseName + " will harvest only one.";
				player.addChatMessage(new ChatComponentTranslation(chat, new Object[0]));
			}else{
				chat = this.BaseName + "\'s range was changed to " + (range * 2 + 1) + "x" + (range * 2 + 1);
				player.addChatMessage(new ChatComponentTranslation(chat, new Object[0]));
			}
		}
		return var1;
	}

	private int setRange(ItemStack var1, EntityPlayer var3)
	{
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
	private int getRange(ItemStack item)
	{
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
	public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean par4)
	{
		super.addInformation(item, player, par3List, par4);
		int range = getRange(item);
		if (range == 0){
			par3List.add("Range: Only one");
		}else{
			par3List.add("Range: " + (range * 2 + 1) + "x" + (range * 2 + 1));
		}
	}
	public boolean checkArrays(Block block, String[] blocklist)
	{
		String uIdName = AdvancedTools.getUniqueStrings(block);
		for(int i = 0;i<blocklist.length;i++){
			if(blocklist[i].equals(uIdName)){
				return true;
			}
		}
		return false;
	}
}
