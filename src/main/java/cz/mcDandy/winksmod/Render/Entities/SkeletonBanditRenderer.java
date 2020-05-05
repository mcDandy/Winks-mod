package cz.mcDandy.winksmod.Render.Entities;

import cz.mcDandy.winksmod.Entities.SkeletonBandit;
import cz.mcDandy.winksmod.Entities.SunSpell;
import cz.mcDandy.winksmod.Main;
import cz.mcDandy.winksmod.Render.Models.ModelBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.StayClothingLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.model.Models;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SkeletonBanditRenderer extends SkeletonRenderer {
    private static final ResourceLocation textureLoc = new ResourceLocation("minecraft:textures/entity/skeleton/skeleton.png");

    public SkeletonBanditRenderer(EntityRendererManager manager) {
        super(manager);
        this.addLayer(new SkeletonBanditClothingLayerRenderer<>(this));
    }
    public ResourceLocation getEntityTexture(AbstractSkeletonEntity entity) {
        return textureLoc;
    }
}