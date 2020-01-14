package cz.mcDandy.winksmod;

import java.awt.Color;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import cz.mcDandy.winksmod.Dimensions.Biomes.ModBiomes;
import cz.mcDandy.winksmod.Utils.NoAutomaticBlockItem;
import cz.mcDandy.winksmod.Blocks.Fp_block;
import cz.mcDandy.winksmod.Dimensions.DimOmega;
import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import cz.mcDandy.winksmod.Entities.ModEntities;
import cz.mcDandy.winksmod.Items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
class Event {
	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
		// Register all your blocks inside this registerAll call
		event.getRegistry().registerAll(setup(new Fp_block(Color.MAGENTA.getRGB(),
				Block.Properties.create(Material.SAND).hardnessAndResistance(3.0F, 3.0F)), "fp_block")

		);
		// LOGGER.debug("Registered Blocks");
	}

	/**
	 * This method will be called by Forge when it is time for the mod to register
	 * its Items. This method will always be called after the Block registry method.
	 */
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(
				// This is a very simple Item. It has no special properties except for being on
				// our creative tab.
				ModItems.Items
		// We need to go over the entire registry so that we include any potential
		// Registry Overrides
		);
		for (final Block block : ForgeRegistries.BLOCKS.getValues()) {

			final ResourceLocation blockRegistryName = block.getRegistryName();
			// An extra safe-guard against badly registered blocks
			Preconditions.checkNotNull(blockRegistryName, "Registry Name of Block \"" + block + "\" of class \""
					+ block.getClass().getName() + "\"is null! This is not allowed!");

			// Check that the blocks is from our mod, if not, continue to the next block
			if (!blockRegistryName.getNamespace().equals(Main.MODID)) {
				continue;
			}

			// If you have blocks that don't have a corresponding BlockItem, uncomment this
			// code and create
			// an Interface - or even better an Annotation - called NoAutomaticBlockItem
			// with no methods
			// and implement it on your blocks that shouldn't have BlockItems autmatically
			// made for them
			if (block instanceof NoAutomaticBlockItem) {
				continue;
			}

			// Make the properties, and make it so that the item will be on our ItemGroup
			// (CreativeTab)
			final Item.Properties properties = new Item.Properties()
					.group(/* ModItemGroups.WMOD */ItemGroup.BUILDING_BLOCKS);
			// Create the new BlockItem with the block and it's properties
			final BlockItem blockItem = new BlockItem(block, properties);
			// Setup the new BlockItem with the block's registry name and register it
			registry.register(setup(blockItem, blockRegistryName));
		}
		Main.LOGGER.info("Registered Items");
	}
	@SubscribeEvent
	public static void onDimensionModRegistry(RegistryEvent.Register<ModDimension> event) {
		event.getRegistry().registerAll(ModDimensions.OMEGA);
	}
	@SubscribeEvent
	public static void onBiomeRegistry(RegistryEvent.Register<Biome> event) {
		event.getRegistry().registerAll(ModBiomes.BIOMES);
	}

	@SubscribeEvent
	public static void register(RegistryEvent.Register<EntityType<?>> e) {
		IForgeRegistry<EntityType<?>> registry = e.getRegistry();
		registry.registerAll(ModEntities.Entities);
	}


	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name) {
		Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
		return setup(entry, new ResourceLocation(Main.MODID, name));
	}

	/**
	 * Performs setup on a registry entry
	 *
	 * @param registryName The full registry name of the entry
	 */
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry,
			@Nonnull final ResourceLocation registryName) {
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}

}