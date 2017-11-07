package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Items.PotionMystic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.entity.player.EntityPlayerMP;;
public class ModPotions extends PotionHelper {
	public static EntityPlayerMP player;
	public static Potion Mystic;
	public static PotionEffect MisticPot;
	//public static PotionType MysticPot;
	// = new MysticP(false, 0);
	private NBTTagCompound nbt=player.getEntityData();;
			public ModPotions() 
{
		Mystic = new PotionMystic(32, false, 0).setIconIndex(0, 0).setPotionName("potion.MysticPotioQn").setRegistryName("MystPotion");
		MisticPot =  new PotionEffect(Mystic,200);
		MisticPot.writeCustomPotionEffectToNBT(nbt);
			
}
}
