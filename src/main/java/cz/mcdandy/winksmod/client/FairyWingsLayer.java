package cz.mcdandy.winksmod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.attachment.Transformation;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.object.equipment.ElytraModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;

/**
 * Renders the fairy wings on every player wearing the fairy form.
 *
 * <p>Nothing is equipped for this — the wings are not an item, so the armour slots stay free — and the pose comes
 * from {@link ModRenderers#TRANSFORMATION} which the render state modifier fills from the synced attachment.</p>
 */
public class FairyWingsLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    private static final Identifier WINGS_TEXTURE =
            Identifier.fromNamespaceAndPath(WinksMod.MODID, "textures/entity/fairy_wings.png");

    private final ElytraModel model;

    public FairyWingsLayer(RenderLayerParent<AvatarRenderState, PlayerModel> renderer, EntityModelSet models) {
        super(renderer);
        this.model = new ElytraModel(models.bakeLayer(ModRenderers.FAIRY_WINGS));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        if (!state.getRenderDataOrDefault(ModRenderers.TRANSFORMATION, Transformation.DEFAULT).isFairy()) {
            return;
        }

        poseStack.pushPose();
        // Same nudge the vanilla wings layer uses to clear the back of the head.
        poseStack.translate(0.0F, 0.0F, 0.125F);
        coloredCutoutModelCopyLayerRender(this.model, WINGS_TEXTURE, poseStack, submitNodeCollector, lightCoords, state, -1, 1);
        poseStack.popPose();
    }
}
