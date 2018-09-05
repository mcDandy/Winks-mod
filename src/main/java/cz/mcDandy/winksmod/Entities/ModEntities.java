package cz.mcDandy.winksmod.Entities;

import cz.mcDandy.winksmod.Main;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

	public static void init() {
		// Every entity in our mod has an ID (local to this mod)
		int id = 1;
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, "Entities"), EntityGenericFairy.class,
				"GenericFairy", id++, Main.instance, 64, 3, true, 0xffffff, 0x000000);

		// We want our mob to spawn in Plains and ice plains biomes. If you don't add
		// this then it will not spawn automatically
		// but you can of course still make it spawnmanually
		EntityRegistry.addSpawn(EntityGenericFairy.class, 100, 3, 5, EnumCreatureType.MONSTER, Biomes.PLAINS,
				Biomes.ICE_PLAINS);

		// This is the loot table for our mob
		LootTableList.register(EntityGenericFairy.LOOT);
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(EntityGenericFairy.class, RenderGenericFairy.FACTORY);
	}
}