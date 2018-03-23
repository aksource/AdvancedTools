package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.AdvancedTools;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderGCreeper extends RenderCreeper {
    /**
     * The creeper model.
     */
    private static final ResourceLocation GOLD_CREEPER = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/gcreeper.png");

    public RenderGCreeper(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    @Nullable
    protected ResourceLocation getEntityTexture(@Nonnull EntityCreeper entity) {
        return GOLD_CREEPER;
    }
}
