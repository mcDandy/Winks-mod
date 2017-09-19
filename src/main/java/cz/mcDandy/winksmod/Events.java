package cz.mcDandy.winksmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events{
	EntityPlayer player;
	@SubscribeEvent
	public void onRightClick(MouseEvent e) {
		if(e.getButton()==1) 
		{
			System.out.println("click");
		}
	}
}