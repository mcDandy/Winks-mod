package cz.mcDandy.winksmod.Capatibilities;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import cz.mcDandy.winksmod.Util;
import java.util.concurrent.Callable;


public class Capabilities
{
    @CapabilityInject(IFairy.class)
    public static Capability<IFairy> FIARY_CAP = Util.Null();

    public static void init()
    {
        CapabilityManager.INSTANCE.register(IFairy.class, new FairyStorange(), new Callable<IFairy>()
        {
            @Override
            public IFairy call() throws Exception
            {
                return new Fairy();
            }
        });
    }
}