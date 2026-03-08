package cz.mcDandy.winksmod.Packets;

import cz.mcDandy.winksmod.Capabilities.FairyEnergyCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FairyEnergyPacket implements IMessage {
        private int id;
        private double value;

        public FairyEnergyPacket(int id, double value) {
            this.id = id;
            this.value = value;
        }

        public FairyEnergyPacket(PacketBuffer packetBuffer) {
            id = packetBuffer.readInt();
            value = packetBuffer.readDouble();
        }

     void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeInt(id);
        packetBuffer.writeDouble(value);
    }
    static FairyEnergyPacket decode(PacketBuffer packetBuffer) {
        int id=packetBuffer.readInt();
        double value=packetBuffer.readDouble();
        return new FairyEnergyPacket(id,value);
    }

        public void handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                ServerPlayerEntity player = context.getSender();
                if (player != null) {
                    Entity entity = player.world.getEntityByID(id);
                    if (entity != null) {
                        entity.getCapability(FairyEnergyCapability.FAIRY_ENERGY_CAPABILITY).ifPresent(capability -> {
                            capability.setAmount(value);
                        });
                    }
                }
            });
            context.setPacketHandled(true);
        }
}
