package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_HighSkeleton;
import Nanashi.AdvancedTools.entity.Entity_SkeletonSniper;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderAdvSkeleton extends RenderBiped<EntitySkeleton> {
    private static final ResourceLocation HSke = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/hskeleton.png");
    private static final ResourceLocation SkeSni = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/skeletons.png");
    private static final ResourceLocation Ske = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderAdvSkeleton(RenderManager renderManager1) {
        super(renderManager1, new ModelSkeleton(), 0.5F);
    }

    public void func_82422_c() {
        GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntitySkeleton entity) {
        if (entity instanceof Entity_HighSkeleton) {
            return HSke;
        } else if (entity instanceof Entity_SkeletonSniper) {
            return SkeSni;
        }
        return Ske;
    }
}
