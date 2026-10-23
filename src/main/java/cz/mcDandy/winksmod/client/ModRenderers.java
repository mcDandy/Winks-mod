package cz.mcdandy.winksmod.client;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.entity.ModEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/** Client side registration: entity renderers and the model layers they bake. */
@EventBusSubscriber(value = Dist.CLIENT, modid = WinksMod.MODID)
public final class ModRenderers {
    /** Body of the prisoner — same mesh as a skeleton, on our own layer id. */
    public static final ModelLayerLocation PRISONER_BODY = new ModelLayerLocation(Identifier.fromNamespaceAndPath(WinksMod.MODID, "prisoner"), "main");

    /** The clothes overlay rendered over the prisoner's body (0.25 pixel inflation, like a stray's coat). */
    public static final ModelLayerLocation PRISONER_CLOTHES = new ModelLayerLocation(PRISONER_BODY.model(), "outer");

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
    }

    private ModRenderers() {
    }
}
