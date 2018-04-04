package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Items.fpowder;
import cz.mcDandy.winksmod.Items.magicdiamond;
import cz.mcDandy.winksmod.Items.SolWand;
import cz.mcDandy.winksmod.Items.wings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {

	public static Item fpowder = new fpowder("fpowder", ModTabs.fmod);
	public static Item solwand = new SolWand("solwand", ModTabs.fmod, 1);
	public static Item wings = new wings("wings", ModTabs.fmod, 1);
	public static Item magicdiamond = new magicdiamond("magicdiamond", ModTabs.fmod, 1);
	public static Item[] ITEMS = new Item[] { fpowder, solwand, wings, magicdiamond };
}