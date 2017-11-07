package cz.mcDandy.winksmod.Items;

import net.minecraft.potion.Potion;

public class PotionMystic extends Potion {

public PotionMystic(int par1, boolean par2, int par3) {
super(par2, par3);
}
    
	public Potion setIconIndex(int par1, int par2) {
	super.setIconIndex(par1, par2);
	this.setPotionName("PotionMystic");
	return this;
}
}