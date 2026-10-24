package cz.mcdandy.winksmod.attachment;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * How a transformed player moves through the air. Part of {@link Transformation} so both sides agree on what the
 * movement code simulates: the server owns it (that is what drains the hunger bar), the local client only mirrors it
 * early so its own movement reacts without waiting for the sync packet.
 */
public enum FlightMode {
    /** Not flying — vanilla walking and falling take over again. */
    NONE(0),

    /** Elytra style gliding: speed and lift come from where the player looks. */
    GLIDE(1),

    /** Hanging still in the air; nothing pulls the player down while hovering. */
    HOVER(2);

    public static final Codec<FlightMode> CODEC = Codec.INT.xmap(FlightMode::byId, FlightMode::id);

    public static final StreamCodec<ByteBuf, FlightMode> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(FlightMode::byId, FlightMode::id);

    private final int id;

    FlightMode(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    /** Whether the player is off the ground under their own power. */
    public boolean isFlying() {
        return this != NONE;
    }

    /** @return the mode with this id, or {@link #NONE} when the id is unknown. */
    public static FlightMode byId(int id) {
        for (FlightMode mode : values()) {
            if (mode.id == id) {
                return mode;
            }
        }

        return NONE;
    }
}
