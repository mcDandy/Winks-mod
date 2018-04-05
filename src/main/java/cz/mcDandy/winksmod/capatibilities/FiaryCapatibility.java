package cz.mcDandy.winksmod.capatibilities;

public class FiaryCapatibility implements IFiaryCapatibility

{

	private float fiaryLVL = 0.0F;
	

	public void remove(float points)

	{

		this.fiaryLVL -= points;

		if (this.fiaryLVL < 0.0F)
			this.fiaryLVL = 0.0F;

	}

	public void add(float points)

	{

		this.fiaryLVL += points;

	}

	public void set(float points)

	{

		this.fiaryLVL = points;

	}

	public float getFiaryLVL()

	{

		return this.fiaryLVL;

	}

	public boolean IsFiary() {
		return this.fiaryLVL > 0;
	}


}