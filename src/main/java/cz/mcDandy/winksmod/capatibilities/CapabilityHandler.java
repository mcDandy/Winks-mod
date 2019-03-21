package cz.mcDandy.winksmod.capatibilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		// Attach the capability to all players without it
		if (event.getObject() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getObject();
			if (!player.hasCapability(FairyProvider.FAIRY_CAP, null)) {
				IFairy fairy = new Fairy();
				event.addCapability(fairy.getKey(), fairy.getProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		// Send client capability details to the client on login
		if (event.player instanceof EntityPlayerMP) {
			IFairy fairy = event.player.getCapability(FairyProvider.FAIRY_CAP, null);
			if (fairy != null)
				fairy.dataChanged((EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
		// Copy capability on player death to new player
		if (event.isWasDeath() && event.getEntityPlayer() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
			IFairy oldFiary = event.getOriginal().getCapability(FairyProvider.FAIRY_CAP, null);
			IFairy newFiary = player.getCapability(FairyProvider.FAIRY_CAP, null);
			if (oldFiary == null || newFiary == null)
				return;
			newFiary.deserializeNBT(oldFiary.serializeNBT());
			newFiary.dataChanged(player);
		}
	}
}