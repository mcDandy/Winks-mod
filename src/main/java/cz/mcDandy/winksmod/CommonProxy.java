package cz.mcDandy.winksmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	//public static final int TARGET_EFFECT_MESSAGE_ID = 34;
	//public static SimpleNetworkWrapper simpleNetworkWrapper;

	public void preinit(FMLPreInitializationEvent event) {
//		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("MBEchannel");
//		simpleNetworkWrapper.registerMessage(WinksPacketHandler.class, WinksMessage.class, TARGET_EFFECT_MESSAGE_ID,
//				Side.SERVER);
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
