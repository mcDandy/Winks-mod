package cz.mcdandy.winksmod.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public enum Species {
    HUMAN(0),
    FAIRY(1);

    public static final Codec<Species> CODEC = Codec.INT.xmap(Species::byId, Species::id);
    public static final StreamCodec<RegistryFriendlyByteBuf, Species> STREAM_CODEC = StreamCodec.of(
            (buf, species) -> buf.writeVarInt(species.id()),
            buf -> byId(buf.readVarInt())
    );

    private final int id;

    Species(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static Species byId(int id) {
        for (Species species : values()) {
            if (species.id == id) {
                return species;
            }
        }
        return HUMAN;
    }
}
