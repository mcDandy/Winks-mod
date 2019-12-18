package cz.mcDandy.winksmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "winksmod";
	public static Main instance;
	public static final Logger LOGGER = LogManager.getLogger(MODID);
}