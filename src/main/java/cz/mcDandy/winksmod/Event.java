package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Event{
EntityPlayer player;
EnumHand hand;
@SubscribeEvent
 public void onPlayerTick(TickEvent.PlayerTickEvent event) {
	//ItemStack itemstack = player.getHeldItem(hand);
	//if(itemstack == ModItems.wings){}
}
 
 //Called when the client ticks. 
 @SubscribeEvent
 public void onClientTick(TickEvent.ClientTickEvent event) {

}
 
 //Called when the server ticks. Usually 20 ticks a second. 
 @SubscribeEvent
 public void onServerTick(TickEvent.ServerTickEvent event) {

}
 
 //Called when a new frame is displayed (See fps) 
 @SubscribeEvent
 public void onRenderTick(TickEvent.RenderTickEvent event) {

}
 
 //Called when the world ticks
 @SubscribeEvent
 public void onWorldTick(TickEvent.WorldTickEvent event) {

}}