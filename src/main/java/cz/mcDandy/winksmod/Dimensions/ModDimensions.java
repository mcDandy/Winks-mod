package cz.mcDandy.winksmod.Dimensions;

import java.util.function.BiFunction;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import static cz.mcDandy.winksmod.Main.MODID;

public class ModDimensions {
	public static final ResourceLocation OMEGA_RL = new ResourceLocation(MODID, "omega");

	public static ModDimension DIM_OMEGA = new ModDimension() {
		@Override
		public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
			return DimOmega::new;
		}
	}.setRegistryName(OMEGA_RL);
}
