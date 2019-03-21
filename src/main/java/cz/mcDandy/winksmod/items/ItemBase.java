package cz.mcDandy.winksmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {
	public ItemBase(String unlocalizedName, CreativeTabs tab) {
		this(unlocalizedName, tab, 64);
	}

	public ItemBase(String unlocalizedName, CreativeTabs tab, int stackSize) {
		// TODO Auto-generated constructor stub
		setTranslationKey(unlocalizedName);
		setRegistryName(unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

}