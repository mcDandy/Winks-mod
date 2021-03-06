package cz.mcDandy.winksmod.Dimensions.Biomes;

import cz.mcDandy.winksmod.Dimensions.FillerBlockTypes;
import cz.mcDandy.winksmod.Entities.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

public final class OmegaBiome extends Biome {
    protected OmegaBiome() {
        super((new Biome.Builder()).surfaceBuilder(ModSurfaceBuilder.OMEGA, ModSurfaceBuilder.PACKED_ICE_CONFIG).precipitation(Biome.RainType.NONE).category(Biome.Category.ICY).depth(0.1F).scale(0.2F).temperature(-2.0F).downfall(0.0F).waterColor(329011).waterFogColor(329011).parent((String) null));
        this.addCarver(GenerationStage.Carving.AIR, createCarver(WorldCarver.HELL_CAVE, new ProbabilityConfig(0.2F)));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(FillerBlockTypes.PACKED_ICE, Blocks.ICE.getDefaultState(), 14)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 10, 20, 128))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(FillerBlockTypes.PACKED_ICE, Blocks.BLUE_ICE.getDefaultState(), 33)).withPlacement(Placement.MAGMA.configure(new FrequencyConfig(4))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Features.ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(1))));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(ModEntities.PRISONER, 1, 2, 4));
    }
}