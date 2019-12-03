package cz.mcDandy.winksmod;


import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import cz.mcDandy.winksmod.Capatibilities.Fairy;
import cz.mcDandy.winksmod.Capatibilities.FairyProvider;
import cz.mcDandy.winksmod.Capatibilities.IFairy;
import cz.mcDandy.winksmod.Register.ModBlocks;
import cz.mcDandy.winksmod.Register.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Event {
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS);

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS);

		for (Block block : ModBlocks.BLOCKS) {
			event.getRegistry().register(new BlockItem(block, new Properties()));
		}
	}
	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) 
	{
	
	}
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for (Block block : ModBlocks.BLOCKS) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
					new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}

		for (Item item : ModItems.ITEMS) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}

	@SubscribeEvent
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();

		IFairy fairy = player.getCapability(FairyProvider.FAIRY_CAP, null);
		if (fairy == null) {
			fairy = new Fairy(0, false);
		}
	}
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Main.MODID, name));
	}

	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}