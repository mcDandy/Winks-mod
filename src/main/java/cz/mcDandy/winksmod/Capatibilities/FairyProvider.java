package cz.mcDandy.winksmod.Capatibilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FairyProvider implements ICapabilitySerializable<NBTBase>{



	@CapabilityInject(IFairy.class)
	public static Capability<IFairy> FAIRY_CAP = null;//Cannot be null
	private IFairy instance = FAIRY_CAP.getDefaultInstance();
	
	    public FairyProvider()
	    {
	        instance = new Fairy();
	    }
	@Override

	public boolean hasCapability(Capability<?> capability, EnumFacing facing)

	{

		return capability == FAIRY_CAP;

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

		return FAIRY_CAP.getStorage().writeNBT(FAIRY_CAP, this.instance, null);

	}
  @SuppressWarnings({"ConstantConditions", "SameReturnValue"})

	@Override

	public void deserializeNBT(NBTBase nbt)

	{

		FAIRY_CAP.getStorage().readNBT(FAIRY_CAP, this.instance, null, nbt);

	}

}  