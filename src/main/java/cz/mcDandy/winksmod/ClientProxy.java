package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Entities.ModEntities;
import cz.mcDandy.winksmod.register.Blocks;
import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preinit(FMLPreInitializationEvent event) {


	}
	@Override
	public void postinit(FMLPostInitializationEvent event) {
		ModEntities.initModels();
		for (Block block : Blocks.BLOCKS) {
		
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
					new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}

		for (Item item : ModItems.ITEMS) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
	}

