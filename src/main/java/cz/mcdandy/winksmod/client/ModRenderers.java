package cz.mcdandy.winksmod.client;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.attachment.FlightMode;
import cz.mcdandy.winksmod.attachment.ModAttachments;
import cz.mcdandy.winksmod.attachment.Transformation;
import cz.mcdandy.winksmod.entity.ModEntities;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.object.equipment.ElytraModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

/** Client side registration: entity renderers and the model layers they bake. */
@EventBusSubscriber(value = Dist.CLIENT, modid = WinksMod.MODID)
public final class ModRenderers {
    /** Body of the prisoner — same mesh as a skeleton, on our own layer id. */
    public static final ModelLayerLocation PRISONER_BODY = new ModelLayerLocation(Identifier.fromNamespaceAndPath(WinksMod.MODID, "prisoner"), "main");

    /** The clothes overlay rendered over the prisoner's body (0.25 pixel inflation, like a stray's coat). */
    public static final ModelLayerLocation PRISONER_CLOTHES = new ModelLayerLocation(PRISONER_BODY.model(), "outer");

    /** Fairy wings — the elytra mesh baked on its own layer so it can carry our texture. */
    public static final ModelLayerLocation FAIRY_WINGS = new ModelLayerLocation(Identifier.fromNamespaceAndPath(WinksMod.MODID, "fairy_wings"), "main");

    /** Render data key holding what the avatar being rendered currently is; filled by {@link #extractTransformation}. */
    public static final ContextKey<Transformation> TRANSFORMATION =
            new ContextKey<>(Identifier.fromNamespaceAndPath(WinksMod.MODID, "transformation"));

    /** Wings at rest: folded back against the player like elytra on a walking player. */
    private static final float FOLDED_X_ROT = (float) (Math.PI / 12.0);
    private static final float FOLDED_Z_ROT = (float) (-Math.PI / 12.0);

    /** Wings in flight: spread out and tilted, the pose vanilla gives a gliding elytra. */
    private static final float OPEN_X_ROT = (float) (Math.PI / 9.0);
    private static final float OPEN_Z_ROT = (float) (-Math.PI / 2.0);
    private static final float HOVER_FLAP_ANGLE = 0.18F;
    private static final float HOVER_FLAP_PERIOD = 5.0F;

    /** How quickly the wings catch up with their target pose per frame. */
    private static final float POSE_LERP = 0.3F;

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.PRISONER.get(), PrisonerRenderer::new);
        event.registerEntityRenderer(ModEntities.SUN_SPELL.get(), SunSpellRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PRISONER_BODY, SkeletonModel::createBodyLayer);
        event.registerLayerDefinition(PRISONER_CLOTHES, () -> LayerDefinition.create(
            HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32
        ));
        // The elytra mesh itself — the wings "point to" vanilla's own wing model instead of inventing a new one.
        event.registerLayerDefinition(FAIRY_WINGS, ElytraModel::createLayer);
    }

    /** Puts the fairy wings on every player skin renderer. */
    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType skin : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
            if (renderer != null) {
                renderer.addLayer(new FairyWingsLayer(renderer, event.getEntityModels()));
            }
        }
    }

    @SubscribeEvent
    public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState renderState) {
                ModRenderers.extractTransformation(avatar, renderState);
            }
        });
    }

    /**
     * Copies the player's form onto the render state and poses the wings for it. Vanilla drives its own wing pose off
     * the fall flying flag, which we never set (nothing is equipped), so the pose is computed here from the synced
     * {@link cz.mcdandy.winksmod.attachment.FlightMode} instead — that is also why the flight mode has to be synced:
     * every client needs it to animate everyone else's wings.
     */
    private static void extractTransformation(Avatar avatar, AvatarRenderState renderState) {
        Transformation transformation = avatar.getData(ModAttachments.TRANSFORMATION.get());
        renderState.setRenderData(TRANSFORMATION, transformation);

        boolean poweredFlight = transformation.isFairy() && transformation.flight() == FlightMode.GLIDE;
        boolean open = transformation.isFairy() && transformation.flight().isFlying();
        boolean hovering = transformation.isFairy() && transformation.flight() == FlightMode.HOVER;
        // The custom flight does not set vanilla's fall-flying entity flag, so provide the equivalent render state.
        // This makes the avatar use the horizontal powered-flight pose instead of the normal walking pose.
        renderState.isFallFlying = poweredFlight;
        renderState.fallFlyingTimeInTicks = poweredFlight ? 10.0F : 0.0F;
        renderState.shouldApplyFlyingYRot = false;
        float flap = hovering
                ? (float) Math.sin(renderState.ageInTicks * (Math.PI * 2.0 / HOVER_FLAP_PERIOD)) * HOVER_FLAP_ANGLE
                : 0.0F;
        float targetX = open ? OPEN_X_ROT + flap : FOLDED_X_ROT;
        float targetZ = open ? OPEN_Z_ROT : FOLDED_Z_ROT;

        renderState.elytraRotX += (targetX - renderState.elytraRotX) * POSE_LERP;
        renderState.elytraRotY += (0.0F - renderState.elytraRotY) * POSE_LERP;
        renderState.elytraRotZ += (targetZ - renderState.elytraRotZ) * POSE_LERP;
    }

    private ModRenderers() {
    }
}
