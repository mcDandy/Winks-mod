package cz.mcDandy.winksmod.Packets;

import cz.mcDandy.winksmod.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;

public class PacketDispatcher extends PacketDispatcherAbstract {

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public PacketDispatcher() {

        super(NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Main.MODID, "packet")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel());
    }

    @Override
    public void registerPackets() {
        dispatcher.registerMessage(nextID(),FairyEnergyPacket.class,FairyEnergyPacket::encode,FairyEnergyPacket::decode,FairyEnergyPacket::handle);
    }
}
