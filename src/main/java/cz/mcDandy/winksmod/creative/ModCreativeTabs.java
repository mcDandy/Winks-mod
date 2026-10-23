package cz.mcdandy.winksmod.creative;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.block.ModBlocks;
import cz.mcdandy.winksmod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * The mod's own creative mode tab. The old code dropped its content into vanilla item groups, which no longer
 * map onto what this mod adds (there is no decorations group anymore).
 */
public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WinksMod.MODID);

    public static final ResourceKey<CreativeModeTab> WINKS_TAB =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(WinksMod.MODID, "winks_tab"));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("winks_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + WinksMod.MODID + ".winks_tab"))
            .icon(() -> new ItemStack(ModItems.SOLARIA_WAND.get()))
            .displayItems((parameters, output) -> {
                output.accept(new ItemStack(ModBlocks.FP_BLOCK.get()));
                output.accept(new ItemStack(ModBlocks.DARK_OBSIDIAN.get()));
                output.accept(new ItemStack(ModItems.FAIRY_POWDER.get()));
                output.accept(new ItemStack(ModItems.IRON_ROD.get()));
                output.accept(new ItemStack(ModItems.SOLAR_CORE.get()));
                output.accept(new ItemStack(ModItems.SOLARIA_WAND.get()));
            })
            .build());

    private ModCreativeTabs() {
    }
}
