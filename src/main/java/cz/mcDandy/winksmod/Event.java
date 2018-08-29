package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Capatibilities.CapabilityHandler;
import cz.mcDandy.winksmod.Capatibilities.Fiary;
import cz.mcDandy.winksmod.Capatibilities.FiaryProvider;
import cz.mcDandy.winksmod.Capatibilities.FiaryStorange;
import cz.mcDandy.winksmod.Capatibilities.IFiary;
import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
	public static void AttachCapatibility(AttachCapabilitiesEvent<EntityPlayer> e) 
	{
		//CapabilityManager.INSTANCE.register(IFiary.class, new FiaryStorange(), Fiary.class);
	e.addCapability(CapabilityHandler.FIARY_CAP_RL, (ICapabilityProvider) FiaryProvider.FIARY_CAP);
	}
	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;

		IFiary fiary = player.getCapability(FiaryProvider.FIARY_CAP, null);
		if(fiary==null) {
			fiary=new Fiary(0, false);
		//	player.getCapability(FiaryProvider.FIARY_CAP, null).setLVL(0);
		//	player.getCapability(FiaryProvider.FIARY_CAP, null).setTransformation(false);}
		//	CapabilityClass cap = playerIn.getCapability(FiaryProvider.FIARY_CAP, null)
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event)

	{
if(event.isWasDeath()) {
		EntityPlayer player = event.getEntityPlayer();

		IFiary fiary = player.getCapability(FiaryProvider.FIARY_CAP, null);

		IFiary oldFiaryCapatibility = event.getOriginal().getCapability(FiaryProvider.FIARY_CAP, null);

		fiary.setLVL(oldFiaryCapatibility.getLVL());
		fiary.setTransformation(oldFiaryCapatibility.IsTransformed());
}
	}

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