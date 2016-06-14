package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.entity.Entity_IHFrozenMob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class Render_UQFreezer extends Render
{
    @Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		Entity_IHFrozenMob var10 = (Entity_IHFrozenMob)var1;
		this.bindEntityTexture(var10);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GL11.glRotatef(180.0F - var8, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, var10.height * 0.75F, 0.0F);
		GL11.glScalef(var10.width * 1.5F, var10.height * 1.5F, var10.width * 1.5F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		(new RenderBlocks()).renderBlockAsItem(Blocks.glass, 0, var1.getBrightness(var9));
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
