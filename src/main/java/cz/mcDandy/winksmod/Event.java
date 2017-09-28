package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.nbt.*;

public class Event {
	EntityPlayer player;
	EnumHand hand;
	int FiaryLevel;
	NBTTagCompound nbt = new NBTTagCompound();
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		// ItemStack itemstack = player.getHeldItem(hand);
		// if(itemstack == ModItems.wings){}
	}

	// Called when the client ticks.
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {

	}

	// Called when the server ticks. Usually 20 ticks a second.
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

	}

	// Called when a new frame is displayed (See fps)
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {

	}

	// Called when the world ticks
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {

	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.LoadFromFile event) {
		if (nbt.hasKey("Fiary Level", 99)) {
			FiaryLevel = nbt.getInteger("Fiary Level");
		} else {
			nbt.setInteger("Fiary Level", 0);
		}
	}

	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.SaveToFile event) {
		nbt.setInteger("Fiary Level", FiaryLevel + 1);
	}
}