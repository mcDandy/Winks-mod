package cz.mcDandy.winksmod.Dimensions.Biomes;

import cz.mcDandy.winksmod.Dimensions.Biomes.BiomeDecoration.SpikeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.IceSpikeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Features {
    public static final Feature<NoFeatureConfig> ICE_SPIKE = register("winksmod:ice_spike", new SpikeFeature(NoFeatureConfig::deserialize));

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return (F)(Registry.<Feature<?>>register(Registry.FEATURE, key, value));
    }
}
