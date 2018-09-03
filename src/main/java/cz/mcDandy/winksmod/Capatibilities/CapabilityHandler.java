package cz.mcDandy.winksmod.Capatibilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler
{
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        //Attach the capability to all players without it
        if(event.getObject() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getObject();
            if(!player.hasCapability(FiaryProvider.FIARY_CAP, null))
            {
                IFiary fiary = FiaryProvider.FIARY_CAP.getDefaultInstance();
                event.addCapability(fiary.getKey(), fiary.getProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        //Send client capability details to the client on login
        if(event.player instanceof EntityPlayerMP)
        {
            IFiary fiary = event.player.getCapability(FiaryProvider.FIARY_CAP, null);
            if(fiary != null)
                fiary.dataChanged((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public static void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        //Copy capability on player death to new player
        if(event.isWasDeath() && event.getEntityPlayer() instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
            IFiary oldFiary = event.getOriginal().getCapability(FiaryProvider.FIARY_CAP, null);
            IFiary newFiary = player.getCapability(FiaryProvider.FIARY_CAP, null);
            if(oldFiary == null || newFiary == null) return;
            newFiary.deserializeNBT(oldFiary.serializeNBT());
            newFiary.dataChanged(player);
        }
    }
}