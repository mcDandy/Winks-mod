package cz.mcDandy.winksmod.networking;

import cz.mcDandy.winksmod.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class WinksPacketHandler implements IMessageHandler<WinksMessage, IMessage> {
	@Override public IMessage onMessage(WinksMessage message, MessageContext ctx) {
	    // This is the player the packet was sent to the server from
	    EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
	    // The value that was sent
	    //float amount = message.toSend;
	    // Execute the action on the main server thread by adding it as a scheduled task
	    serverPlayer.getServerWorld().addScheduledTask(() -> {
	     // serverPlayer.inventory.addItemStackToInventory(new ItemStack(Items.DIAMOND, amount));
	    });
	    // No response packet
	    return null;
	  }
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);


}