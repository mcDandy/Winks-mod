package cz.mcDandy.winksmod.Capabilities;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AccessibleTransformationsCapability implements ICapabilitySerializable<IntNBT> {

    @CapabilityInject(IAccessibleTransformations.class)
    public static final Capability<IAccessibleTransformations> ACCESSIBLE_TRANSFORMATIONS_CAPABILITY = null;
    private LazyOptional<IAccessibleTransformations> instance = LazyOptional.of(() -> ACCESSIBLE_TRANSFORMATIONS_CAPABILITY.getDefaultInstance());

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IAccessibleTransformations.class, new AccessibleTransformationsStorage(), AccessibleTransformations::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return ACCESSIBLE_TRANSFORMATIONS_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) ACCESSIBLE_TRANSFORMATIONS_CAPABILITY.getStorage().writeNBT(ACCESSIBLE_TRANSFORMATIONS_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        ACCESSIBLE_TRANSFORMATIONS_CAPABILITY.getStorage().readNBT(ACCESSIBLE_TRANSFORMATIONS_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
