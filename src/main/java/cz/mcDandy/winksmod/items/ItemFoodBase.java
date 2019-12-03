package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Main;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemFood;

public class ItemFoodBase extends ItemFood {
	public ItemFoodBase(String unlocalizedName, ItemGroup tab) {
		this(unlocalizedName, tab, 64, 0, false);
	}

	public ItemFoodBase(String unlocalizedName, ItemGroup tab, int stackSize) {
		// TODO Auto-generated constructor stub
		super(stackSize, 0, false);
		setTranslationKey(unlocalizedName);
		setRegistryName(Main.MODID + ":" + unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

	public ItemFoodBase(String unlocalizedName, ItemGroup tab, int stackSize, float potionEffectProbability,
			boolean alwaysEdible) {
		// TODO Auto-generated constructor stub
		super(stackSize, potionEffectProbability, alwaysEdible);
		setTranslationKey(unlocalizedName);
		setRegistryName(Main.MODID + ":" + unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

}
