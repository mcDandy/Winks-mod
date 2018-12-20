package cz.mcDandy.winksmod.Capatibilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class FairyStorange implements IStorage<IFairy>

{
	@Override
	public NBTBase writeNBT(Capability<IFairy> capability, IFairy instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setDouble("LVL", instance.getLVL());
		nbt.setBoolean("Trans", instance.IsTransformed());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IFairy> capability, IFairy instance, EnumFacing side, NBTBase nbt) {
		instance.setLVL(((NBTTagCompound) nbt).getDouble("LVL"));
		instance.setTransformation(((NBTTagCompound) nbt).getBoolean("Trans"));
	}
}