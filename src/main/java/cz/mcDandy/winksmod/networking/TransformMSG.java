package cz.mcDandy.winksmod.networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TransformMSG implements IMessage {
	public TransformMSG(boolean trans) {
		transformed = trans;
		messageIsValid = true;
	}

	/*
	 * public Vec3d getTargetCoordinates() { return targetCoordinates; }
	 */

	public boolean isMessageValid() {
		return messageIsValid;
	}

	// for use by the message handler only.
	public TransformMSG() {
		messageIsValid = false;
	}

	/**
	 * Called by the network code once it has received the message bytes over the
	 * network. Used to read the ByteBuf contents into your member variables
	 * 
	 * @param buf
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			transformed = buf.readBoolean();

			// these methods may also be of use for your code:
			// for Itemstacks - ByteBufUtils.readItemStack()
			// for NBT tags ByteBufUtils.readTag();
			// for Strings: ByteBufUtils.readUTF8String();

		} catch (IndexOutOfBoundsException ioe) {
			System.err.println("Exception while reading transformed: " + ioe);
			return;
		}
		messageIsValid = true;
	}

	/**
	 * Called by the network code. Used to write the contents of your message member
	 * variables into the ByteBuf, ready for transmission over the network.
	 * 
	 * @param buf
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!messageIsValid)
			return;
		buf.writeBoolean(transformed);

		// these methods may also be of use for your code:
		// for Itemstacks - ByteBufUtils.writeItemStack()
		// for NBT tags ByteBufUtils.writeTag();
		// for Strings: ByteBufUtils.writeUTF8String();
		// System.out.println("TargetEffectMessageToClient:toBytes length=" +
		// buf.readableBytes()); // debugging only
	}

	@Override
	public String toString() {
		return "TransformToClient[transformed=" + transformed + "]";
	}

	private boolean transformed;
	private boolean messageIsValid;
}