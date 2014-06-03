package Nanashi.AdvancedTools.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_GoldCreeper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGCreeper extends RenderLiving
{
	/** The creeper model. */
	private ModelBase creeperModel = new ModelCreeper(2.0F);
	private ResourceLocation tex = new ResourceLocation(AdvancedTools.textureassets, "textures/mob/gcreeper.png");
	private ResourceLocation power = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	public RenderGCreeper()
	{
		super(new ModelCreeper(), 0.5F);
	}

	/**
	 * Updates creeper scale in prerender callback
	 */
	protected void updateCreeperScale(Entity_GoldCreeper par1EntityCreeper, float par2)
	{
		float var4 = par1EntityCreeper.getCreeperFlashIntensity(par2);
		float var5 = 1.0F + MathHelper.sin(var4 * 100.0F) * var4 * 0.01F;

		if (var4 < 0.0F)
		{
			var4 = 0.0F;
		}

		if (var4 > 1.0F)
		{
			var4 = 1.0F;
		}

		var4 *= var4;
		var4 *= var4;
		float var6 = (1.0F + var4 * 0.4F) * var5;
		float var7 = (1.0F + var4 * 0.1F) / var5;
		GL11.glScalef(var6, var7, var6);
	}

	/**
	 * Updates color multiplier based on creeper state called by getColorMultiplier
	 */
	protected int updateCreeperColorMultiplier(Entity_GoldCreeper par1EntityCreeper, float par2, float par3)
	{
		float var5 = par1EntityCreeper.getCreeperFlashIntensity(par3);

		if ((int)(var5 * 10.0F) % 2 == 0)
		{
			return 0;
		}
		else
		{
			int var6 = (int)(var5 * 0.2F * 255.0F);

			if (var6 < 0)
			{
				var6 = 0;
			}

			if (var6 > 255)
			{
				var6 = 255;
			}

			short var7 = 255;
			short var8 = 255;
			short var9 = 255;
			return var6 << 24 | var7 << 16 | var8 << 8 | var9;
		}
	}

	/**
	 * A method used to render a creeper's powered form as a pass model.
	 */
	protected int renderCreeperPassModel(Entity_GoldCreeper par1EntityCreeper, int par2, float par3)
	{
		if (par1EntityCreeper.getPowered())
		{
			if (par1EntityCreeper.isInvisible())
			{
				GL11.glDepthMask(false);
			}
			else
			{
				GL11.glDepthMask(true);
			}

			if (par2 == 1)
			{
				float var4 = (float)par1EntityCreeper.ticksExisted + par3;
				bindTexture(power);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float var5 = var4 * 0.01F;
				float var6 = var4 * 0.01F;
				GL11.glTranslatef(var5, var6, 0.0F);
				this.setRenderPassModel(this.creeperModel);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float var7 = 0.5F;
				GL11.glColor4f(var7, var7, var7, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				return 1;
			}

			if (par2 == 2)
			{
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		return -1;
	}

	protected int func_77061_b(Entity_GoldCreeper par1EntityCreeper, int par2, float par3)
	{
		return -1;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
	{
		this.updateCreeperScale((Entity_GoldCreeper)par1EntityLiving, par2);
	}

	/**
	 * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
	 */
	protected int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3)
	{
		return this.updateCreeperColorMultiplier((Entity_GoldCreeper)par1EntityLiving, par2, par3);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.renderCreeperPassModel((Entity_GoldCreeper)par1EntityLiving, par2, par3);
	}

	protected int inheritRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.func_77061_b((Entity_GoldCreeper)par1EntityLiving, par2, par3);
	}

	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return tex;
	}
}
