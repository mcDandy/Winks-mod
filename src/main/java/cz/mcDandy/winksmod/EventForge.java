package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="yourmodid", bus= Mod.EventBusSubscriber.Bus.FORGE)
public class EventForge
{
    public static final ResourceLocation DIMENSION_TYPE_RL = new ResourceLocation("yourmodid", "yourdimension");

    @SubscribeEvent
    public static void onRegisterDimensionsEvent(RegisterDimensionsEvent event)
    {

        DimensionManager.registerDimension(ModDimensions.OMEGA_RL, ModDimensions.DIM_OMEGA, null, false);

    }
}