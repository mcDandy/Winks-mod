package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.register.Crafting;
import cz.mcDandy.winksmod.register.Register;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import cz.mcDandy.winksmod.Entities.FiaryGeneric;
@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "winksmod";
	public static final String VERSION = "1.0";

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
	
		Register.registerAll(event);
	}
	

	@EventHandler
	public void init(FMLInitializationEvent event) {
	//	RenderManager rm = Minecraft.getMinecraft().getRenderManager();
	//	RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
		Crafting.Register(event);
		MinecraftForge.EVENT_BUS.register(new Event());
		// some example code
		System.out.println("DIRT BLOCK >> " + Blocks.DIRT.getUnlocalizedName());
	}
}
