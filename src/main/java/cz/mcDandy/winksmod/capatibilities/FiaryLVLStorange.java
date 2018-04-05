package cz.mcDandy.winksmod.capatibilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FiaryLVLStorange implements IStorage<IFiaryLVL>

{

	@Override
	public NBTBase writeNBT(Capability<IFiaryLVL> capability, IFiaryLVL instance, EnumFacing side)

	{

		return new NBTTagFloat(instance.getFiaryLVL());

	}

	@Override

	public void readNBT(Capability<IFiaryLVL> capability, IFiaryLVL instance, EnumFacing side, NBTBase nbt)

	{

		instance.set(((NBTPrimitive) nbt).getFloat());

	}

}