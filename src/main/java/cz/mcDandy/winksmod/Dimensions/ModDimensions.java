package cz.mcDandy.winksmod.Dimensions;

import cz.mcDandy.winksmod.Main;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;


import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import cz.mcDandy.winksmod.Dimensions.DimOmega;

import java.util.function.BiFunction;
import java.util.function.Supplier;
	public class ModDimensions {
		@ObjectHolder(Main.MODID+":omega_dimension")
		public static final ModDimension OMEGA_DIMENSION = null;
		public static final ResourceLocation OMEGA_TYPE_RL = new ResourceLocation(Main.MODID, "omega");



	private static ModDimension dimFactory() {
	       return new ModDimension() {
	        @Override
	        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
	            return DimOmega::new;
	        }
	    };
	}
}
