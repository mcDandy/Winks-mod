package cz.mcDandy.winksmod.Render.Entities;

import cz.mcDandy.winksmod.Entities.SunSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SunSpellRenderer extends SpriteRenderer<SunSpell> {

    public SunSpellRenderer(EntityRendererManager renderManagerIn, ItemRenderer itemRenderer) {
        super(renderManagerIn, itemRenderer);
    }

    public static class Factory implements IRenderFactory<SunSpell> {
        @Override
        public EntityRenderer<? super SunSpell> createRenderFor(EntityRendererManager manager) {
            return new SunSpellRenderer(manager, Minecraft.getInstance().getItemRenderer());
        }
    }



}