package cz.mcdandy.winksmod;

import cz.mcdandy.winksmod.attachment.ModAttachments;
import cz.mcdandy.winksmod.block.ModBlocks;
import cz.mcdandy.winksmod.creative.ModCreativeTabs;
import cz.mcdandy.winksmod.entity.ModEntities;
import cz.mcdandy.winksmod.item.ModItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

/**
 * Entry point of the Winks mod.
 *
 * <p>Everything the mod contributes to a registry is a {@link net.neoforged.neoforge.registries.DeferredRegister}
 * attached to the mod event bus here. The old 1.15 code base registered blocks, items, entities and biomes by hand
 * inside registry events; dimensions, dimension types, biomes, features and placed features are plain data pack
 * files now (see {@code data/winksmod}).
 */
@Mod(WinksMod.MODID)
public class WinksMod {
    public static final String MODID = "winksmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WinksMod(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        LOGGER.info("Winks mod initialised");
    }
}
