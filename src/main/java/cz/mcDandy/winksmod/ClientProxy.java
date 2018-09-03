package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Entities.ModEntities;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preinit(FMLPreInitializationEvent event) {
	//	CommonProxy.simpleNetworkWrapper.registerMessage(WinksPacketHandlerClient.class, TransformMSG.class,
	//			CommonProxy.TARGET_EFFECT_MESSAGE_ID, Side.CLIENT);
	}

	@Override
	public void postinit(FMLPostInitializationEvent event) {
		ModEntities.initModels();
	}
}
