package cz.mcDandy.winksmod.Dimensions;

import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class FileBlockTypes {
    public static final OreFeatureConfig.FillerBlockType PACKED_ICE = OreFeatureConfig.FillerBlockType.create("FillerBlockType","packed_ice",new BlockMatcher(Blocks.PACKED_ICE));
}
