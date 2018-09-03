package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Capatibilities.Capabilities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {


	public void preinit(FMLPreInitializationEvent event) {
Capabilities.init();
	}

	public void postinit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub

		}

}
