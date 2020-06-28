package cz.mcDandy.winksmod.Render.Entities;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PrisonerRenderer extends SkeletonRenderer {
    private static final ResourceLocation textureLoc = new ResourceLocation("minecraft:textures/entity/skeleton/skeleton.png");

    public PrisonerRenderer(EntityRendererManager manager) {
        super(manager);
        this.addLayer(new PrisonerClothingLayerRenderer<>(this));
    }

    public ResourceLocation getEntityTexture(AbstractSkeletonEntity entity) {
        return textureLoc;
    }
}