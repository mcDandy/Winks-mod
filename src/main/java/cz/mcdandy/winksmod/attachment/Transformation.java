package cz.mcdandy.winksmod.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * The form the player currently has, plus how that form flies. Unlike {@link AccessibleTransformations} (which lists
 * what the player unlocked and survives death on purpose) this is the <em>current</em> state: it is deliberately not
 * copied on death, so dying always brings the player back to {@link #HUMAN}.
 *
 * <p>The value is synced to every client that tracks the player — that is what lets other players see fairy wings —
 * but only the server writes it for anyone other than the local player.</p>
 */
public record Transformation(boolean transformed, int type, FlightMode flight) {
    /** The plain player: no wings, no custom movement. */
    public static final int HUMAN = 0;

    /** The fairy form: renders wings and unlocks gliding and hovering. */
    public static final int FAIRY = 1;

    public static final Transformation DEFAULT = new Transformation(false, HUMAN, FlightMode.NONE);

    public static final MapCodec<Transformation> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.BOOL.fieldOf("transformed").forGetter(Transformation::transformed),
            Codec.INT.fieldOf("type").forGetter(Transformation::type),
            FlightMode.CODEC.fieldOf("flight").forGetter(Transformation::flight)
    ).apply(i, Transformation::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Transformation> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            Transformation::transformed,
            ByteBufCodecs.VAR_INT,
            Transformation::type,
            FlightMode.STREAM_CODEC,
            Transformation::flight,
            Transformation::new
    );

    /** Whether the player is in any of the transformed forms. */
    public boolean isTransformed() {
        return transformed;
    }

    /** Whether the active transformed species has fairy flight. */
    public boolean isFairy() {
        return transformed && type == FAIRY;
    }

    /**
     * @return a copy of this state with species {@code newType}. Selecting fairy does not transform the player;
     * transformation is controlled separately by {@link #withTransformed(boolean)}.
     */
    public Transformation withType(int newType) {
        boolean nextTransformed = transformed && newType == FAIRY;
        return new Transformation(nextTransformed, newType, nextTransformed ? flight : FlightMode.NONE);
    }

    /** @return a copy with the transformation state changed without changing the species type. */
    public Transformation withTransformed(boolean nextTransformed) {
        return new Transformation(nextTransformed, type, nextTransformed ? flight : FlightMode.NONE);
    }

    /** @return a copy of this state flying like {@code mode}. */
    public Transformation withFlight(FlightMode mode) {
        return flight == mode ? this : new Transformation(transformed, type, mode);
    }

    @Override
    public String toString() {
        return "Transformation[" + transformed + ", type=" + type + " + " + flight + "]";
    }
}
