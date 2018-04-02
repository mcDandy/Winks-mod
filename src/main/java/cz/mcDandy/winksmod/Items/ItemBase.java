package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {
	public ItemBase(String unlocalizedName, CreativeTabs tab) {
		this(unlocalizedName, tab, 64);
	}

	public ItemBase(String unlocalizedName, CreativeTabs tab, int stackSize) {
		// TODO Auto-generated constructor stub
		setUnlocalizedName(unlocalizedName);
		setRegistryName(Main.MODID+":"+unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

}