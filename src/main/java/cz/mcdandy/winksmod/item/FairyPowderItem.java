package cz.mcdandy.winksmod.item;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Eating fairy powder clears every harmful effect the eater is suffering from. */
public class FairyPowderItem extends Item {
    public FairyPowderItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide()) {
            List<MobEffectInstance> effects = ImmutableList.copyOf(entity.getActiveEffects());

            for (MobEffectInstance instance : effects) {
                if (!instance.getEffect().value().isBeneficial()) {
                    entity.removeEffect(instance.getEffect());
                }
            }
        }

        return result;
    }
}
