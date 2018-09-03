package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MagicDiamond extends Item {

	ItemStack itemstack;

	public MagicDiamond(String unlocalizedName, CreativeTabs tab, int maxStack) {
		super();
		this.setRegistryName(Main.MODID, unlocalizedName);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(tab);
		this.maxStackSize = maxStack;
		this.setMaxDamage(100);
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