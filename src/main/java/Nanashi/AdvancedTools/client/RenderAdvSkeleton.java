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

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderAdvSkeleton extends RenderBiped<EntitySkeleton> {
    private static final ResourceLocation HIGH_SKELETON = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/hskeleton.png");
    private static final ResourceLocation SKELETON_SNIPER = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/skeletons.png");
    private static final ResourceLocation SKELETON = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderAdvSkeleton(RenderManager renderManager1) {
        super(renderManager1, new ModelSkeleton(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntitySkeleton entity) {
        if (entity instanceof Entity_HighSkeleton) {
            return HIGH_SKELETON;
        } else if (entity instanceof Entity_SkeletonSniper) {
            return SKELETON_SNIPER;
        }
        return SKELETON;
    }
}
