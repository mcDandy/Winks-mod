package cz.mcDandy.winksmod.Dimensions.Biomes;

import cz.mcDandy.winksmod.Blocks.ModBlocks;
import cz.mcDandy.winksmod.Dimensions.FillerBlockTypes;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public final class ObsidianBiome extends Biome {
   protected ObsidianBiome() {
      super((new Builder()).surfaceBuilder(ModSurfaceBuilder.OBSIDIAN_SURFACE_BUIDLER, ModSurfaceBuilder.OBSIDIAN_CONFIG).precipitation(RainType.NONE).category(Category.ICY).depth(0.1F).scale(0.2F).temperature(-2.0F).downfall(0.0F).waterColor(329011).waterFogColor(329011).parent((String)null));
      this.addCarver(GenerationStage.Carving.AIR, createCarver(WorldCarver.HELL_CAVE, new ProbabilityConfig(0.15F)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(FillerBlockTypes.OBSIDIAN, ModBlocks.DARK_OBSIDIAN.getDefaultState(), 14)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 10, 20, 128))));
  }
}