package cz.mcDandy.winksmod.Capatibilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFiary implements IMessage
{
    private double LVL;
    private Boolean transformation;

    public MessageFiary() {}

    public MessageFiary(double lVL, boolean transformed) {
		this.LVL = lVL;
		this.transformation = transformed;
    }
    @Override
    public void fromBytes(ByteBuf buf)
    {
        LVL = buf.readDouble();
        transformation=buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(LVL);
        buf.writeBoolean(transformation);
    }

    public static class Handler implements IMessageHandler<MessageFiary, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageFiary message, MessageContext ctx)
        {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    Minecraft mc = Minecraft.getMinecraft();
                    EntityPlayerSP player = mc.player;
                    IFiary capability = player.getCapability(FiaryProvider.FIARY_CAP, null);
                    if(capability != null)
                        capability.setLVL(message.LVL);
                    capability.setTransformation(message.transformation);
                }
            });
            return null;
        }
    }
}