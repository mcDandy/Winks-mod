package cz.mcDandy.winksmod;


import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "winksmod";
	public static final String VERSION = "1.0";

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
	MinecraftForge.EVENT_BUS.register(new Event());
	
	}
	

	@EventHandler
	public void init(FMLInitializationEvent event) {
	//	RenderManager rm = Minecraft.getMinecraft().getRenderManager();
	//	RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
		
		
		// some example code
		System.out.println("DIRT BLOCK >> " + Blocks.DIRT.getUnlocalizedName());
	}
}
