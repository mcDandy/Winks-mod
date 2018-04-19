package cz.mcDandy.winksmod.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class WinksPacketHandlerClient implements IMessageHandler<TransformMSG, IMessage> {
	public IMessage onMessage(final TransformMSG message, MessageContext ctx) {
		if (ctx.side != Side.CLIENT) {
			System.err.println("TransformedToClient received on wrong side:" + ctx.side);
			return null;
		}
		if (!message.isMessageValid()) {
			System.err.println("Package was invalid" + message.toString());
			return null;
		}

		// we know for sure that this handler is only used on the client side, so it is
		// ok to assume
		// that the ctx handler is a client, and that Minecraft exists.
		// Packets received on the server side must be handled differently! See
		// MessageHandlerOnServer

		// This code creates a new task which will be executed by the client during the
		// next tick,
		// for example see Minecraft.runGameLoop() , just under section
		// In this case, the task is to call
		// messageHandlerOnClient.processMessage(worldclient, message)
		Minecraft minecraft = Minecraft.getMinecraft();
		final WorldClient worldClient = minecraft.world;
		minecraft.addScheduledTask(new Runnable() {
			public void run() {
				processMessage(worldClient, message);
			}
		});

		return null;
	}

	protected void processMessage(WorldClient worldClient, TransformMSG message) {
		// TODO Auto-generated method stub

	}
}

// This message is called from the Client thread.
// It spawns a number of Particle particles at the target location within a
// short range around the target location
