package cz.mcdandy.winksmod.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * Bit field of the transformations the player has unlocked. Replaces the old {@code IAccessibleTransformations}
 * capability; it is stored as a plain data attachment that survives death through
 * {@link net.neoforged.neoforge.attachment.AttachmentType.Builder#copyOnDeath()}.
 */
public record AccessibleTransformations(int rawData) {
    public static final int COUNT = 32;

    public static final MapCodec<AccessibleTransformations> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("raw_data").forGetter(AccessibleTransformations::rawData)
    ).apply(i, AccessibleTransformations::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AccessibleTransformations> STREAM_CODEC = StreamCodec.of(
            (buf, value) -> buf.writeVarInt(value.rawData()),
            buf -> new AccessibleTransformations(buf.readVarInt())
    );

    public boolean[] getEnabledTransformations() {
        boolean[] enabled = new boolean[COUNT];
        int remaining = rawData;
        for (int i = 0; i < COUNT; i++) {
            enabled[i] = (remaining & 1) == 1;
            remaining >>= 1;
        }
        return enabled;
    }

    /**
     * @return a copy of this field with the transformation {@code id} set to {@code enabled}.
     */
    public AccessibleTransformations withTransformation(int id, boolean enabled) {
        if (id < 0 || id >= COUNT) {
            throw new IndexOutOfBoundsException("Transformation id " + id + " is out of range");
        }

        int updated = enabled ? (rawData | (1 << id)) : (rawData & ~(1 << id));
        return new AccessibleTransformations(updated);
    }

    public boolean isEnabled(int id) {
        return id >= 0 && id < COUNT && (rawData & (1 << id)) != 0;
    }
}
