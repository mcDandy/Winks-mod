package cz.mcDandy.winksmod.Entities;

import cz.mcDandy.winksmod.Items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class SunSpell extends ProjectileItemEntity {
	public int explosionPower = 1;
	public Entity thrower;
	   public SunSpell(EntityType<? extends SunSpell> p_i50154_1_, World p_i50154_2_) {
		      super(p_i50154_1_, p_i50154_2_);
		   }
	public SunSpell(World worldIn, LivingEntity throwerIn) {
		super(ModEntities.SUN_SPELL, throwerIn, worldIn);
		thrower = throwerIn;
	}

	public SunSpell(World worldIn, double x, double y, double z) {
		super(ModEntities.SUN_SPELL, x, y, z, worldIn);
		// thrower = throwerIn;
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.getType() == RayTraceResult.Type.ENTITY) {
				Entity entity = ((EntityRayTraceResult) result).getEntity();
				// entity.attackEntityFrom(DamageSource.(this, this.thrower), 6.0F);
				// this.applyEnchantments(this.shootingEntity, entity);
			}

			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.thrower);
			this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, (float) this.explosionPower,
					flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
			this.remove();
		}

	}

	@Override
	protected Item getDefaultItem() {
		// TODO Auto-generated method stub
		return ModItems.SOLAR_CORE;
	}

}
