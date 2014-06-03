package Nanashi.AdvancedTools.client;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_ThrowingKnife;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderThrowingKnife extends Render
{
    private static final ResourceLocation tex = new ResourceLocation(AdvancedTools.textureassets,"textures/items/knife.png");
	public void renderKnife(Entity_ThrowingKnife var1, double var2, double var4, double var6, float var8, float var9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2, (float)var4, (float)var6);
        GL11.glRotatef(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9 - 90.0F, 0.0F, 1.0F, 0.0F);

        if (var1.isPoison())
        {
            GL11.glRotatef(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9, 0.0F, 0.0F, 1.0F);
        }
        else
        {
            GL11.glRotatef(var1.exRotate, 0.0F, 0.0F, 1.0F);
        }
        this.bindEntityTexture(var1);
        Tessellator var10 = Tessellator.instance;
        byte var11 = 0;
        float var12 = 0.0F;
        float var13 = 0.5F;
        float var14 = (float)(0 + var11 * 10) / 32.0F;
        float var15 = (float)(7 + var11 * 10) / 32.0F;
        float var16 = 0.0F;
        float var17 = (float)(8 + var11 * 10) / 32.0F;
        float var18 = (float)(7 + var11 * 10) / 32.0F;
        float var19 = (float)(15 + var11 * 10) / 32.0F;
        float var20 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(var20, var20, var20);
        GL11.glTranslatef(2.5F, 0.0F, 0.0F);
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-3.0D, -1.5D, -1.0D, (double)var16, (double)var18);
        var10.addVertexWithUV(-3.0D, -1.5D, 1.0D, (double)var17, (double)var18);
        var10.addVertexWithUV(-3.0D, 1.5D, 1.0D, (double)var17, (double)var19);
        var10.addVertexWithUV(-3.0D, 1.5D, -1.0D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, var20, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-3.0D, -1.5D, -1.0D, (double)var16, (double)var18);
        var10.addVertexWithUV(-2.0D, -1.5D, -1.0D, (double)var17, (double)var18);
        var10.addVertexWithUV(-2.0D, -1.5D, 1.0D, (double)var17, (double)var19);
        var10.addVertexWithUV(-3.0D, -1.5D, 1.0D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, -var20, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-3.0D, 1.5D, 1.0D, (double)var16, (double)var18);
        var10.addVertexWithUV(-2.0D, 1.5D, 1.0D, (double)var17, (double)var18);
        var10.addVertexWithUV(-2.0D, 1.5D, -1.0D, (double)var17, (double)var19);
        var10.addVertexWithUV(-3.0D, 1.5D, -1.0D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, 0.0F, -var20);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-3.0D, 1.5D, -1.0D, (double)var16, (double)var18);
        var10.addVertexWithUV(-2.0D, 1.5D, -1.0D, (double)var17, (double)var18);
        var10.addVertexWithUV(-2.0D, -1.5D, -1.0D, (double)var17, (double)var19);
        var10.addVertexWithUV(-3.0D, -1.5D, -1.0D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, 0.0F, var20);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-3.0D, -1.5D, 1.0D, (double)var16, (double)var18);
        var10.addVertexWithUV(-2.0D, -1.5D, 1.0D, (double)var17, (double)var18);
        var10.addVertexWithUV(-2.0D, 1.5D, 1.0D, (double)var17, (double)var19);
        var10.addVertexWithUV(-3.0D, 1.5D, 1.0D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(-var20, 0.0F, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-2.0D, 1.5D, -1.0D, (double)var16, (double)var18);
        var10.addVertexWithUV(-2.0D, 1.5D, 1.0D, (double)var17, (double)var18);
        var10.addVertexWithUV(-2.0D, -1.5D, 1.0D, (double)var17, (double)var19);
        var10.addVertexWithUV(-2.0D, -1.5D, -1.0D, (double)var16, (double)var19);
        var10.draw();
        var18 += 0.25F;
        var19 += 0.25F;
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-5.0D, -0.5D, -0.5D, (double)var16, (double)var18);
        var10.addVertexWithUV(-5.0D, -0.5D, 0.5D, (double)var17, (double)var18);
        var10.addVertexWithUV(-5.0D, 0.5D, 0.5D, (double)var17, (double)var19);
        var10.addVertexWithUV(-5.0D, 0.5D, -0.5D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, var20, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-5.0D, -0.5D, -0.5D, (double)var16, (double)var18);
        var10.addVertexWithUV(-3.0D, -0.5D, -0.5D, (double)var17, (double)var18);
        var10.addVertexWithUV(-3.0D, -0.5D, 0.5D, (double)var17, (double)var19);
        var10.addVertexWithUV(-5.0D, -0.5D, 0.5D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, -var20, 0.0F);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-5.0D, 0.5D, 0.5D, (double)var16, (double)var18);
        var10.addVertexWithUV(-3.0D, 0.5D, 0.5D, (double)var17, (double)var18);
        var10.addVertexWithUV(-3.0D, 0.5D, -0.5D, (double)var17, (double)var19);
        var10.addVertexWithUV(-5.0D, 0.5D, -0.5D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, 0.0F, -var20);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-5.0D, 0.5D, -0.5D, (double)var16, (double)var18);
        var10.addVertexWithUV(-3.0D, 0.5D, -0.5D, (double)var17, (double)var18);
        var10.addVertexWithUV(-3.0D, -0.5D, -0.5D, (double)var17, (double)var19);
        var10.addVertexWithUV(-5.0D, -0.5D, -0.5D, (double)var16, (double)var19);
        var10.draw();
        GL11.glNormal3f(0.0F, 0.0F, var20);
        var10.startDrawingQuads();
        var10.addVertexWithUV(-5.0D, -0.5D, 0.5D, (double)var16, (double)var18);
        var10.addVertexWithUV(-3.0D, -0.5D, 0.5D, (double)var17, (double)var18);
        var10.addVertexWithUV(-3.0D, 0.5D, 0.5D, (double)var17, (double)var19);
        var10.addVertexWithUV(-5.0D, 0.5D, 0.5D, (double)var16, (double)var19);
        var10.draw();

        for (int var21 = 0; var21 < 2; ++var21)
        {
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            var10.startDrawingQuads();
            var10.addVertexWithUV(-2.0D, -0.75D, 0.0D, (double)var12, (double)var14);
            var10.addVertexWithUV(3.0D, -0.75D, 0.0D, (double)var13, (double)var14);
            var10.addVertexWithUV(3.0D, 0.75D, 0.0D, (double)var13, (double)var15);
            var10.addVertexWithUV(-2.0D, 0.75D, 0.0D, (double)var12, (double)var15);
            var10.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.renderKnife((Entity_ThrowingKnife)var1, var2, var4, var6, var8, var9);
    }

	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return tex;
	}
}
