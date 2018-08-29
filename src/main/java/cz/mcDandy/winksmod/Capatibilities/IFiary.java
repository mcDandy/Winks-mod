package cz.mcDandy.winksmod.Capatibilities;

public interface IFiary

{

	public void removeLVL(float points);

	public void addLVL(float points);

	public void setLVL(float points);

	public float getLVL();

	public boolean IsFiary();
	
	public boolean IsTransformed();
	
	public void setTransformation(boolean transformation);

}