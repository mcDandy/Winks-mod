package cz.mcdandy.winksmod.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * The player's fairy energy. Replaces the old Forge {@code Capability}: it is stored as a plain data attachment,
 * which NeoForge saves with the player and copies on death for us.
 *
 * <p>The value is mutable on purpose — the per tick regeneration mutates the stored instance instead of writing a
 * new one twenty times a second, which would mean a sync packet per tick.
 */
public final class FairyEnergy {
    public static final double DEFAULT_AMOUNT = 0.0D;

    public static final MapCodec<FairyEnergy> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(Codec.DOUBLE.fieldOf("amount").forGetter(FairyEnergy::amount)).apply(i, FairyEnergy::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FairyEnergy> STREAM_CODEC = StreamCodec.of(
            (buf, value) -> buf.writeDouble(value.amount()),
            buf -> new FairyEnergy(buf.readDouble())
    );

    private double amount;

    public FairyEnergy(double amount) {
        this.amount = Math.max(0.0D, amount);
    }

    public double amount() {
        return amount;
    }

    public void setAmount(double value) {
        this.amount = Math.max(0.0D, value);
    }

    /** @return the new amount, clamped at zero. */
    public double addOrSubtractAmount(double delta) {
        setAmount(amount + delta);
        return amount;
    }

    /** Whether there is at least one full point of energy to spend. */
    public boolean isNotEmpty() {
        return amount > 1.0D;
    }

    @Override
    public String toString() {
        return "FairyEnergy[" + amount + "]";
    }
}
