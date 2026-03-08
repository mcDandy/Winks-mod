package cz.mcDandy.winksmod.Packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class PacketDispatcherAbstract {
    protected final SimpleChannel dispatcher;
    private byte packetId = 0;

    protected PacketDispatcherAbstract(SimpleChannel channel) {
        dispatcher = channel;
    }


    public abstract void registerPackets();

    /**
     * Send this message to the specified player.
     */
    public final void sendTo(@Nonnull IMessage message, ServerPlayerEntity player) {
        Objects.requireNonNull(message);
        dispatcher.send(PacketDistributor.PLAYER.with(() -> player), message);
     }

    public final void sendToAll(IMessage message) {
        dispatcher.send(PacketDistributor.ALL.noArg(), message);
    }


    public final void sendToAllAround(IMessage message, DimensionType dimension, double x, double y, double z,

                                      double range) {
        sendToAllAround(message, new PacketDistributor.TargetPoint(x, y, z,

                range, dimension));
    }


    public final void sendToAllAround(IMessage message, PacketDistributor.TargetPoint point) {
        dispatcher.send(PacketDistributor.NEAR.with(() -> point), message);
    }

    public final void sendToAllTrackingPlayers(IMessage message, Entity target) {
        dispatcher.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> target), message);
    }


    public final void sendToServer(IMessage message) {
        dispatcher.sendToServer(message);
    }

    protected int nextID() {
        return packetId++;
    }

}
