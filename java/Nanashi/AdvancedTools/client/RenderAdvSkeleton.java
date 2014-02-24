package Nanashi.AdvancedTools.client;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.Entity_HighSkeleton;
import Nanashi.AdvancedTools.Entity_SkeletonSniper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAdvSkeleton extends RenderBiped
{
    private static final ResourceLocation HSke = new ResourceLocation(AdvancedTools.textureassets ,"textures/mob/hskeleton.png");
    private static final ResourceLocation SkeSni = new ResourceLocation(AdvancedTools.textureassets, "textures/mob/skeletons.png");
    private static final ResourceLocation Ske = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderAdvSkeleton()
    {
        super(new ModelSkeleton(), 0.5F);
    }

    protected void scaleSkeleton(EntitySkeleton par1EntitySkeleton, float par2)
    {
        if (par1EntitySkeleton.getSkeletonType() == 1)
        {
            GL11.glScalef(1.2F, 1.2F, 1.2F);
        }
    }

    protected void func_82422_c()
    {
        GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
    }

    protected ResourceLocation func_110860_a(EntitySkeleton entity)
    {
        if(entity instanceof Entity_HighSkeleton)
        {
        	return HSke;
        }
        else if(entity instanceof Entity_SkeletonSniper)
        {
        	return SkeSni;
        }
        else
        	return Ske;
    }

    protected ResourceLocation func_110856_a(EntityLiving par1EntityLiving)
    {
        return this.func_110860_a((EntitySkeleton)par1EntityLiving);
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
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110860_a((EntitySkeleton)par1Entity);
    }
}
