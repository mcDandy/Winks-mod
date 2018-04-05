package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.capatibilities.FiaryCapatibilityProvider;
import cz.mcDandy.winksmod.capatibilities.IFiaryCapatibility;
import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Event {

	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;

		 IFiaryCapatibility fiaryCapatibility = player.getCapability(FiaryCapatibilityProvider.FIARY_CAP, null);
	}




	@SubscribeEvent

	public void onPlayerClone(PlayerEvent.Clone event)

	{

	 EntityPlayer player = event.getEntityPlayer();

	 IFiaryCapatibility fiaryCapatibility = player.getCapability(FiaryCapatibilityProvider.FIARY_CAP, null);

	 IFiaryCapatibility oldFiaryCapatibility = event.getOriginal().getCapability(FiaryCapatibilityProvider.FIARY_CAP, null);
	 
	 fiaryCapatibility.set(oldFiaryCapatibility.getFiaryLVL());

	}
	// Called when a new frame is displayed (See fps)
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {

	}

	// Called when the server ticks. Usually 20 ticks a second.
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

	}

	// Called when the world ticks
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {

	}

}