package cz.mcDandy.winksmod.Capatibilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FiaryProvider implements ICapabilitySerializable<NBTBase>{



	@CapabilityInject(IFiary.class)
	public static Capability<IFiary> FIARY_CAP = null;//Cannot be null
	private IFiary instance = FIARY_CAP.getDefaultInstance();
	
	    public FiaryProvider()
	    {
	        instance = new Fiary();
	    }
	@Override

	public boolean hasCapability(Capability<?> capability, EnumFacing facing)

	{

		return capability == FIARY_CAP;

	}

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
    	 return hasCapability(capability, facing) ? (T) instance : null;
}

	@Override

	public NBTBase serializeNBT()

	{

		return FIARY_CAP.getStorage().writeNBT(FIARY_CAP, this.instance, null);

	}
  @SuppressWarnings({"ConstantConditions", "SameReturnValue"})

	@Override

	public void deserializeNBT(NBTBase nbt)

	{

		FIARY_CAP.getStorage().readNBT(FIARY_CAP, this.instance, null, nbt);

	}

}  