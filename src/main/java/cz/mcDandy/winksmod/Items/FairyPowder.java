package cz.mcDandy.winksmod.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;

public class FairyPowder extends Item {
    public FairyPowder(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        ItemStack itemStack = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote) {
            Collection<EffectInstance> effects = entityLiving.getActivePotionEffects();
            Collection<EffectInstance> effects2 = new ArrayList<>();
            for (EffectInstance e : effects) {
                if (!e.getPotion().isBeneficial()) effects2.add(e);
            }
            for (EffectInstance e : effects2) {
                entityLiving.removePotionEffect(e.getPotion());
            }
        }
        return itemStack;
    }
}
