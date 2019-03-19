package cz.mcDandy.winksmod.GUI;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTp implements IMessage {
	private double X;
	private double Y;
	private double Z;

	public MessageTp() {
	}

	public MessageTp(double X,double Y,double Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		}

	@Override
	public void fromBytes(ByteBuf buf) {
		X = buf.readDouble();
		Y = buf.readDouble();
		Z = buf.readDouble();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(X);
		buf.writeDouble(Y);
		buf.writeDouble(Z);
	}

	public static class Handler implements IMessageHandler<MessageTp, IMessage> {
		@Override
		public IMessage onMessage(final MessageTp message, MessageContext ctx) {
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					Minecraft mc = Minecraft.getMinecraft();
					EntityPlayerSP player = mc.player;
					player.setPositionAndUpdate(X,Y,Z);	
				}
			});
			return null;
		}
	}
}