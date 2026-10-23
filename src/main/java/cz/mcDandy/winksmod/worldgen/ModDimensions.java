package cz.mcdandy.winksmod.worldgen;

import cz.mcdandy.winksmod.WinksMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

/**
 * Registry keys of the two custom dimensions. Both the dimension itself and its type are pure data packs now:
 * {@code data/winksmod/dimension/*.json} and {@code data/winksmod/dimension_type/*.json}.
 */
public final class ModDimensions {
    public static final ResourceKey<Level> OMEGA = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(WinksMod.MODID, "omega"));
    public static final ResourceKey<Level> OBSIDIAN =
            ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(WinksMod.MODID, "obsidian"));

    public static final ResourceKey<LevelStem> OMEGA_STEM = ResourceKey.create(Registries.LEVEL_STEM, OMEGA.identifier());
    public static final ResourceKey<LevelStem> OBSIDIAN_STEM = ResourceKey.create(Registries.LEVEL_STEM, OBSIDIAN.identifier());

    public static final ResourceKey<DimensionType> OMEGA_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(WinksMod.MODID, "omega"));
    public static final ResourceKey<DimensionType> OBSIDIAN_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(WinksMod.MODID, "obsidian"));

    private ModDimensions() {
    }
}
