package cz.mcDandy.winksmod.Capatibilities;

import cz.mcDandy.winksmod.Capatibilities.FairyProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntityMP;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityHandler {
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		// Attach the capability to all players without it
		if (event.getObject() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getObject();
			if (!player.hasCapability(FairyProvider.FAIRY_CAP, null)) {
				IFairy fairy = new Fairy();
				event.addCapability(fairy.getKey(), fairy.getProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		// Send client capability details to the client on login
		if (event.getPlayer() instanceof PlayerEntityMP) {
			IFairy fairy = event.getPlayer().getCapability(FairyProvider.FAIRY_CAP, null);
			if (fairy != null)
				fairy.dataChanged((PlayerEntityMP) event.player);
		}
	}

	@SubscribeEvent
	public static void onClonePlayer(PlayerEvent.Clone event) {
		// Copy capability on player death to new player
		if (event.isWasDeath() && event.getPlayerEntity() instanceof PlayerEntityMP) {
			PlayerEntityMP player = (PlayerEntityMP) event.getPlayerEntity();
			IFairy oldFiary = event.getOriginal().getCapability(FairyProvider.FAIRY_CAP, null);
			IFairy newFiary = player.getCapability(FairyProvider.FAIRY_CAP, null);
			if (oldFiary == null || newFiary == null)
				return;
			newFiary.deserializeNBT(oldFiary.serializeNBT());
			newFiary.dataChanged(player);
		}
	}
}