package cz.mcDandy.winksmod.Render.Entities;

import cz.mcDandy.winksmod.Entities.SkeletonBandit;
import cz.mcDandy.winksmod.Entities.SunSpell;
import cz.mcDandy.winksmod.Render.Models.ModelBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SkeletonBanditRenderer extends EntityRenderer<SkeletonBandit> {

    public SkeletonBanditRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(SkeletonBandit entity) {
        return null;
    }

    public static class Factory implements IRenderFactory<SkeletonBandit> {
        @Override
        public EntityRenderer<? super SkeletonBandit> createRenderFor(EntityRendererManager manager) {
            return new SkeletonBanditRenderer(manager);
        }
    }
}