package cz.mcDandy.winksmod.Items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.datafixers.types.templates.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.world.World;

public class Fpowder extends Item {

	public Fpowder(String unlocalizedName, Properties properities) {
		super(properities);
		properities.food(new Food.Builder().hunger(1).saturation(2.0f).build()));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		Effect[] pf = new Effect[player.getActivePotionEffects().size()];
		int a = 0;
		for (Effect eff : player.getActivePotionEffects()) {
			if (!eff.isBeneficial()) {
				pf[a] = eff;
				a++;
			}
		}
		for (int s = 0; s < a; s++) {
			player.removeActivePotionEffect(pf[s]);
		}
	}
}
