package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Items.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;

public class ModItems {

	public static Item fpowder = new fpowder("fpowder", CreativeTabs.MATERIALS);
	public static Item solwand = new solwand("solwand", CreativeTabs.COMBAT, 1);
	public static Item wings = new wings("wings",CreativeTabs.TRANSPORTATION,1);
	//public static Item MysticPotion = new mPotion("MysticPotion",CreativeTabs.BREWING,1);
	public static Item magicdiamond = new magicdiamond("magicdiamond",CreativeTabs.MISC,1);
}