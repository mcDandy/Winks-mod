package cz.mcdandy.winksmod.event;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.attachment.FairyEnergy;
import cz.mcdandy.winksmod.attachment.ModAttachments;
import cz.mcdandy.winksmod.worldgen.ModDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Game (logic) bus handlers. */
@EventBusSubscriber(modid = WinksMod.MODID)
public final class GameEvents {
    /** Fairy energy trickles back, exactly like the old {@code EventForge.OnPlayerTick}. */
    private static final double REGEN_PER_TICK = 0.001D;

    /**
     * How much of the freeze meter water in Omega adds per tick. Vanilla drains the meter by two every tick it does
     * not see powder snow, so three gives the same net +1/tick a powder snow block pushes.
     */
    private static final int FREEZE_PER_TICK = 3;

    /**
     * How far past {@code getTicksRequiredToFreeze()} the meter is allowed to fill. Required: vanilla applies freeze
     * damage inside its own tick step, right after draining two ticks worth of meter, so stopping exactly at the
     * threshold would leave it permanently one step short and never hurt anything.
     */
    private static final int FREEZE_HEADROOM = 20;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        AttachmentType<FairyEnergy> type = ModAttachments.FAIRY_ENERGY.get();
        FairyEnergy energy = player.getData(type);
        double before = Math.floor(energy.amount());

        energy.addOrSubtractAmount(REGEN_PER_TICK);

        // Only push a new value to the client once a whole point was gained, not twenty times a second.
        if (Math.floor(energy.amount()) != before) {
            player.setData(type, energy);
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity) || !(entity.level() instanceof ServerLevel level)) {
            return;
        }

        if (level.dimension() != ModDimensions.OMEGA || !entity.canFreeze() || !entity.isInWater()) {
            return;
        }

        int ceiling = entity.getTicksRequiredToFreeze() + FREEZE_HEADROOM;
        entity.setTicksFrozen(Math.min(ceiling, entity.getTicksFrozen() + FREEZE_PER_TICK));
    }

    private GameEvents() {
    }
}
