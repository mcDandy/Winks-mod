package cz.mcDandy.winksmod.Capatibilities;

import java.util.concurrent.Callable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class Capabilities {
	@CapabilityInject(IFairy.class)
	public static final Capability<IFairy> FIARY_CAP = null;

	public static void init() {
		CapabilityManager.INSTANCE.register(IFairy.class, new FairyStorange(), new Callable<IFairy>() {
			@Override
			public IFairy call() throws Exception {
				return new Fairy();
			}
		});
	}
}