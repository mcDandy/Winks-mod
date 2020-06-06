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

public class AccessableTransformationsCapability implements ICapabilitySerializable<IntNBT> {

    @CapabilityInject(IAccessableTransformations.class)
    public static final Capability<IAccessableTransformations> ACCESSABLE_TRANSFORMATIONS_CAPABILITY = null;
    private LazyOptional<IAccessableTransformations> instance = LazyOptional.of(ACCESSABLE_TRANSFORMATIONS_CAPABILITY::getDefaultInstance);

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IAccessableTransformations.class, new AccessableTransformationsStorage(), AccessableTransformations::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return ACCESSABLE_TRANSFORMATIONS_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) ACCESSABLE_TRANSFORMATIONS_CAPABILITY.getStorage().writeNBT(ACCESSABLE_TRANSFORMATIONS_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        ACCESSABLE_TRANSFORMATIONS_CAPABILITY.getStorage().readNBT(ACCESSABLE_TRANSFORMATIONS_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
