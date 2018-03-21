package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_ThrowingKnife;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderThrowingKnife extends Render<Entity_ThrowingKnife> {
    private static final ResourceLocation tex = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/items/knife.png");

    protected RenderThrowingKnife(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    public void renderKnife(Entity_ThrowingKnife entity, double x, double y, double z, float var8, float partialTick) {
        this.bindEntityTexture(entity);
        GlStateManager.pushMatrix();//GL11.glPushMatrix();
        GlStateManager.translate(x, y, z);//GL11.glTranslated(x, y, z);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTick - 90.0F, 0.0F, 1.0F, 0.0F);//GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTick - 90.0F, 0.0F, 1.0F, 0.0F);

        if (entity.isPoison()) {
            GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTick, 0.0F, 0.0F, 1.0F);//GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTick, 0.0F, 0.0F, 1.0F);
        } else {
            GlStateManager.rotate(entity.exRotate, 0.0F, 0.0F, 1.0F);//GL11.glRotatef(entity.exRotate, 0.0F, 0.0F, 1.0F);
        }
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        byte var11 = 0;
        float var12 = 0.0F;
        float var13 = 0.5F;
        float var14 = (float) (0 + var11 * 10) / 32.0F;
        float var15 = (float) (7 + var11 * 10) / 32.0F;
        float var16 = 0.0F;
        float var17 = (float) (8 + var11 * 10) / 32.0F;
        float var18 = (float) (7 + var11 * 10) / 32.0F;
        float var19 = (float) (15 + var11 * 10) / 32.0F;
        float var20 = 0.05625F;
        GlStateManager.enableRescaleNormal();//GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GlStateManager.scale(var20, var20, var20);//GL11.glScalef(var20, var20, var20);
        GlStateManager.translate(2.5F, 0.0F, 0.0F);//GL11.glTranslatef(2.5F, 0.0F, 0.0F);
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-3.0D, -1.5D, -1.0D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, -1.5D, 1.0D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, 1.5D, 1.0D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-3.0D, 1.5D, -1.0D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, var20, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-3.0D, -1.5D, -1.0D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, -1.5D, -1.0D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, -1.5D, 1.0D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-3.0D, -1.5D, 1.0D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, -var20, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-3.0D, 1.5D, 1.0D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, 1.5D, 1.0D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, 1.5D, -1.0D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-3.0D, 1.5D, -1.0D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, 0.0F, -var20);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-3.0D, 1.5D, -1.0D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, 1.5D, -1.0D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, -1.5D, -1.0D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-3.0D, -1.5D, -1.0D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, 0.0F, var20);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-3.0D, -1.5D, 1.0D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, -1.5D, 1.0D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, 1.5D, 1.0D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-3.0D, 1.5D, 1.0D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(-var20, 0.0F, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-2.0D, 1.5D, -1.0D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, 1.5D, 1.0D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-2.0D, -1.5D, 1.0D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-2.0D, -1.5D, -1.0D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        var18 += 0.25F;
        var19 += 0.25F;
        GL11.glNormal3f(var20, 0.0F, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-5.0D, -0.5D, -0.5D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-5.0D, -0.5D, 0.5D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-5.0D, 0.5D, 0.5D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-5.0D, 0.5D, -0.5D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, var20, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-5.0D, -0.5D, -0.5D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, -0.5D, -0.5D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, -0.5D, 0.5D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-5.0D, -0.5D, 0.5D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, -var20, 0.0F);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-5.0D, 0.5D, 0.5D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, 0.5D, 0.5D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, 0.5D, -0.5D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-5.0D, 0.5D, -0.5D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, 0.0F, -var20);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-5.0D, 0.5D, -0.5D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, 0.5D, -0.5D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, -0.5D, -0.5D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-5.0D, -0.5D, -0.5D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();
        GL11.glNormal3f(0.0F, 0.0F, var20);
        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexBuffer.pos(-5.0D, -0.5D, 0.5D).tex((double) var16, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, -0.5D, 0.5D).tex((double) var17, (double) var18).endVertex();
        vertexBuffer.pos(-3.0D, 0.5D, 0.5D).tex((double) var17, (double) var19).endVertex();
        vertexBuffer.pos(-5.0D, 0.5D, 0.5D).tex((double) var16, (double) var19).endVertex();
        tessellator.draw();

        for (int var21 = 0; var21 < 2; ++var21) {
            GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);//GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, var20);
            vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexBuffer.pos(-2.0D, -0.75D, 0.0D).tex((double) var12, (double) var14).endVertex();
            vertexBuffer.pos(3.0D, -0.75D, 0.0D).tex((double) var13, (double) var14).endVertex();
            vertexBuffer.pos(3.0D, 0.75D, 0.0D).tex((double) var13, (double) var15).endVertex();
            vertexBuffer.pos(-2.0D, 0.75D, 0.0D).tex((double) var12, (double) var15).endVertex();
            tessellator.draw();
        }

        GlStateManager.disableRescaleNormal();//GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GlStateManager.popMatrix();//GL11.glPopMatrix();
    }

    @Override
    public void doRender(@Nonnull Entity_ThrowingKnife var1, double var2, double var4, double var6, float var8, float var9) {
        this.renderKnife(var1, var2, var4, var6, var8, var9);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull Entity_ThrowingKnife entity) {
        return tex;
    }
}
