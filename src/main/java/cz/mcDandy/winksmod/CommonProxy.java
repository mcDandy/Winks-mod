package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.capatibilities.FiaryLVL;
import cz.mcDandy.winksmod.capatibilities.FiaryLVLStorange;
import cz.mcDandy.winksmod.capatibilities.IFiaryLVL;
import cz.mcDandy.winksmod.networking.WinksMessage;
import cz.mcDandy.winksmod.networking.WinksPacketHandler;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	public static final int TARGET_EFFECT_MESSAGE_ID = 34;
	public static SimpleNetworkWrapper simpleNetworkWrapper;

	public void preinit(FMLPreInitializationEvent event) {
		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("MBEchannel");
		simpleNetworkWrapper.registerMessage(WinksPacketHandler.class, WinksMessage.class, TARGET_EFFECT_MESSAGE_ID,
				Side.SERVER);
	}

	public void postinit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub
		CapabilityManager.INSTANCE.register(IFiaryLVL.class, new FiaryLVLStorange(), FiaryLVL.class);
	}

}
