package cz.mcDandy.winksmod.GUI;

import java.util.logging.Level;

import cz.mcDandy.winksmod.Main;
import cz.mcDandy.winksmod.register.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTp implements IMessage {
	private double X;
	private double Y;
	private double Z;

	public MessageTp() {
	}

	public MessageTp(double X, double Y, double Z) {
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

	public class Handler implements IMessageHandler<MessageTp, IMessage> {
		@Override
		public IMessage onMessage(final MessageTp message, MessageContext ctx) {
			IThreadListener mainThread = Minecraft.getMinecraft();
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					Main.logger.log(Level.INFO, "Server recived Teleport button click.");
					EntityPlayerMP player = ctx.getServerHandler().player;
					Minecraft mc = Minecraft.getMinecraft();
					if (player.getHeldItemMainhand().getItem().equals(new ItemStack(ModItems.solwand).getItem())) {
						if (Y != Double.NaN) { // normal teleport
							if (player.getHeldItemMainhand().getMaxDamage()
									- player.getHeldItemMainhand().getItemDamage() < Math
											.pow(player.getDistance(X, Y, Z), 2)) {
								player.getHeldItemMainhand().damageItem((int) Math.pow(player.getDistance(X, Y, Z), 2),
										player);
								player.setPositionAndUpdate(X, Y, Z);
							} else {
								player.server.sendMessage(new TextComponentString("NotEnouchEnergy"));// not enough
																										// energy
							}
						} else { // safe teleport - Height is surface height

							if (player.getHeldItemMainhand().getMaxDamage()
									- player.getHeldItemMainhand().getItemDamage() < Math
											.pow(player.getDistance(X, 0, Z), 2)) {
								if (mc.world.getChunk(new BlockPos((int) X, 0, (int) Z)).isLoaded()) {
									player.getHeldItemMainhand()
											.damageItem((int) Math.pow(player.getDistance(X, 0, Z), 2), player);
									player.setPositionAndUpdate(X, mc.world.getHeight((int) X, (int) Z), Z);
								} else {
									player.server.sendMessage(new TextComponentString("ChunckNotLoaded"));// Chunk not
																											// loaded
								}
							} else {
								player.server.sendMessage(new TextComponentString("NotEnouchEnergy"));// not enough
																										// energy
							}
						}
					} else {
						Main.logger.log(Level.WARNING, "User " + player.getUniqueID() + " used "
								+ player.getHeldItemMainhand() + " which is unusual");

					}
				}
			});
			return null;
		}
	}
}