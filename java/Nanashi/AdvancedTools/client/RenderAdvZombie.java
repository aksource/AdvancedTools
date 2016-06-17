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

@SideOnly(Side.CLIENT)
public class RenderAdvZombie extends RenderZombie
{
	private static final ResourceLocation Fire = new ResourceLocation(AdvancedTools.textureassets, "textures/mob/fzombie.png");
	private static final ResourceLocation Warrior = new ResourceLocation(AdvancedTools.textureassets, "textures/mob/zombiew.png");
	private static final ResourceLocation Zombie = new ResourceLocation("textures/entity/zombie/zombie.png");

	public RenderAdvZombie(RenderManager var1)
	{
        super(var1);
//		super(var1, new ModelZombie(), 0.5F, 1.0F);
	}


	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
    @Override
	protected ResourceLocation getEntityTexture(EntityZombie par1Entity)
	{
        return par1Entity instanceof Entity_FireZombie ? Fire : (par1Entity instanceof Entity_ZombieWarrior ? Warrior : Zombie);
	}
}
