package cz.mcDandy.winksmod.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.registry.Registry;

public class ModEntities {
	public static EntityType<SunSpell> SUN_SPELL = register("winksmod:sun_spell", EntityType.Builder.<SunSpell>create(SunSpell::new, EntityClassification.MISC).size(1.0F, 1.0F));
	public static EntityType<SkeletonBandit> SKELETON_BANDIT = register("winksmod:skeleton_bandit", EntityType.Builder.<SkeletonBandit>create(SkeletonBandit::new, EntityClassification.MONSTER).size(1.0F, 1.0F));
	public static EntityType[] Entities = new EntityType[] {SUN_SPELL,SKELETON_BANDIT};
	   private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
		      return Registry.register(Registry.ENTITY_TYPE, key, builder.build(key));
		   }
}
