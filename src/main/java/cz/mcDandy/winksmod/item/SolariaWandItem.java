package cz.mcdandy.winksmod.item;

import cz.mcdandy.winksmod.attachment.FairyEnergy;
import cz.mcdandy.winksmod.attachment.ModAttachments;
import cz.mcdandy.winksmod.entity.SunSpellEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;

/**
 * Holding the wand charges it, releasing fires a {@link SunSpellEntity} straight ahead and burns one point of
 * fairy energy.
 */
public class SolariaWandItem extends Item {
    /** Effectively "until the player lets go", like the old 7200 tick use duration. */
    private static final int MAX_USE_DURATION = 7200;

    public SolariaWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return MAX_USE_DURATION;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTime) {
        if (!(level instanceof ServerLevel serverLevel) || !(entity instanceof Player player)) {
            return false;
        }

        AttachmentType<FairyEnergy> type = ModAttachments.FAIRY_ENERGY.get();
        FairyEnergy energy = player.getData(type);

        if (!energy.isNotEmpty()) {
            return false;
        }

        SunSpellEntity.fire(serverLevel, entity);
        energy.addOrSubtractAmount(-1.0D);
        // Write the attachment back so the new value reaches the client as well.
        player.setData(type, energy);
        return true;
    }
}
