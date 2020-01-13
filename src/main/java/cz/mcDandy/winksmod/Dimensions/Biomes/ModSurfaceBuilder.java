package cz.mcDandy.winksmod.Dimensions.Biomes;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.*;
import net.minecraft.world.gen.surfacebuilders.OmegaSurfaceBuilder;

import java.util.function.Function;

public class ModSurfaceBuilder<C extends ISurfaceBuilderConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<net.minecraft.world.gen.surfacebuilders.SurfaceBuilder<?>> {
        public static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
        public static final SurfaceBuilderConfig PACKED_ICE_CONFIG = new SurfaceBuilderConfig(PACKED_ICE, PACKED_ICE, PACKED_ICE);
        public static final net.minecraft.world.gen.surfacebuilders.SurfaceBuilder<SurfaceBuilderConfig> OMEGA = register("omega", new OmegaSurfaceBuilder(SurfaceBuilderConfig::deserialize));

        //private final Function<Dynamic<?>, ? extends C> field_215408_a;

        private static <C extends ISurfaceBuilderConfig, F extends net.minecraft.world.gen.surfacebuilders.SurfaceBuilder<C>> F register(String key, F builderIn) {
            return (F)(Registry.<net.minecraft.world.gen.surfacebuilders.SurfaceBuilder<?>>register(Registry.SURFACE_BUILDER, key, builderIn));
        }
}
