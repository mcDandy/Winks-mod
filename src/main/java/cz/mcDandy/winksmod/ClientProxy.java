package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.Entities.ModEntities;
import cz.mcDandy.winksmod.networking.TransformMSG;
import cz.mcDandy.winksmod.networking.WinksPacketHandlerClient;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

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
