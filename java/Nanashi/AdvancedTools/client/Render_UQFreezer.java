package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.entity.Entity_IHFrozenMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class Render_UQFreezer extends Render {
    private ItemStack glassItemStack = new ItemStack(Blocks.glass);
    protected Render_UQFreezer(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9)
	{
		Entity_IHFrozenMob var10 = (Entity_IHFrozenMob)var1;
		this.bindEntityTexture(var10);
		GlStateManager.pushMatrix();//GL11.glPushMatrix();
		GlStateManager.translate(var2, var4, var6);//GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GlStateManager.rotate(180 - var8, 0.0F, 1.0F, 0.0F);//GL11.glRotatef(180.0F - var8, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, var10.height * 0.75F, 0.0F);//GL11.glTranslatef(0.0F, var10.height * 0.75F, 0.0F);
		GlStateManager.scale(var10.width * 1.5F, var10.height * 1.5F, var10.width * 1.5F);//GL11.glScalef(var10.width * 1.5F, var10.height * 1.5F, var10.width * 1.5F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.2F);//GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
		GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);//GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        Minecraft.getMinecraft().getItemRenderer().renderItem(null, this.glassItemStack, ItemCameraTransforms.TransformType.NONE);
//		(new RenderBlocks()).renderBlockAsItem(Blocks.glass, 0, var1.getBrightness(var9));
		GlStateManager.popMatrix();//GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}
