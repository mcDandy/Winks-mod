package cz.mcDandy.winksmod;

import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "winksmod";
	public static final String VERSION = "1.0";
	public static Main instance;
	@SidedProxy(clientSide = "cz.mcDandy.winksmod.ClientProxy", serverSide = "cz.mcDndy.winksmod.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		// RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);

	}

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		//MinecraftForge.EVENT_BUS.register(new Event());
		proxy.preinit(event);
	}

	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}
}
