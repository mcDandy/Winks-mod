package cz.mcDandy.winksmod.Dimensions.Omega;

import javax.annotation.Nullable;

import cz.mcDandy.winksmod.Dimensions.Biomes.ModBiomes;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DimOmega extends Dimension {
	private static final Vec3d fogColor = new Vec3d(0.46, 0.84, 1.0);

	public DimOmega(World worldIn, DimensionType typeIn) {
		super(worldIn, typeIn, 0.1F);
		this.doesWaterVaporize = false;
		this.nether = false;
	}

	@OnlyIn(Dist.CLIENT)
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		return fogColor;
	}

	public ChunkGenerator<?> createChunkGenerator() {
		NetherGenSettings nethergensettings = ChunkGeneratorType.CAVES.createSettings();
		nethergensettings.setDefaultBlock(Blocks.PACKED_ICE.getDefaultState());
		nethergensettings.setDefaultFluid(Blocks.WATER.getDefaultState());
		return ChunkGeneratorType.CAVES.create(this.world, BiomeProviderType.FIXED.create(BiomeProviderType.FIXED.createSettings(this.world.getWorldInfo()).setBiome(ModBiomes.OMEGA_BIOME)), nethergensettings);
	}

	public boolean isSurfaceWorld() {
		return false;
	}

	@Nullable
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
		return null;
	}

	@Nullable
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
		return null;
	}

	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.5F;
	}

	public boolean canRespawnHere() {
		return false;
	}

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