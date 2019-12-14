package cz.mcDandy.winksmod.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SunSpell extends AbstractFireballEntity {
	public int explosionPower = 1;

	@OnlyIn(Dist.CLIENT)
	public SunSpell(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(EntityType.FIREBALL, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public SunSpell(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(EntityType.FIREBALL, shooter, accelX, accelY, accelZ, worldIn);
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			if (result.getType() == RayTraceResult.Type.ENTITY) {
				Entity entity = ((EntityRayTraceResult) result).getEntity();
				entity.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0F);
				this.applyEnchantments(this.shootingEntity, entity);
			}

			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world,
					this.shootingEntity);
			this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, (float) this.explosionPower,
					flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
			this.remove();
		}

	}

}
