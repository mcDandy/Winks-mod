package cz.mcDandy.winksmod.capatibilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FiaryCapatibllityStorange implements IStorage<IFiaryCapatibility>

{

	@Override

	public NBTBase writeNBT(Capability<IFiaryCapatibility> capability, IFiaryCapatibility instance, EnumFacing side)

	{

		return new NBTTagFloat(instance.getFiaryLVL());

	}

	@Override

	public void readNBT(Capability<IFiaryCapatibility> capability, IFiaryCapatibility instance, EnumFacing side, NBTBase nbt)

	{

		instance.set(((NBTPrimitive) nbt).getFloat());

	}

}