package cz.mcdandy.winksmod.client;

import cz.mcdandy.winksmod.entity.SunSpellEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

/** The sun spell is rendered as the item it is made of (the solar core), like the old sprite renderer did. */
public class SunSpellRenderer extends ThrownItemRenderer<SunSpellEntity> {
    public SunSpellRenderer(EntityRendererProvider.Context context) {
        super(context, 1.0F, false);
    }
}
