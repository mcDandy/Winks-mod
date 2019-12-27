package cz.mcDandy.winksmod.Entities;

import cz.mcDandy.winksmod.Items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SunSpell extends DamagingProjectileEntity {
	public int explosionPower = 1;
	public Entity thrower;
	   public SunSpell(EntityType<? extends SunSpell> p_i50154_1_, World p_i50154_2_) {
		      super(p_i50154_1_, p_i50154_2_);
		   }
	   @OnlyIn(Dist.CLIENT)
	   public SunSpell(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
	      super(EntityType.DRAGON_FIREBALL, x, y, z, accelX, accelY, accelZ, worldIn);
	   }

	   public SunSpell(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
	      super(EntityType.DRAGON_FIREBALL, shooter, accelX, accelY, accelZ, worldIn);
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

	  /**
	    * Called when the entity is attacked.
	    */
	   public boolean attackEntityFrom(DamageSource source, float amount) {
	      return false;
	   }

	   protected IParticleData getParticle() {
	      return ParticleTypes.DRAGON_BREATH;
	   }

	   protected boolean isFireballFiery() {
	      return false;
	   }

}
