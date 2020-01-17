package cz.mcDandy.winksmod.Dimensions;

import cz.mcDandy.winksmod.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

public class DimOmegaType extends ModDimension
    {
        public DimOmegaType(final ResourceLocation registryName)
        {
            this.setRegistryName(registryName);
        }

        public static DimensionType getDimensionType()
        {
            return DimensionType.byName(new ResourceLocation(Main.MODID, DimOmega.Name));
        }

        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
        {
            return DimOmega::new;
        }
    }


