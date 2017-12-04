package cz.mcDandy.winksmod.register;

import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Crafting {
	public static void Register(FMLInitializationEvent event) {
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.fpowder, 1), new ItemStack(Items.DYE, 1, 5),
				Items.ENDER_EYE, Items.REDSTONE, Items.GLOWSTONE_DUST);// 1 fiary powder = purple die, redstone, glowstone and eye of ender
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.magicdiamond,1,99), new Object[] {" A ","AXA"," A ",'X',Items.DIAMOND,'A',ModItems.fpowder}); 
		
		GameRegistry.addShapedRecipe(new ItemStack(ModItems.solwand,1,ModItems.magicdiamond.getDamage(new ItemStack(ModItems.magicdiamond,-1))), new Object[] {"X  "," A ","  A",'X',ModItems.magicdiamond,'A',ModItems.fpowder}); 
	
	}
}