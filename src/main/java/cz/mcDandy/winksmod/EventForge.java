package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=Main.MODID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class EventForge
{
    @SubscribeEvent
    public static void registerDimension(final RegisterDimensionsEvent event)
    {
        DimensionManager.registerDimension(ModDimensions.OMEGA_RL, ModDimensions.DIM_OMEGA, null, false);
        DimensionManager.registerDimension(ModDimensions.OBSIDIAN_RL, ModDimensions.DIM_OBSIDIAN, null, false);
    }

}