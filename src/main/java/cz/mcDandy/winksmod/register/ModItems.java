package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Items.Fpowder;
import cz.mcDandy.winksmod.Items.MagicDiamond;
import cz.mcDandy.winksmod.Items.SolWand;
import cz.mcDandy.winksmod.Items.Wings;
import net.minecraft.item.Item;

public class ModItems {

	public static Item fpowder = new Fpowder("Fpowder", ModTabs.fmod);
	public static Item solwand = new SolWand("solwand", ModTabs.fmod, 1);
	public static Item wings = new Wings("Wings", ModTabs.fmod, 1);
	public static Item magicdiamond = new MagicDiamond("MagicDiamond", ModTabs.fmod, 1);
	public static Item[] ITEMS = new Item[] { fpowder, solwand, wings, magicdiamond };
}