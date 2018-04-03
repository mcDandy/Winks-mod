package cz.mcDandy.winksmod.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class fpowder extends ItemFoodBase {

	public fpowder(String unlocalizedName, CreativeTabs tab) {
		super(unlocalizedName, tab, 64, 0, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		PotionEffect[] pf = new PotionEffect[player.getActivePotionEffects().size()];
		int a = 0;
		for (PotionEffect eff : player.getActivePotionEffects()) {
			if (eff.getPotion().isBadEffect()) {
				pf[a] = eff;
				a++;
			}
		}
		for (int s = 0; s < a; s++) {
			player.removeActivePotionEffect(pf[s].getPotion());
		}
	}
}
