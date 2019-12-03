package cz.mcDandy.winksmod.Register;

import cz.mcDandy.winksmod.Items.Fpowder;
import cz.mcDandy.winksmod.Items.MagicDiamond;
import cz.mcDandy.winksmod.Items.SolWand;
import cz.mcDandy.winksmod.Items.Wings;
import net.minecraft.item.Item;

public class ModItems {

	public static Item fpowder = new Fpowder("Fpowder", ModItemGroups.fmod);
	public static Item solwand = new SolWand("solwand", new Properties());
	public static Item wings = new Wings("Wings", ModItemGroups.fmod, 1);
	public static Item magicdiamond = new MagicDiamond("MagicDiamond", ModItemGroups.fmod, 1);
	public static Item[] ITEMS = new Item[] { fpowder, solwand, wings, magicdiamond };
}