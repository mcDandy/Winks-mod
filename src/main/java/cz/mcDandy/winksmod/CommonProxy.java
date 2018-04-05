package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.capatibilities.FiaryCapatibility;
import cz.mcDandy.winksmod.capatibilities.FiaryCapatibllityStorange;
import cz.mcDandy.winksmod.capatibilities.IFiaryCapatibility;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preinit(FMLPreInitializationEvent event) {

	}

	public void postinit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub

	}
	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub
		CapabilityManager.INSTANCE.register(IFiaryCapatibility.class, new FiaryCapatibllityStorange(), FiaryCapatibility.class);
	}
	
}
