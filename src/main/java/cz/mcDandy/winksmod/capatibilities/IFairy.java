package cz.mcDandy.winksmod.capatibilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IFairy

{

	public void removeLVL(double points);

	public void addLVL(double points);

	public void setLVL(double d);

	public double getLVL();

	public boolean IsFiary();

	public boolean IsTransformed();

	public void setTransformation(boolean transformation);

	public ICapabilityProvider getProvider();

	public void dataChanged(EntityPlayerMP player);

	public ResourceLocation getKey();

	NBTTagCompound serializeNBT();

	void deserializeNBT(NBTTagCompound nbt);

}