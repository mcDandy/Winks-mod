package cz.mcDandy.winksmod.register;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class Register {
	public static void registerAll(FMLPreInitializationEvent pre) {
		registerBlocks(pre, Blocks.fp_block);
		registerItems(pre, ModItems.fpowder, ModItems.solwand, ModItems.wings, ModItems.MysticPotion, ModItems.magicdiamond);
        
		registerPotions(pre, ModPotions.Mystic);
	}
	
	private static void registerPotions(FMLPreInitializationEvent pre, Potion... potions) {
		// TODO Auto-generated method stub
		for (Potion potion : potions) {
			/*	//final ItemBlock itemblock = new ItemBlock(block);
			if (pre.getSide() == Side.CLIENT) {
				GameRegistry.register(potion);
				//GameRegistry.register(itemblock, block.getRegistryName());
			}*/
	if(potion!=null) {GameRegistry.register(potion);	
		}
	}
		
	}

	public static void registerBlocks(FMLPreInitializationEvent pre, Block... blocks) {
		for (Block block : blocks) {
			final ItemBlock itemblock = new ItemBlock(block);
			if (pre.getSide() == Side.CLIENT) {
				GameRegistry.register(block);
				GameRegistry.register(itemblock, block.getRegistryName());
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
						new ModelResourceLocation(block.getRegistryName(), "inventory"));
			}
		}
	}

	public static void registerItems(FMLPreInitializationEvent pre, Item... items) {
		for (Item item : items) {

			if (pre.getSide() == Side.CLIENT) {
				GameRegistry.register(item);

				ModelLoader.setCustomModelResourceLocation(item, 0,
						new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		}

	}
}