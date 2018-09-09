package cz.mcDandy.winksmod.Capatibilities;

import cz.mcDandy.winksmod.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Fairy implements IFairy

{
private double LVL=0;
private static ResourceLocation RL = new ResourceLocation(Main.MODID, "Fairy");
	private boolean transformation=false;

	
	public Fairy(double LVL, boolean transformed) {
		this.LVL = LVL;
		this.transformation = transformed;
	}

	public Fairy() {
		// TODO Auto-generated constructor stub
	}



	public void removeLVL(double points)

	{

		this.LVL -= points;

		if (this.LVL < 0.0F)
			this.LVL = 0.0F;

	}

	public void addLVL(double points)

	{

		this.LVL += points;

	}

	public void setLVL(double points)

	{

		this.LVL = points;

	}

	public double getLVL()

	{

		return this.LVL;

	}

	public boolean IsFiary() {
		return this.LVL > 0;
	}
	public boolean IsTransformed() {
		return transformation;
	}
	public void setTransformation(boolean transformation) {
		 this.transformation= transformation;
	}
	 @Override
	    public ResourceLocation getKey()
	    {
	        return RL;
	    }

	    @Override
	    public ICapabilityProvider getProvider()
	    {
	        return new FairyProvider();
	    }

	    @Override
	    public void dataChanged(EntityPlayerMP player)
	    {
	        //Sync data to the client
	        Main.NETWORK.sendTo( new MessageFairy(LVL,transformation), player);
	    }

	    @Override
	    public NBTTagCompound serializeNBT()
	    {
	        NBTTagCompound tag = new NBTTagCompound();
	        tag.setDouble("LVL", LVL);
	        tag.setBoolean("Transformation", transformation);
	        return tag;
	    }

	    @Override
	    public void deserializeNBT(NBTTagCompound nbt)
	    {
	        LVL = nbt.getDouble("LVL");
	        transformation = nbt.getBoolean("Transformation");
	}


	}

