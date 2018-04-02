package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Entities.ModEntities;
import cz.mcDandy.winksmod.register.ModBlocks;
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
	}
	}

