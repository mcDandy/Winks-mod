package cz.mcDandy.winksmod.capatibilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class FiaryCapatibilityProvider implements ICapabilitySerializable<NBTBase>

{

 @CapabilityInject(IFiaryCapatibility.class)

 public static final Capability<IFiaryCapatibility> FIARY_CAP = null;

 
 private IFiaryCapatibility instance = FIARY_CAP.getDefaultInstance();

 
 @Override

 public boolean hasCapability(Capability<?> capability, EnumFacing facing)

 {

 return capability == FIARY_CAP;

 }

 
 @Override

 public <T> T getCapability(Capability<T> capability, EnumFacing facing)

 {

 return capability == FIARY_CAP ? FIARY_CAP.<T> cast(this.instance) : null;

 }

 
 @Override

 public NBTBase serializeNBT()

 {

 return FIARY_CAP.getStorage().writeNBT(FIARY_CAP, this.instance, null);

 }

 
 @Override

 public void deserializeNBT(NBTBase nbt)

 {

 FIARY_CAP.getStorage().readNBT(FIARY_CAP, this.instance, null, nbt);

 }

}