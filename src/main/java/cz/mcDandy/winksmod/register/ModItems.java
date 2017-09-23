package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Items.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {

	public static Item fpowder = new fpowder("fpowder", CreativeTabs.MATERIALS);
	public static Item solWand = new solWand("solWand", CreativeTabs.COMBAT, 1);
	public static Item wings = new wings("wings",CreativeTabs.TRANSPORTATION,1);
}