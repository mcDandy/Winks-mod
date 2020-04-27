package cz.mcDandy.winksmod.Items;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ModItems {
	public static Item FAIRY_POWDER = new FairyPowder(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS).food(new Food.Builder().fastToEat().setAlwaysEdible().build()))
			.setRegistryName("fairy_powder");
	public static Item SOLARIA_WAND = new SolariaWand(
			new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS)).setRegistryName("solaria_wand");
	public static Item IRON_ROD = new Item(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS))
			.setRegistryName("iron_rod");
	public static Item SOLAR_CORE = new Item(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS))
			.setRegistryName("solar_core");
	public static Item[] Items = new Item[] {FAIRY_POWDER, SOLARIA_WAND, IRON_ROD, SOLAR_CORE };

}
