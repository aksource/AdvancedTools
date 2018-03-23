package Nanashi.AdvancedTools.client;

import Nanashi.AdvancedTools.AdvancedTools;
import Nanashi.AdvancedTools.entity.Entity_FireZombie;
import Nanashi.AdvancedTools.entity.Entity_ZombieWarrior;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class RenderAdvZombie extends RenderZombie {
    private static final ResourceLocation FIRE_ZOMBIE = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/fzombie.png");
    private static final ResourceLocation ZOMBIE_WARRIOR = new ResourceLocation(AdvancedTools.TEXTURE_ASSETS, "textures/mob/zombiew.png");
    private static final ResourceLocation ZOMBIE = new ResourceLocation("textures/entity/zombie/zombie.png");

    public RenderAdvZombie(RenderManager renderManager) {
        super(renderManager);
    }


    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(EntityZombie par1Entity) {
        return par1Entity instanceof Entity_FireZombie ? FIRE_ZOMBIE : (par1Entity instanceof Entity_ZombieWarrior ? ZOMBIE_WARRIOR : ZOMBIE);
    }
}
