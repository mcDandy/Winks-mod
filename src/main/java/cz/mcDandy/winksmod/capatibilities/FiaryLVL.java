package cz.mcDandy.winksmod.capatibilities;

public class FiaryLVL implements IFiaryLVL

{

	private float fiaryLVL = 0.0F;
	private boolean transformation=false;
	

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