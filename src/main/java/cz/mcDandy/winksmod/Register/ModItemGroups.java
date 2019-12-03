package cz.mcDandy.winksmod.Register;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import cz.mcDandy.winksmod.Main;
import cz.mcDandy.winksmod.Register.ModItemGroup;

class ModItemGroups{
	public static final ModItemGroup MOD_ITEM_GROUP = new ModItemGroup(Main.MODID, () -> new ItemStack(ModItems.fpowder));

}