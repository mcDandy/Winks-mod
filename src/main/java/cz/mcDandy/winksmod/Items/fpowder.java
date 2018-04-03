package cz.mcDandy.winksmod.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class fpowder extends ItemFoodBase {

	public fpowder(String unlocalizedName, CreativeTabs tab) {
		super(unlocalizedName, tab,64,0,true);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) 
	{
		for(PotionEffect eff:player.getActivePotionEffects()) {
			if(eff.getPotion().isBadEffect()) 
			{
				player.removeActivePotionEffect(eff.getPotion());
			}
			}
	}
}
