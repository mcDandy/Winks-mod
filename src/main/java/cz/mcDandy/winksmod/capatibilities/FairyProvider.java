package cz.mcDandy.winksmod.Capatibilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class FairyProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(IFairy.class)
	public static final Capability<IFairy> FAIRY_CAP = null;
	private IFairy instance = new Fairy();

	public FairyProvider() {
		instance = new Fairy();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == FAIRY_CAP;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return hasCapability(capability, facing) ? (T) instance : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return FAIRY_CAP.getStorage().writeNBT(FAIRY_CAP, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		FAIRY_CAP.getStorage().readNBT(FAIRY_CAP, this.instance, null, nbt);

	}
}