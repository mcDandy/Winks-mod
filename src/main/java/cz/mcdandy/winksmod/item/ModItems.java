package cz.mcdandy.winksmod.item;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * All items of the mod. The old code looped over the block registry to auto create block items; here every
 * block item is registered explicitly, and the portal blocks simply do not get one.
 */
public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WinksMod.MODID);

    /** The old {@code ItemGroup.DECORATIONS} default food: 4 nutrition with the vanilla saturation modifier. */
    private static final FoodProperties FAIRY_POWDER_FOOD = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.6F)
            .alwaysEdible()
            .build();

    public static final DeferredItem<FairyPowderItem> FAIRY_POWDER =
            ITEMS.registerItem("fairy_powder", FairyPowderItem::new, properties -> properties.food(FAIRY_POWDER_FOOD));

    public static final DeferredItem<SolariaWandItem> SOLARIA_WAND = ITEMS.registerItem("solaria_wand", SolariaWandItem::new);

    public static final DeferredItem<Item> IRON_ROD = ITEMS.registerSimpleItem("iron_rod");

    /** The item the sun spell is rendered as. */
    public static final DeferredItem<Item> SOLAR_CORE = ITEMS.registerSimpleItem("solar_core");

    public static final DeferredItem<BlockItem> FP_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.FP_BLOCK);
    public static final DeferredItem<BlockItem> DARK_OBSIDIAN_ITEM = ITEMS.registerSimpleBlockItem(ModBlocks.DARK_OBSIDIAN);

    private ModItems() {
    }
}
