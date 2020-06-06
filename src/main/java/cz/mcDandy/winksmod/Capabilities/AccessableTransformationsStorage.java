package cz.mcDandy.winksmod.Capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class AccessableTransformationsStorage implements Capability.IStorage<IAccessableTransformations> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IAccessableTransformations> capability, IAccessableTransformations instance, Direction side) {
        return IntNBT.valueOf(instance.getRawData());
    }

    @Override
    public void readNBT(Capability<IAccessableTransformations> capability, IAccessableTransformations instance, Direction side, INBT nbt) {
        if (!(instance instanceof FairyEnergy))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        instance.setRawData(((IntNBT)nbt).getInt());
    }
}