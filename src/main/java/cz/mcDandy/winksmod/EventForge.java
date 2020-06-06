package cz.mcDandy.winksmod;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import cz.mcDandy.winksmod.Capabilities.AccessableTransformationsCapability;
import cz.mcDandy.winksmod.Capabilities.FairyEnergyCapability;
import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
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
    @SubscribeEvent
    public static void onPlayerModelEvent(PlayerModelEvent.Render.Pre event){
        event.getModelPlayer().bipedBody.addBox(0,1.6f,0.5f,1,2,0.1f);
    }
    @SubscribeEvent
    public void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(new ResourceLocation(Main.MODID, "FairyEnergy"), new FairyEnergyCapability());
            event.addCapability(new ResourceLocation(Main.MODID, "AccessableTransformations"), new AccessableTransformationsCapability());
        }
    }
}