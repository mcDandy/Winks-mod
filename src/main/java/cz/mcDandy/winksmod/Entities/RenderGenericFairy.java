package cz.mcDandy.winksmod.Entities;

import javax.annotation.Nonnull;

import cz.mcDandy.winksmod.Main;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderGenericFairy extends RenderLiving<EntityFairyGeneric> {

	private ResourceLocation mobTexture = new ResourceLocation(Main.MODID, ":textures/entities/FairyGeneric.png");

	public static final Factory FACTORY = new Factory();

	public RenderGenericFairy(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityFairyGeneric entity) {
		return mobTexture;
	}

	public static class Factory implements IRenderFactory<EntityFairyGeneric> {

		@Override
		public Render<? super EntityFairyGeneric> createRenderFor(RenderManager manager) {
			return new RenderGenericFairy(manager, new ModelBiped(), 0.5F);
		}

	}

}