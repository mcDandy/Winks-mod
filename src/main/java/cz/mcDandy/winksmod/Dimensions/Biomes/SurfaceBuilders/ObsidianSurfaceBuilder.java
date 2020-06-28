package cz.mcDandy.winksmod.Dimensions.Biomes.SurfaceBuilders;


import com.mojang.datafixers.Dynamic;
import cz.mcDandy.winksmod.Blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class ObsidianSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
    private static final BlockState DARK_OBSIDIAN = ModBlocks.DARK_OBSIDIAN.getDefaultState();
    protected long seed;
    protected OctavesNoiseGenerator octavesNoiseGenerator;


    public ObsidianSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51308_1_) {
        super(p_i51308_1_);
    }

    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int i = seaLevel + 1;
        int j = x & 15;
        int k = z & 15;
        double d0 = 0.03125D;
        boolean flag = this.octavesNoiseGenerator.func_205563_a((double) x * 0.03126D, (double) z * 0.03126D, 0.0D) + random.nextDouble() * 0.2D > 0.0D;
        boolean flag1 = this.octavesNoiseGenerator.func_205563_a((double) x * 0.03125D, 109.0D, (double) z * 0.03125D) + random.nextDouble() * 0.2D > 0.0D;
        int l = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int i1 = -1;
        BlockState blockstate = OBSIDIAN;
        BlockState blockstate1 = OBSIDIAN;

        for (int j1 = 127; j1 >= 0; --j1) {
            blockpos$mutable.setPos(j, j1, k);
            BlockState blockstate2 = chunkIn.getBlockState(blockpos$mutable);
            if (blockstate2.getBlock() != null && !blockstate2.isAir()) {
                if (blockstate2.getBlock() == defaultBlock.getBlock()) {
                    if (i1 == -1) {
                        if (l <= 0) {
                            blockstate = CAVE_AIR;
                            blockstate1 = OBSIDIAN;
                        } else if (j1 >= i - 4 && j1 <= i + 1) {
                            blockstate = OBSIDIAN;
                            blockstate1 = OBSIDIAN;
                            if (flag1) {
                                blockstate = OBSIDIAN;
                                blockstate1 = OBSIDIAN;
                            }

                            if (flag) {
                                blockstate = DARK_OBSIDIAN;
                                blockstate1 = DARK_OBSIDIAN;
                            }
                        }

                        if (j1 < i && (blockstate == null || blockstate.isAir())) {
                            blockstate = defaultFluid;
                        }

                        i1 = l;
                        if (j1 >= i - 1) {
                            chunkIn.setBlockState(blockpos$mutable, blockstate, false);
                        } else {
                            chunkIn.setBlockState(blockpos$mutable, blockstate1, false);
                        }
                    } else if (i1 > 0) {
                        --i1;
                        chunkIn.setBlockState(blockpos$mutable, blockstate1, false);
                    }
                }
            } else {
                i1 = -1;
            }
        }

    }

    public void setSeed(long seed) {
        if (this.seed != seed || this.octavesNoiseGenerator == null) {
            this.octavesNoiseGenerator = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4, 0);
        }

        this.seed = seed;
    }
}