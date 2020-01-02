package cz.mcDandy.winksmod.Dimensions;

import java.util.function.BiFunction;
import cz.mcDandy.winksmod.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

public class ModDimensions {
	 public static final ResourceLocation OMEGA_RES = new ResourceLocation(Main.MODID,"omega_dim");
	public static ModDimension OMEGA = new ModDimension() {
	        @Override
	        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
	            return DimOmega::new;
	        }
	    }.setRegistryName(OMEGA_RES);
}
