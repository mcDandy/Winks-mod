package cz.mcDandy.winksmod.Entities;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGenericFairy extends RenderLiving<EntityGenericFairy> {

	private ResourceLocation mobTexture = new ResourceLocation("modtut:textures/entity/weirdzombie.png");

	public static final Factory FACTORY = new Factory();

	public RenderGenericFairy(RenderManager rendermanagerIn) {
		// We use the vanilla zombie model here and we simply
		// retexture it. Of course you can make your own model
		super(rendermanagerIn, new ModelBiped(), 0.5F);
	}

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityGenericFairy entity) {
		return mobTexture;
	}

	public static class Factory implements IRenderFactory<EntityGenericFairy> {

		@Override
		public Render<? super EntityGenericFairy> createRenderFor(RenderManager manager) {
			return new RenderGenericFairy(manager);
		}

	}

}