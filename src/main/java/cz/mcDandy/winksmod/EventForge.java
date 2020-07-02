package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Capabilities.AccessibleTransformationsCapability;
import cz.mcDandy.winksmod.Capabilities.FairyEnergyCapability;
import cz.mcDandy.winksmod.Capabilities.IAccessibleTransformations;
import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventForge {
    @SubscribeEvent
    public static void registerDimension(final RegisterDimensionsEvent event) {
        DimensionManager.registerDimension(ModDimensions.OMEGA_RL, ModDimensions.DIM_OMEGA, null, false);
        DimensionManager.registerDimension(ModDimensions.OBSIDIAN_RL, ModDimensions.DIM_OBSIDIAN, null, false);
    }

    @SubscribeEvent
    public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Main.MODID, "fairy_energy"), new FairyEnergyCapability());
            event.addCapability(new ResourceLocation(Main.MODID, "accessible_transformations"), new AccessibleTransformationsCapability());
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(AccessibleTransformationsCapability.ACCESSIBLE_TRANSFORMATIONS_CAPABILITY).ifPresent((IAccessibleTransformations originalInstance) -> {
                ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
                player.getCapability(AccessibleTransformationsCapability.ACCESSIBLE_TRANSFORMATIONS_CAPABILITY).ifPresent((IAccessibleTransformations instance) -> {
                    instance.setRawData(originalInstance.getRawData());
                });
            });
        }
    }

    @SubscribeEvent
    public static void OnPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayerEntity) {
            event.player.getCapability(FairyEnergyCapability.FAIRY_ENERGY_CAPABILITY).ifPresent(fe -> {
                fe.addOrSubtractAmount(0.001);
            });
        }
    }
}