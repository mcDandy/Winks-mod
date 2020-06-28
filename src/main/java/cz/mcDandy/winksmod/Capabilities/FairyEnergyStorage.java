package cz.mcDandy.winksmod.Capabilities;

import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class FairyEnergyStorage implements Capability.IStorage<IFairyEnergy> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IFairyEnergy> capability, IFairyEnergy instance, Direction side) {
        return DoubleNBT.valueOf(instance.getAmount());
    }

    @Override
    public void readNBT(Capability<IFairyEnergy> capability, IFairyEnergy instance, Direction side, INBT nbt) {
        if (!(instance instanceof FairyEnergy))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        instance.setAmount(((DoubleNBT) nbt).getInt());
    }
}