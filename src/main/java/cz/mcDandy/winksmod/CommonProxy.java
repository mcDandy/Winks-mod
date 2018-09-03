package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Capatibilities.Capabilities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	//public static final int TARGET_EFFECT_MESSAGE_ID = 34;
	//public static SimpleNetworkWrapper simpleNetworkWrapper;

	public void preinit(FMLPreInitializationEvent event) {
Capabilities.init();
	}

	public void postinit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub
		//CapabilityManager.INSTANCE.register(IFiary.class, new FiaryStorange(), Fiary.class);
		
	//	MinecraftForge.EVENT_BUS.register(new Event());
		}

}
