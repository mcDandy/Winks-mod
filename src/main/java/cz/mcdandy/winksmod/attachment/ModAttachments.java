package cz.mcdandy.winksmod.attachment;

import cz.mcdandy.winksmod.WinksMod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * Player data attachments. {@link #FAIRY_ENERGY} is synced to the client and copied on death, which is what the
 * old capability + {@code FairyEnergyPacket} + {@code PlayerEvent.Clone} combination used to do by hand.
 */
public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WinksMod.MODID);

    public static final Supplier<AttachmentType<FairyEnergy>> FAIRY_ENERGY = ATTACHMENT_TYPES.register(
            "fairy_energy",
            () -> AttachmentType.<FairyEnergy>builder(() -> new FairyEnergy(FairyEnergy.DEFAULT_AMOUNT))
                    .serialize(FairyEnergy.CODEC)
                    .sync(FairyEnergy.STREAM_CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<AccessibleTransformations>> ACCESSIBLE_TRANSFORMATIONS =
            ATTACHMENT_TYPES.register(
                    "accessible_transformations",
                    () -> AttachmentType.<AccessibleTransformations>builder(() -> new AccessibleTransformations(0))
                            .serialize(AccessibleTransformations.CODEC)
                            .sync(AccessibleTransformations.STREAM_CODEC)
                            .copyOnDeath()
                            .build()
            );

    /**
     * The form the player is wearing right now and how that form flies. Synced (so everyone sees the wings), saved
     * (a fairy stays a fairy over a relog) but <b>not</b> {@code copyOnDeath} — dying always returns them to human.
     */
    public static final Supplier<AttachmentType<Transformation>> TRANSFORMATION = ATTACHMENT_TYPES.register(
            "transformation",
            () -> AttachmentType.<Transformation>builder(() -> Transformation.DEFAULT)
                    .serialize(Transformation.CODEC)
                    .sync(Transformation.STREAM_CODEC)
                    .build()
    );

    /** Local integration state for the custom flight physics; neither saved nor synced, every side keeps its own. */
    public static final Supplier<AttachmentType<FlightMotion>> FLIGHT_MOTION = ATTACHMENT_TYPES.register(
            "flight_motion",
            () -> AttachmentType.<FlightMotion>builder(FlightMotion::new).build()
    );

    private ModAttachments() {
    }
}
