package cz.mcDandy.winksmod.capatibilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FiaryLVLProvider implements ICapabilitySerializable<NBTBase>

{

	@CapabilityInject(IFiaryLVL.class)

	public static final Capability<IFiaryLVL> FIARYLVL_CAP = null;

	private IFiaryLVL instance = FIARYLVL_CAP.getDefaultInstance();

	@Override

	public boolean hasCapability(Capability<?> capability, EnumFacing facing)

	{

		return capability == FIARYLVL_CAP;

	}

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return hasCapability(capability, facing) ? (T) FIARYLVL_CAP : null;
}

	@Override

	public NBTBase serializeNBT()

	{

		return FIARYLVL_CAP.getStorage().writeNBT(FIARYLVL_CAP, this.instance, null);

	}

	@Override

	public void deserializeNBT(NBTBase nbt)

	{

		FIARYLVL_CAP.getStorage().readNBT(FIARYLVL_CAP, this.instance, null, nbt);

	}

}