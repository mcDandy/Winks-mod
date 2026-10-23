package cz.mcdandy.winksmod.entity;

import cz.mcdandy.winksmod.WinksMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Entity types plus the attribute and spawn placement wiring they need. */
@EventBusSubscriber(modid = WinksMod.MODID)
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, WinksMod.MODID);

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(WinksMod.MODID, name));
    }

    public static final ResourceKey<EntityType<?>> SUN_SPELL_KEY = key("sun_spell");
    public static final ResourceKey<EntityType<?>> PRISONER_KEY = key("prisoner");

    public static final DeferredHolder<EntityType<?>, EntityType<SunSpellEntity>> SUN_SPELL = ENTITY_TYPES.register(
            "sun_spell",
            () -> EntityType.Builder.of(SunSpellEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .eyeHeight(0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(SUN_SPELL_KEY)
    );

    public static final DeferredHolder<EntityType<?>, EntityType<PrisonerEntity>> PRISONER = ENTITY_TYPES.register(
            "prisoner",
            () -> EntityType.Builder.of(PrisonerEntity::new, MobCategory.MONSTER)
                    .sized(0.65F, 1.95F)
                    .eyeHeight(1.7F)
                    .build(PRISONER_KEY)
    );

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(PRISONER.get(), Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25).build());
    }

    /** The prisoner walks the floor of its dimension like any other monster. */
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(PRISONER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrisonerEntity::spawnOnSolidGround, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    private ModEntities() {
    }
}
