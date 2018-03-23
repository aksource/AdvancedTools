package Nanashi.AdvancedTools.client;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class Render_UQMagics extends Render<Entity> {
    protected Render_UQMagics(RenderManager renderManager) {
        super(renderManager);
    }


    public void doRender(@Nullable Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {}

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull Entity entity) {
        return TextureMap.LOCATION_MISSING_TEXTURE;
    }
}
