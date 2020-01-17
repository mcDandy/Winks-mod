package cz.mcDandy.winksmod.Dimensions;

import java.util.function.BiFunction;
import cz.mcDandy.winksmod.Event;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;

public class ModDimensions {

	public static DimensionType DIM_OMEGA;
	public static final RegistryObject<ModDimension> DUNGEON_MOD_DIMENSION = --------Event.register(dungeon_basic_regname, DimensionRegistrar::dimFactory);

	private static ModDimension dimFactory() {
	       return new ModDimension() {
	        @Override
	        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
	            return DimOmega::new;
	        }
	    }
    }
}