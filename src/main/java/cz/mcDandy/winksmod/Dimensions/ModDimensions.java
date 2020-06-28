package cz.mcDandy.winksmod.Dimensions;

import cz.mcDandy.winksmod.Dimensions.Dimension.DimObsidian;
import cz.mcDandy.winksmod.Dimensions.Dimension.DimOmega;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

import static cz.mcDandy.winksmod.Main.MODID;

public class ModDimensions {
    public static final ResourceLocation OMEGA_RL = new ResourceLocation(MODID, "omega");
    public static final ResourceLocation OBSIDIAN_RL = new ResourceLocation(MODID, "obsidian");

    public static ModDimension DIM_OMEGA = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return DimOmega::new;
        }
    }.setRegistryName(OMEGA_RL);
    public static ModDimension DIM_OBSIDIAN = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return DimObsidian::new;
        }
    }.setRegistryName(OBSIDIAN_RL);
}
