package cz.mcDandy.winksmod.Render.Entities;

import cz.mcDandy.winksmod.Entities.SkeletonBandit;
import cz.mcDandy.winksmod.Entities.SunSpell;
import cz.mcDandy.winksmod.Main;
import cz.mcDandy.winksmod.Render.Models.ModelBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SkeletonBanditRenderer<T extends SkeletonBandit, M extends SkeletonModel<T>> extends MobRenderer<T, M> {
    private static final ResourceLocation textureLoc = new ResourceLocation(Main.MODID,"rextures/entities/skeleton_bandit.png");

    public SkeletonBanditRenderer(EntityRendererManager manager, M model, float shadowSize) {
        super(manager, model, shadowSize);
    }

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return textureLoc;
    }
}