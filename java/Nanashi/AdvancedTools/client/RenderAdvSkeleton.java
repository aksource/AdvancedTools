package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_HighSkeleton;
import Nanashi.AdvancedTools.entity.Entity_SkeletonSniper;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderAdvSkeleton extends RenderBiped
{
    private static final ResourceLocation HSke = new ResourceLocation(AdvancedTools.textureassets ,"textures/mob/hskeleton.png");
    private static final ResourceLocation SkeSni = new ResourceLocation(AdvancedTools.textureassets, "textures/mob/skeletons.png");
    private static final ResourceLocation Ske = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderAdvSkeleton(RenderManager renderManager1)
    {
        super(renderManager1, new ModelSkeleton(), 0.5F);
    }

    protected void scaleSkeleton(EntitySkeleton par1EntitySkeleton, float par2)
    {
        if (par1EntitySkeleton.getSkeletonType() == 1)
        {
            GL11.glScalef(1.2F, 1.2F, 1.2F);
        }
    }

    public void func_82422_c()
    {
        GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.scaleSkeleton((EntitySkeleton)par1EntityLivingBase, par2);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        if(entity instanceof Entity_HighSkeleton)
        {
            return HSke;
        }
        else if(entity instanceof Entity_SkeletonSniper)
        {
            return SkeSni;
        }
        return Ske;
    }
}
