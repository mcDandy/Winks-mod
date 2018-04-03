package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Event {



	EntityPlayer player;

	EnumHand hand;

	int FiaryLevel;

	// Called when the client ticks.
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {

	}

	@SubscribeEvent
	public void onLivingEvent(LivingUpdateEvent event) {
		if (event.getEntity() == player) {
			ItemStack itemstack = player.getHeldItem(hand);
			if (itemstack.getItem() == ModItems.wings) {
			}
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.LoadFromFile event) {
		NBTTagCompound nbt = player.getEntityData();
		if (nbt.hasKey("Fiary Level", 99)) {
			FiaryLevel = nbt.getInteger("Fiary Level");
			System.out.println(FiaryLevel);
		} else {
			nbt.setInteger("Fiary Level", 0);
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.SaveToFile event) {
		NBTTagCompound nbt = player.getEntityData();
		nbt.setInteger("Fiary Level", FiaryLevel + 1);
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