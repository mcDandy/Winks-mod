package cz.mcDandy.winksmod.register;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

public class ModPotions extends PotionHelper {
	public static Potion Mystic;
	public static PotionEffect MisticPot;
	//public static PotionType MysticPot;
	// = new MysticP(false, 0);
			public ModPotions() 
{		Mystic = new PotionMystic(32, false, 0).setIconIndex(0, 0).setPotionName("potion.MysticPotion").setRegistryName("MystPotion");
	MisticPot =  new PotionEffect(Mystic,200);
		
		//		MysticPot.setRegistryName("MysticPot");
			
}
}
