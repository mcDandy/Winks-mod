package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Entities.ModEntities;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ClientProxy extends CommonProxy {
    @Override
    public void preinit(FMLPreInitializationEvent event) {
	
		ModEntities.initModels();
	}
    }
