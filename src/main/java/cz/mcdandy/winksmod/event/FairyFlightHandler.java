package cz.mcdandy.winksmod.event;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.attachment.FlightMode;
import cz.mcdandy.winksmod.attachment.FlightMotion;
import cz.mcdandy.winksmod.attachment.ModAttachments;
import cz.mcdandy.winksmod.attachment.Transformation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * The flight of the fairy form.
 *
 * <p>Vanilla only glides when an item in an equipment slot carries the {@code elytra_flight} component, and every
 * slot the player could wear that in is a slot they may want for real armour — so nothing is ever equipped here.
 * Instead this handler copies what vanilla's own fall flying does: it keeps its own velocity, feeds it to the entity
 * each tick and lets the entity integrate it into the position like any other movement.</p>
 *
 * <p>The server owns the {@link FlightMode} (it reads the jump key out of the input packet the client sends and is
 * the side that drains the hunger bar); the local client runs the very same code on its own player so take off,
 * braking and landing do not wait for a round trip. Both sides read the same keys, so they agree; when they somehow
 * do not, the synced attachment from the server simply overwrites the prediction.</p>
 */
@EventBusSubscriber(modid = WinksMod.MODID)
public final class FairyFlightHandler {
    /** Vanilla's default entity gravity. Our glide math applies it ourselves so nothing else can touch it. */

    /** Powered flight uses elytra steering plus a reduced rocket-like forward thrust. */
    private static final double POWERED_ACCELERATION = 0.2D;
    private static final double MAX_POWERED_SPEED = 0.8D;
    private static final double SNEAK_FACTOR = 0.2D;
    private static final double SPRINT_FACTOR = 1.8D;
    private static final int HOLD_TICKS = 3;
    private static final double HOVER_VERTICAL_SPEED = 0.12D;

    /**
     * What flying costs per block moved: twice what sprinting costs. Vanilla drains {@code 0.1} exhaustion per block
     * sprinted (see {@code ServerPlayer#checkMovementStatistics}), so flying burns food at double that rate.
     */
    private static final double EXHAUSTION_PER_BLOCK = 0.2D;

    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.isEffectiveAi()) {
            player.getData(ModAttachments.FLIGHT_MOTION.get()).setWasOnGround(player.onGround());
        }
    }

    /**
     * One tick of fairy flight: reads the keys, moves between the flight modes and hands the resulting velocity to
     * the entity. Draining the hunger bar happens here too, on the server only, exactly like vanilla's own movement
     * statistics do.
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // The server runs this for every player (it is what knows they are flying and drains their food bar); a
        // client only runs it for its own avatar, which is the one entity it actually moves.
        if (!player.isEffectiveAi()) {
            return;
        }

        AttachmentType<Transformation> transformationType = ModAttachments.TRANSFORMATION.get();
        Transformation current = player.getData(transformationType);
        FlightMotion motion = player.getData(ModAttachments.FLIGHT_MOTION.get());

        if (!current.isFairy() || !current.isTransformed()) {
            motion.reset();
            motion.setWasOnGround(player.onGround());
            return;
        }

        FlightMode mode = current.flight();
        boolean jump = jumpKeyDown(player);
        boolean wasJumpDown = motion.jumpDown();
        int previousJumpHeldTicks = motion.jumpHeldTicks();
        boolean tapped = jump && !wasJumpDown;
        boolean released = !jump && wasJumpDown;
        if (jump) {
            motion.setJumpHeldTicks(Math.min(HOLD_TICKS, previousJumpHeldTicks + 1));
        } else {
            motion.setJumpHeldTicks(0);
        }
        motion.setJumpDown(jump);

        // A normal ground jump is left entirely to vanilla. Only a tap while falling starts flight; this prevents
        // the post-jump tick from replacing vanilla's upward impulse with flight gravity.
        if (!mode.isFlying()) {
            if (tapped && !player.onGround() && !motion.wasOnGround() && player.getDeltaMovement().y <= 0.0D) {
                motion.setVelocity(player.getDeltaMovement().scale(0.25D));
                setFlight(player, transformationType, current, FlightMode.POWERED);
            }

            motion.setWasOnGround(player.onGround());
            return;
        }

        // A short space press switches modes. Holding space in hover is reserved for ascending.
        boolean shortJumpTap = tapped && mode != FlightMode.HOVER;
        boolean hoverJumpToggle = released && previousJumpHeldTicks < HOLD_TICKS;
        if (shortJumpTap || hoverJumpToggle) {
            mode = mode == FlightMode.HOVER ? FlightMode.POWERED : FlightMode.HOVER;
            setFlight(player, transformationType, current, mode);
        }

        // Touching down always ends the flight, whatever mode the fairy was in.
        if (player.onGround()) {
            setFlight(player, transformationType, current, FlightMode.NONE);
            motion.reset();
            motion.setWasOnGround(true);
            return;
        }

        Vec3 velocity;

        velocity = mode == FlightMode.HOVER
                ? hoverStep(player, jump && motion.jumpHeldTicks() >= HOLD_TICKS)
                : poweredFlightStep(player, motion.velocity());

        motion.setVelocity(velocity);
        player.setDeltaMovement(velocity);
        // Flying never builds up fall damage; the distance starts counting again once flight ends.
        player.resetFallDistance();

        if (!player.level().isClientSide()) {
            double speedCost = velocity.length() * (sprintKeyDown(player) ? SPRINT_FACTOR : 1.0D);
            player.causeFoodExhaustion((float) (EXHAUSTION_PER_BLOCK * speedCost));
        }
        motion.setWasOnGround(player.onGround());
    }

    /** The jump key as seen by this side: the local player's own keys, or the input packet on the server. */
    private static boolean jumpKeyDown(Player player) {
        if (player.level().isClientSide()) {
            return player instanceof LocalPlayer local && local.input.keyPresses.jump();
        }

        return player instanceof ServerPlayer server && server.getLastClientInput().jump();
    }

    private static boolean sprintKeyDown(Player player) {
        if (player.level().isClientSide()) {
            return player instanceof LocalPlayer local && local.input.keyPresses.sprint();
        }

        return player instanceof ServerPlayer server && server.getLastClientInput().sprint();
    }

    private static void setFlight(Player player, AttachmentType<Transformation> type, Transformation current, FlightMode mode) {
        player.setData(type, current.withFlight(mode));
    }

    /**
     * One tick of elytra movement — a copy of what vanilla does inside {@code LivingEntity#travelFallFlying}, minus
     * the check for an item that can glide.
     */
    private static Vec3 poweredFlightStep(Player player, Vec3 movement) {
        Vec3 lookAngle = player.getLookAngle();
        double factor = player.isShiftKeyDown() ? SNEAK_FACTOR : sprintKeyDown(player) ? SPRINT_FACTOR : 1.0D;
        Vec3 target = lookAngle.scale(MAX_POWERED_SPEED * factor);
        return movement.scale(1.0D - POWERED_ACCELERATION).add(target.scale(POWERED_ACCELERATION));
    }

    private static Vec3 hoverStep(Player player, boolean ascending) {
        if (ascending) {
            return new Vec3(0.0D, HOVER_VERTICAL_SPEED, 0.0D);
        }
        if (player.isShiftKeyDown()) {
            return new Vec3(0.0D, -HOVER_VERTICAL_SPEED, 0.0D);
        }
        return Vec3.ZERO;
    }

    private FairyFlightHandler() {
    }
}
