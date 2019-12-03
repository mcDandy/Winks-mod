package cz.mcDandy.winksmod.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MagicDiamond extends Item {

	ItemStack itemstack;

	public MagicDiamond(Properties properities) {
		super(properities);

		properities.maxDamage(100);
		properities.maxStackSize(1);
		// this.setDamage(itemstack, 99);

		showDurabilityBar(itemstack);
		isDamageable();
		// getDurabilityForDisplay(itemstack);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
}
