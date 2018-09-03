package cz.mcDandy.winksmod.Capatibilities;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class Capabilities
{
    @CapabilityInject(IFiary.class)
    public static Capability<IFiary> FIARY_CAP = null;

    public static void init()
    {
        CapabilityManager.INSTANCE.register(IFiary.class, new FiaryStorange(), new Callable<IFiary>()
        {
            @Override
            public IFiary call() throws Exception
            {
                return new Fiary();
            }
        });
    }
}