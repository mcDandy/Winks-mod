package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Items.fpowder;
import cz.mcDandy.winksmod.Items.SolWand;
import cz.mcDandy.winksmod.Items.wings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {

	public static Item fpowder = new fpowder("fpowder", CreativeTabs.MATERIALS);
	public static Item solwand = new SolWand("solwand", CreativeTabs.COMBAT, 1);
	public static Item wings = new wings("wings", CreativeTabs.TRANSPORTATION, 1);
	// public static Item MysticPotion = new
	// mPotion("MysticPotion",CreativeTabs.BREWING,1);
	public static Item magicdiamond = new magicdiamond("magicdiamond", CreativeTabs.MISC, 1);

}