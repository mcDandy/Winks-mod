package cz.mcDandy.winksmod;

import cz.mcDandy.winksmod.register.Blocks;
import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Event {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		System.out.println("registring");
		for (Block block : Blocks.BLOCKS) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
					new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}

		for (Item item : ModItems.ITEMS) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}

	}

	EntityPlayer player;

	EnumHand hand;

	int FiaryLevel;

	// Called when the client ticks.
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {

	}

	@SubscribeEvent
	public void onLivingEvent(LivingUpdateEvent event) {
		if (event.getEntity() == player) {
			ItemStack itemstack = player.getHeldItem(hand);
			if (itemstack.getItem() == ModItems.wings) {
			}
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.LoadFromFile event) {
		NBTTagCompound nbt = player.getEntityData();
		if (nbt.hasKey("Fiary Level", 99)) {
			FiaryLevel = nbt.getInteger("Fiary Level");
			System.out.println(FiaryLevel);
		} else {
			nbt.setInteger("Fiary Level", 0);
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.SaveToFile event) {
		NBTTagCompound nbt = player.getEntityData();
		nbt.setInteger("Fiary Level", FiaryLevel + 1);
	}

	// Called when a new frame is displayed (See fps)
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {

	}

	// Called when the server ticks. Usually 20 ticks a second.
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

	}

	// Called when the world ticks
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {

	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(Blocks.fp_block);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(Blocks.fp_block).setRegistryName(Blocks.fp_block.getRegistryName()));
		event.getRegistry().registerAll(ModItems.fpowder, ModItems.solwand, ModItems.wings, ModItems.magicdiamond);

	}
}