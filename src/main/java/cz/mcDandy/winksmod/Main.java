package cz.mcDandy.winksmod;

import java.util.logging.Logger;

import cz.mcDandy.winksmod.capatibilities.MessageFairy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static Logger logger;
	public static final String MODID = "winksmod";
	public static final String VERSION = "1.0";
	public static Main instance;
	@SidedProxy(clientSide = "cz.mcDandy.winksmod.ClientProxy", serverSide = "cz.mcDndy.winksmod.ServerProxy")
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper NETWORK;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		// RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
		proxy.init(event);

	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		logger = Logger.getLogger(MODID);
		proxy.preinit(event);

		NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		// Packet used to sync the PlayerNumber capability from the server to the client
		NETWORK.registerMessage(MessageFairy.Handler.class, MessageFairy.class, 0, Side.CLIENT);

	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}
}
