package cz.mcdandy.winksmod.event;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.attachment.FairyEnergy;
import cz.mcdandy.winksmod.attachment.ModAttachments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/** Game (logic) bus handlers. */
@EventBusSubscriber(modid = WinksMod.MODID)
public final class GameEvents {
    /** Fairy energy trickles back, exactly like the old {@code EventForge.OnPlayerTick}. */
    private static final double REGEN_PER_TICK = 0.001D;

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

    private GameEvents() {
    }
}
