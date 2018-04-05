package cz.mcDandy.winksmod.capatibilities;

import cz.mcDandy.winksmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler

{

	public static final ResourceLocation FIARYLVL_CAP = new ResourceLocation(Main.MODID, "fiary");

	@SubscribeEvent

	public void attachCapability(AttachCapabilitiesEvent<Entity> event)

	{

		if (!(event.getObject() instanceof EntityPlayer))
			return;

		event.addCapability(FIARYLVL_CAP, new FiaryLVLProvider());
	}
}