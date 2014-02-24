package Nanashi.AdvancedTools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ItemUGAxe extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<Block>();
	private ToolMaterial material;
	protected ItemUGAxe(ToolMaterial var2, float var3)
	{
		super(3, var2, blocksEffectiveAgainst, var3);
		this.material = var2;
	}

	protected ItemUGAxe(ToolMaterial var2)
	{
		super(3, var2, blocksEffectiveAgainst, 1.0F);
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//		if(this.getUnlocalizedName().equals("item.UpgradedWoodenAxe")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGWoodaxe");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedStoneAxe")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGStoneaxe");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedIronAxe")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGIronaxe");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedGoldenAxe")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGGoldaxe");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedDiamondAxe")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGDiamondaxe");
//		}else if(this.getUnlocalizedName().equals("item.InfinityAxe")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Infinityaxe");
//		}
//	}
	@Override
	public boolean func_150897_b(Block var1)
	{
		return var1.getMaterial() == Material.wood;
	}
	@Override
    public float getDigSpeed(ItemStack par1ItemStack, Block par2Block, int meta)
    {
        return par2Block != null && (par2Block.getMaterial() == Material.wood || par2Block.getMaterial() == Material.plants || par2Block.getMaterial() == Material.vine) ? this.efficiencyOnProperMaterial : super.getDigSpeed(par1ItemStack, par2Block, meta);
    }
	public boolean doChainDestraction(Block var1)
	{
		return checkArrays(var1, AdvancedTools.addBlockForAxe) && this.func_150897_b(var1);
	}
	@Override
	protected ArrayList searchAroundBlock(World world, ChunkPosition var1, ChunkPosition var2, ChunkPosition var3, Block var4, ItemStack var5, EntityPlayer var6)
	{
		ArrayList var7 = new ArrayList();
		ChunkPosition[] var8 = new ChunkPosition[17];

		if (var1.chunkPosY < var3.chunkPosY){
			var8[0] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY + 1, var1.chunkPosZ);
		}

		if (var1.chunkPosZ > var2.chunkPosZ){
			var8[1] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ - 1);
		}

		if (var1.chunkPosZ < var3.chunkPosZ){
			var8[2] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY, var1.chunkPosZ + 1);
		}

		if (var1.chunkPosX > var2.chunkPosX){
			var8[3] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY, var1.chunkPosZ);
		}

		if (var1.chunkPosX < var3.chunkPosX){
			var8[4] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY, var1.chunkPosZ);
		}

		if (checkArrays(var4, AdvancedTools.addBlockForAxe)){
			if (var1.chunkPosZ > var2.chunkPosZ && var1.chunkPosX > var2.chunkPosX){
				var8[5] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY, var1.chunkPosZ - 1);
			}

			if (var1.chunkPosZ < var3.chunkPosZ && var1.chunkPosX > var2.chunkPosX){
				var8[6] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY, var1.chunkPosZ + 1);
			}

			if (var1.chunkPosZ > var2.chunkPosZ && var1.chunkPosX < var3.chunkPosX){
				var8[7] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY, var1.chunkPosZ - 1);
			}

			if (var1.chunkPosZ < var3.chunkPosZ && var1.chunkPosX < var3.chunkPosX){
				var8[8] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY, var1.chunkPosZ + 1);
			}

			if (var1.chunkPosY < var3.chunkPosY){
				if (var1.chunkPosZ > var2.chunkPosZ){
					var8[13] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY + 1, var1.chunkPosZ - 1);
				}

				if (var1.chunkPosZ < var3.chunkPosZ){
					var8[14] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY + 1, var1.chunkPosZ + 1);
				}

				if (var1.chunkPosX > var2.chunkPosX){
					var8[15] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY + 1, var1.chunkPosZ);
				}

				if (var1.chunkPosX < var3.chunkPosX){
					var8[16] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY + 1, var1.chunkPosZ);
				}

				if (var1.chunkPosZ > var2.chunkPosZ && var1.chunkPosX > var2.chunkPosX){
					var8[9] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY + 1, var1.chunkPosZ - 1);
				}

				if (var1.chunkPosZ < var3.chunkPosZ && var1.chunkPosX > var2.chunkPosX){
					var8[10] = new ChunkPosition(var1.chunkPosX - 1, var1.chunkPosY + 1, var1.chunkPosZ + 1);
				}

				if (var1.chunkPosZ > var2.chunkPosZ && var1.chunkPosX < var3.chunkPosX){
					var8[11] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY + 1, var1.chunkPosZ - 1);
				}

				if (var1.chunkPosZ < var3.chunkPosZ && var1.chunkPosX < var3.chunkPosX){
					var8[12] = new ChunkPosition(var1.chunkPosX + 1, var1.chunkPosY + 1, var1.chunkPosZ + 1);
				}
			}
		}else if (var1.chunkPosY > var2.chunkPosY){
			var8[5] = new ChunkPosition(var1.chunkPosX, var1.chunkPosY - 1, var1.chunkPosZ);
		}

		for (int var9 = 0; var9 < 17; ++var9){
			if (var8[var9] != null && this.destroyBlock(world,var8[var9], var4, var5, var6)){
				var7.add(var8[var9]);
			}
		}

		return var7;
	}
	static{
		blocksEffectiveAgainst.add(Blocks.planks);
		blocksEffectiveAgainst.add(Blocks.bookshelf);
		blocksEffectiveAgainst.add(Blocks.log);
		blocksEffectiveAgainst.add(Blocks.log2);
		blocksEffectiveAgainst.add(Blocks.chest);
		blocksEffectiveAgainst.add(Blocks.double_wooden_slab);
		blocksEffectiveAgainst.add(Blocks.wooden_slab);
		blocksEffectiveAgainst.add(Blocks.pumpkin);
		blocksEffectiveAgainst.add(Blocks.lit_pumpkin);
	}
}
