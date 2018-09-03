package cz.mcDandy.winksmod.Capatibilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FiaryStorange implements IStorage<IFiary>

{

	@Override
	public NBTBase writeNBT(Capability<IFiary> capability, IFiary instance, EnumFacing side)

	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setDouble("LVL", instance.getLVL());
		nbt.setBoolean("Trans", instance.IsTransformed());
		return nbt;
	}
	@Override
	public void readNBT(Capability<IFiary> capability, IFiary instance, EnumFacing side, NBTBase nbt)
	{
		instance.setLVL(((NBTTagCompound)nbt).getDouble("LVL"));
		instance.setTransformation(((NBTTagCompound)nbt).getBoolean("Trans"));
	}

}