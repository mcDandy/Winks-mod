package cz.mcdandy.winksmod.block;

import cz.mcdandy.winksmod.WinksMod;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Block entities of the mod. A block entity type has to name the blocks it may sit in, so these are registered from
 * {@link ModBlocks} and every holder here is read lazily (the block registry is filled before this one).
 */
public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WinksMod.MODID);

    /** Counts the ticks of {@link SunLightBlock} and fades its light away. */
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SunLightBlockEntity>> SUN_LIGHT = BLOCK_ENTITY_TYPES.register(
            "sun_light",
            () -> new BlockEntityType<SunLightBlockEntity>(SunLightBlockEntity::new, Set.of(ModBlocks.SUN_LIGHT.get()))
    );

    private ModBlockEntities() {
    }
}
