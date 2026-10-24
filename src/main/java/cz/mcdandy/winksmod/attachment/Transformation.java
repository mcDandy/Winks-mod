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
public record Transformation(boolean transformed, Species species, FlightMode flight) {
    public static final Transformation DEFAULT = new Transformation(false, Species.HUMAN, FlightMode.NONE);

    public static final MapCodec<Transformation> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.BOOL.fieldOf("transformed").forGetter(Transformation::transformed),
            Species.CODEC.fieldOf("species").forGetter(Transformation::species),
            FlightMode.CODEC.fieldOf("flight").forGetter(Transformation::flight)
    ).apply(i, Transformation::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Transformation> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            Transformation::transformed,
            Species.STREAM_CODEC,
            Transformation::species,
            FlightMode.STREAM_CODEC,
            Transformation::flight,
            Transformation::new
    );

    /** Whether the player currently has an active transformation. */
    public boolean isTransformed() {
        return transformed;
    }

    /** Whether the player is a fairy, regardless of transformation state. */
    public boolean isFairy() {
        return species == Species.FAIRY;
    }

    /**
     * @return a copy of this state with species {@code newType}. Selecting fairy does not transform the player;
     * transformation is controlled separately by {@link #withTransformed(boolean)}.
     */
    public Transformation withSpecies(Species newSpecies) {
        boolean nextTransformed = transformed && newSpecies == Species.FAIRY;
        return new Transformation(nextTransformed, newSpecies, nextTransformed ? flight : FlightMode.NONE);
    }

    /** @return a copy with the transformation state changed without changing the species type. */
    public Transformation withTransformed(boolean nextTransformed) {
        return new Transformation(nextTransformed, species, nextTransformed ? flight : FlightMode.NONE);
    }

    /** @return a copy of this state flying like {@code mode}. */
    public Transformation withFlight(FlightMode mode) {
        return flight == mode ? this : new Transformation(transformed, species, mode);
    }

    @Override
    public String toString() {
        return "Transformation[" + transformed + ", species=" + species + " + " + flight + "]";
    }
}
