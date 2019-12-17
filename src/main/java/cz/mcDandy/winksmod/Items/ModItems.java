package cz.mcDandy.winksmod.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ModItems {
public static Item Fpowder = new Item(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS)).setRegistryName("fpowder");
	public static Item SOLARIA_WAND = new SolariaWand(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS)).setRegistryName("solaria_wand");
	public static Item IRON_ROOD = new Item(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS)).setRegistryName("iron_rood");
	public static Item SOLAR_CORE = new Item(new Item.Properties().group(/* ModItemGroups.WMOD */ItemGroup.DECORATIONS)).setRegistryName("solar_core");
	public static Item[] Items = new Item[] { Fpowder, SOLARIA_WAND, IRON_ROOD, SOLAR_CORE };

}
