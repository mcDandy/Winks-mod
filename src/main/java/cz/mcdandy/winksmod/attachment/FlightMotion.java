package cz.mcdandy.winksmod.attachment;

import net.minecraft.world.phys.Vec3;

/**
 * Per side bookkeeping for the fairy movement code: the velocity we integrate ourselves plus the two bits of key
 * state needed to read the jump key as a tap.
 *
 * <p>This attachment is neither saved nor synced on purpose. Every side integrates its own copy — the client of the
 * player being controlled because that is who actually moves in vanilla, the server so it can keep score (hunger) and
 * simulate everyone else.</p>
 */
public final class FlightMotion {
    private Vec3 velocity = Vec3.ZERO;
    private boolean jumpDown;
    private boolean wasOnGround;
    private int jumpHeldTicks;

    /** The velocity the entity should be moved by on the next tick. */
    public Vec3 velocity() {
        return velocity;
    }

    public void setVelocity(Vec3 velocity) {
        this.velocity = velocity;
    }

    /** Whether the jump key was already down last tick, so a change to {@code true} is a tap. */
    public boolean jumpDown() {
        return jumpDown;
    }

    public void setJumpDown(boolean jumpDown) {
        this.jumpDown = jumpDown;
    }

    public int jumpHeldTicks() {
        return jumpHeldTicks;
    }

    public void setJumpHeldTicks(int jumpHeldTicks) {
        this.jumpHeldTicks = jumpHeldTicks;
    }

    public boolean wasOnGround() {
        return wasOnGround;
    }

    public void setWasOnGround(boolean wasOnGround) {
        this.wasOnGround = wasOnGround;
    }

    /** Drops stored flight velocity; the key state is kept to avoid a held key becoming a phantom tap. */
    public void reset() {
        velocity = Vec3.ZERO;
        jumpHeldTicks = 0;
    }

    @Override
    public String toString() {
        return "FlightMotion[" + velocity + "]";
    }
}
