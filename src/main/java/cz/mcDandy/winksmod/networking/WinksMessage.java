package cz.mcDandy.winksmod.networking;


import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class WinksMessage implements IMessage {
  // A default constructor is always required
  public WinksMessage(){}

  float level;
  boolean transformed;
  public WinksMessage(float level,boolean transformed) {
    this.level = level;
this.transformed = transformed;
  }

  @Override
  public void toBytes(ByteBuf buf) {
    // Writes into the buffer
    buf.writeFloat(level);
    buf.writeBoolean(transformed);
  }

  @Override 
  public void fromBytes(ByteBuf buf) {
    // Reads back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
    level = buf.readFloat();
    transformed = buf.readBoolean();
  }
}