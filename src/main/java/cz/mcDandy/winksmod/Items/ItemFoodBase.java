package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood {
	public ItemFoodBase(String unlocalizedName, CreativeTabs tab) {
		this(unlocalizedName, tab, 64, 0, false);
	}

	public ItemFoodBase(String unlocalizedName, CreativeTabs tab, int stackSize) {
		// TODO Auto-generated constructor stub
		super(stackSize, 0, false);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(Main.MODID + ":" + unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

	public ItemFoodBase(String unlocalizedName, CreativeTabs tab, int stackSize, float potionEffectProbability,
			boolean alwaysEdible) {
		// TODO Auto-generated constructor stub
		super(stackSize, potionEffectProbability, alwaysEdible);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(Main.MODID + ":" + unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

}
