package cz.mcdandy.winksmod.client;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.entity.PrisonerEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SkeletonClothingLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.Identifier;

/** Renders the prisoner with the vanilla skeleton skin plus the mod's clothes overlay. */
public class PrisonerRenderer extends AbstractSkeletonRenderer<PrisonerEntity, SkeletonRenderState> {
    private static final Identifier BODY_TEXTURE = Identifier.withDefaultNamespace("textures/entity/skeleton/skeleton.png");
    private static final Identifier CLOTHES_TEXTURE =
            Identifier.fromNamespaceAndPath(WinksMod.MODID, "textures/entity/prisoner_overlay.png");

    public PrisonerRenderer(EntityRendererProvider.Context context) {
        super(context, ModRenderers.PRISONER_BODY, ModelLayers.SKELETON_ARMOR);
        this.addLayer(new SkeletonClothingLayer<>(this, context.getModelSet(), ModRenderers.PRISONER_CLOTHES, CLOTHES_TEXTURE));
    }

    @Override
    public Identifier getTextureLocation(SkeletonRenderState state) {
        return BODY_TEXTURE;
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }
}
