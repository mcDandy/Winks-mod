/**
 * 
 */
package cz.mcDandy.winksmod.Dimensions.Omega;

import javax.annotation.Nullable;

import cz.mcDandy.winksmod.Dimensions.Biomes.ModBiomes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ModDimension;

public class DimOmega extends Dimension {

	public DimOmega(World worldIn, DimensionType typeIn) {
		super(worldIn, typeIn);
	}

	/**
	 * Return Vec3D with biome specific fog color
	 */
	public static final DimOmega getProvider(World world) {
		if (world != null && world.dimension instanceof DimOmega) {
			return (DimOmega) world.dimension;
		}
		return null;
	}
	@OnlyIn(Dist.CLIENT)
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		return new Vec3d((double) 0.03F, (double) 0.03F, (double) 0.2F);
	}

	/**
	 * Creates the light to brightness table
	 */
	protected void generateLightBrightnessTable() {
		float f = 0.1F;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
		}

	}

	public ChunkGenerator<?> createChunkGenerator() {
		NetherGenSettings nethergensettings = ChunkGeneratorType.CAVES.createSettings();
		nethergensettings.setDefaultBlock(Blocks.PACKED_ICE.getDefaultState());
		nethergensettings.setDefaultFluid(Blocks.WATER.getDefaultState());
		return ChunkGeneratorType.CAVES.create(this.world,
				BiomeProviderType.FIXED.create(BiomeProviderType.FIXED.createSettings().setBiome(ModBiomes.OMEGA_BIOME)), nethergensettings);
	}

	/**
	 * Returns 'true' if in the "main surface world", but 'false' if in the Nether
	 * or End dimensions.
	 */
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	@Nullable
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
		for(int i = chunkPosIn.getXStart(); i <= chunkPosIn.getXEnd(); ++i) {
			for(int j = chunkPosIn.getZStart(); j <= chunkPosIn.getZEnd(); ++j) {
				BlockPos blockpos = this.findSpawn(i, j, checkValid);
				if (blockpos != null) {
					return blockpos;
				}
			}
		}

		return null;
	}
	@Override
	@Nullable
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(posX, 0, posZ);
		Biome biome = this.world.getBiome(blockpos$mutableblockpos);
		BlockState blockstate = biome.getSurfaceBuilderConfig().getTop();
		if (checkValid && !blockstate.getBlock().isIn(BlockTags.VALID_SPAWN)) {
			return null;
		} else {
			Chunk chunk = this.world.getChunk(posX >> 4, posZ >> 4);
			int i = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, posX & 15, posZ & 15);
			if (i < 0) {
				return null;
			} else if (chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, posX & 15, posZ & 15) > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, posX & 15, posZ & 15)) {
				return null;
			} else {
				for(int j = i + 1; j >= 0; --j) {
					blockpos$mutableblockpos.setPos(posX, j, posZ);
					BlockState blockstate1 = this.world.getBlockState(blockpos$mutableblockpos);
					if (!blockstate1.getFluidState().isEmpty()) {
						break;
					}

					if (blockstate1.equals(blockstate)) {
						return blockpos$mutableblockpos.up().toImmutable();
					}
				}

				return null;
			}
		}
	}
	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time
	 * (usually worldTime)
	 */
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.5F;
	}

	/**
	 * True if the player can respawn in this dimension (true = overworld, false =
	 * nether).
	 */
	public boolean canRespawnHere() {
		return true;
	}

	/**
	 * Returns true if the given X,Z coordinate should show environmental fog.
	 */
	@OnlyIn(Dist.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return true;
	}

	public WorldBorder createWorldBorder() {
		return new WorldBorder() {
			public double getCenterX() {
				return super.getCenterX() / 8.0D;
			}

			public double getCenterZ() {
				return super.getCenterZ() / 8.0D;
			}
		};
	}

}

