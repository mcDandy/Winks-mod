package cz.mcDandy.winksmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class Event {

	/*
	 * @SubscribeEvent public static void onPlayerJoin(PlayerLoggedInEvent event) {
	 * EntityPlayer player = event.player;
	 * 
	 * IFairy fairy = player.getCapability(FairyProvider.FAIRY_CAP, null); if (fairy
	 * == null) { fairy = new Fairy(0, false); } }
	 */
	// player.getCapability(FairyProvider.FAIRY_CAP, null).setLVL(0);
	// player.getCapability(FairyProvider.FAIRY_CAP,
	// null).setTransformation(false);}
	// CapabilityClass cap = playerIn.getCapability(FairyProvider.FAIRY_CAP, null);

	// Called when a new frame is displayed (See fps)
	@SubscribeEvent
	public static void onRenderTick(TickEvent.RenderTickEvent event) {

	}

	// Called when the server ticks. Usually 20 ticks a second.
	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {

	}

	// Called when the world ticks
	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event) {

	}

}