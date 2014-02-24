package Nanashi.AdvancedTools.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class Model_IHIce extends ModelBase
{
	ModelRenderer base = new ModelRenderer(this, 0, 0);

	public Model_IHIce()
	{
		this.base.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
	}

	/**
	 * Sets the models various rotation angles.
	 */
	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7)
	{
//		this.setRotationAngles(var2, var3, var4, var5, var6, var7);
		this.base.render(var7);
	}
}
