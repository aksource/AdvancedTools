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
    protected Render_UQMagics(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }


    public void doRender(@Nullable Entity var1, double var2, double var4, double var6, float var8, float var9) {}

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull Entity entity) {
        return TextureMap.LOCATION_MISSING_TEXTURE;
    }
}
