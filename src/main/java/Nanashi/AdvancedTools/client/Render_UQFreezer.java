package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.entity.Entity_IHFrozenMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class Render_UQFreezer extends Render<Entity_IHFrozenMob> {
    private ItemStack glassItemStack = new ItemStack(Blocks.GLASS);

    protected Render_UQFreezer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull Entity_IHFrozenMob entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.bindEntityTexture(entity);
        GlStateManager.pushMatrix();//GL11.glPushMatrix();
        GlStateManager.translate(x, y, z);//GL11.glTranslatef((float)var2, (float)var4, (float)var6);
        GlStateManager.rotate(180 - entityYaw, 0.0F, 1.0F, 0.0F);//GL11.glRotatef(180.0F - var8, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, entity.height * 0.75F, 0.0F);//GL11.glTranslatef(0.0F, var1.height * 0.75F, 0.0F);
        GlStateManager.scale(entity.width * 1.5F, entity.height * 1.5F, entity.width * 1.5F);//GL11.glScalef(var1.width * 1.5F, var1.height * 1.5F, var1.width * 1.5F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.2F);//GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().getItemRenderer().renderItem(null, this.glassItemStack, ItemCameraTransforms.TransformType.NONE);
//		(new RenderBlocks()).renderBlockAsItem(Blocks.glass, 0, var1.getBrightness(var9));
        GlStateManager.popMatrix();//GL11.glPopMatrix();
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull Entity_IHFrozenMob entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
