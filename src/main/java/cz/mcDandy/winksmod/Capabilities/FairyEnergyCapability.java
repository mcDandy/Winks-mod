package cz.mcDandy.winksmod.Capabilities;

import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FairyEnergyCapability implements ICapabilitySerializable<DoubleNBT> {

    @CapabilityInject(IFairyEnergy.class)
    public static final Capability<IFairyEnergy> FAIRY_ENERGY_CAPABILITY = null;
    private LazyOptional<IFairyEnergy> instance = LazyOptional.of(FAIRY_ENERGY_CAPABILITY::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IFairyEnergy.class, new FairyEnergyStorage(), FairyEnergy::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return FAIRY_ENERGY_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public DoubleNBT serializeNBT() {
        return (DoubleNBT) FAIRY_ENERGY_CAPABILITY.getStorage().writeNBT(FAIRY_ENERGY_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(DoubleNBT nbt) {
        FAIRY_ENERGY_CAPABILITY.getStorage().readNBT(FAIRY_ENERGY_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
