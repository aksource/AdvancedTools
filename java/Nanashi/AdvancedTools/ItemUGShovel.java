package Nanashi.AdvancedTools;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemUGShovel extends ItemUGTool
{
    public static final Set<Block> blocksEffectiveAgainst = new HashSet<Block>();
	private ToolMaterial material;
	protected ItemUGShovel(ToolMaterial var2, float var3)
	{
		super(1, var2, blocksEffectiveAgainst, var3);
		this.material = var2;
	}

	protected ItemUGShovel(ToolMaterial var2)
	{
		super(1, var2, blocksEffectiveAgainst, 1.0F);
	}
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IIconRegister par1IconRegister)
//	{
//		if(this.getUnlocalizedName().equals("item.UpgradedWoodenShovel")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGWoodshovel");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedStoneShovel")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGStoneshovel");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedIronShovel")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGIronshovel");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedGoldenShovel")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGGoldshovel");
//		}else if(this.getUnlocalizedName().equals("item.UpgradedDiamondShovel")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "UGDiamondshovel");
//		}else if(this.getUnlocalizedName().equals("item.InfinityShovel")){
//	    	this.itemIcon = par1IconRegister.registerIcon(AdvancedTools.textureDomain + "Infinityshovel");
//		}
//	}
	@Override
	public boolean func_150897_b(Block var1)
	{
		return blocksEffectiveAgainst.contains(var1);
	}

	public boolean doChainDestraction(Block var1)
	{
		return checkArrays(var1, AdvancedTools.addBlockForShovel) && this.func_150897_b(var1);
	}
	static{
		blocksEffectiveAgainst.add(Blocks.grass);
		blocksEffectiveAgainst.add(Blocks.gravel);
		blocksEffectiveAgainst.add(Blocks.dirt);
		blocksEffectiveAgainst.add(Blocks.sand);
		blocksEffectiveAgainst.add(Blocks.snow);
		blocksEffectiveAgainst.add(Blocks.snow_layer);
		blocksEffectiveAgainst.add(Blocks.clay);
		blocksEffectiveAgainst.add(Blocks.farmland);
		blocksEffectiveAgainst.add(Blocks.soul_sand);
		blocksEffectiveAgainst.add(Blocks.mycelium);
	}
}
