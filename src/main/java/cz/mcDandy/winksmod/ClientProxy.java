package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.entities.ModEntities;
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
