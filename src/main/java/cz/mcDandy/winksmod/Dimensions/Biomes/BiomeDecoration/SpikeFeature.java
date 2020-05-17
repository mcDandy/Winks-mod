package cz.mcDandy.winksmod.Dimensions.Biomes.BiomeDecoration;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;

import cz.mcDandy.winksmod.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SpikeFeature extends Feature<NoFeatureConfig> {
    public SpikeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51493_1_) {
        super(p_i51493_1_);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        while(worldIn.isAirBlock(pos)) {
            pos = pos.down();
        }
        while((worldIn.getBlockState(pos)==Blocks.BEDROCK.getDefaultState()||worldIn.getBlockState(pos)==Blocks.PACKED_ICE.getDefaultState()) && pos.getY() > 2) {
            pos = pos.down();
        }
        while(worldIn.isAirBlock(pos) && pos.getY() > 2) {
            pos = pos.down();
        }
        Main.LOGGER.debug("Trying to place at: "+pos.getX()+" "+pos.getY()+" "+pos.getZ());

        if (worldIn.getBlockState(pos).getBlock() != Blocks.PACKED_ICE) {
            return false;
        } else {
            pos = pos.up(rand.nextInt(4));
            Main.LOGGER.debug("Placed at: "+pos.getX()+" "+pos.getY()+" "+pos.getZ());
            int i = rand.nextInt(4) + 7;
            int j = i / 4 + rand.nextInt(2);
            if (j > 1 && rand.nextInt(60) == 0) {
                pos = pos.up(10 + rand.nextInt(30));
            }

            for(int k = 0; k < i; ++k) {
                float f = (1.0F - (float)k / (float)i) * (float)j;
                int l = MathHelper.ceil(f);

                for(int i1 = -l; i1 <= l; ++i1) {
                    float f1 = (float)MathHelper.abs(i1) - 0.25F;

                    for(int j1 = -l; j1 <= l; ++j1) {
                        float f2 = (float)MathHelper.abs(j1) - 0.25F;
                        if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(rand.nextFloat() > 0.75F))) {
                            BlockState blockstate = worldIn.getBlockState(pos.add(i1, k, j1));
                            Block block = blockstate.getBlock();
                            if (blockstate.isAir(worldIn, pos.add(i1, k, j1)) || block == Blocks.PACKED_ICE || block == Blocks.ICE) {
                                this.setBlockState(worldIn, pos.add(i1, k, j1), Blocks.ICE.getDefaultState());
                            }

                            if (k != 0 && l > 1) {
                                blockstate = worldIn.getBlockState(pos.add(i1, -k, j1));
                                block = blockstate.getBlock();
                                if (blockstate.isAir(worldIn, pos.add(i1, -k, j1))) {
                                    this.setBlockState(worldIn, pos.add(i1, -k, j1), Blocks.PACKED_ICE.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }

            int k1 = j - 1;
            if (k1 < 0) {
                k1 = 0;
            } else if (k1 > 1) {
                k1 = 1;
            }

            for(int l1 = -k1; l1 <= k1; ++l1) {
                for(int i2 = -k1; i2 <= k1; ++i2) {
                    BlockPos blockpos = pos.add(l1, -1, i2);
                    int j2 = 50;
                    if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
                        j2 = rand.nextInt(5);
                    }

                    while(blockpos.getY() > 50) {
                        BlockState blockstate1 = worldIn.getBlockState(blockpos);
                        Block block1 = blockstate1.getBlock();
                        if (!blockstate1.isAir(worldIn, blockpos) && !isDirt(block1) && block1 != Blocks.SNOW_BLOCK && block1 != Blocks.ICE && block1 != Blocks.PACKED_ICE) {
                            break;
                        }

                        this.setBlockState(worldIn, blockpos, Blocks.PACKED_ICE.getDefaultState());
                        blockpos = blockpos.down();
                        --j2;
                        if (j2 <= 0) {
                            blockpos = blockpos.down(rand.nextInt(5) + 1);
                            j2 = rand.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}
