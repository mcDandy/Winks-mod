package cz.mcDandy.winksmod.Capatibilities;

public class Fiary implements IFiary

{
private float LVL;
	private boolean transformation;
//	private Fiary fiary= new Fiary(0.0D,false);
	public Fiary(float LVL, boolean transformed) {
		this.LVL = LVL;
		this.transformation = transformed;
	}

	public void removeLVL(float points)

	{

		this.LVL -= points;

		if (this.LVL < 0.0F)
			this.LVL = 0.0F;

	}

	public void addLVL(float points)

	{

		this.LVL += points;

	}

	public void setLVL(float points)

	{

		this.LVL = points;

	}

	public float getLVL()

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

	}

