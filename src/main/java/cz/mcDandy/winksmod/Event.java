package cz.mcDandy.winksmod;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import cz.mcDandy.winksmod.Blocks.ModBlocks;
import cz.mcDandy.winksmod.Capabilities.AccessibleTransformationsCapability;
import cz.mcDandy.winksmod.Capabilities.FairyEnergyCapability;
import cz.mcDandy.winksmod.Dimensions.Biomes.ModBiomes;
import cz.mcDandy.winksmod.Entities.ModEntities;
import cz.mcDandy.winksmod.Items.ModItems;
import cz.mcDandy.winksmod.Render.Entities.PrisonerRenderer;
import cz.mcDandy.winksmod.Render.Entities.SunSpellRenderer;
import cz.mcDandy.winksmod.Utils.NoAutomaticBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Event {

    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
        // Register all your blocks inside this registerAll call
        event.getRegistry().registerAll(
                ModBlocks.BLOCKS
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
    public static void onBiomeRegistry(RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(ModBiomes.BIOMES);
    }
    @SubscribeEvent
    public static void onFmlSetup(FMLCommonSetupEvent event)
    {
        FairyEnergyCapability.register();
        AccessibleTransformationsCapability.register();
    }

    @SubscribeEvent
    public static void OnEntityRegister(RegistryEvent.Register<EntityType<?>> e) {
        IForgeRegistry<EntityType<?>> registry = e.getRegistry();
        registry.registerAll(ModEntities.Entities);
    }

    @SubscribeEvent
    public static void setupModels(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SUN_SPELL, new SunSpellRenderer.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.PRISONER, PrisonerRenderer::new);
    }
    @SubscribeEvent
    public static void CreatePOI(RegistryEvent.Register<PointOfInterestType> event) {
        Method method = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_226359_a_", String.class, Set.class, int.class,
                int.class);
        method.setAccessible(true);
        try {PointOfInterestCustom.OMEGA_PORTAL = (PointOfInterestType) method.invoke(null, "dimomega_portal",
                Sets.newHashSet(ImmutableSet.copyOf(ModBlocks.PORTAL_OMEGA_BLOCK.getStateContainer().getValidStates())), 0, 1);
            event.getRegistry().register(PointOfInterestCustom.OMEGA_PORTAL);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

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