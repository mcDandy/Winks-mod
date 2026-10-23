package cz.mcdandy.winksmod.entity;

import cz.mcdandy.winksmod.item.ModItems;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * The spell the solaria wand fires: a straight flying solar core that blinds whatever is close to it when it
 * lands. Replaces the old {@code DamagingProjectileEntity} + {@code IRendersAsItem} combination.
 */
public class SunSpellEntity extends AbstractHurtingProjectile implements ItemSupplier {
    /** Constructor used by the entity type registration. */
    public SunSpellEntity(EntityType<? extends SunSpellEntity> type, Level level) {
        super(type, level);
    }

    private SunSpellEntity(EntityType<? extends SunSpellEntity> type, LivingEntity shooter, Vec3 direction) {
        super(type, shooter, direction, shooter.level());
    }

    /** Creates a spell leaving {@code shooter} at eye height and travelling along its view vector. */
    public static SunSpellEntity fire(ServerLevel level, LivingEntity shooter) {
        SunSpellEntity spell = new SunSpellEntity(ModEntities.SUN_SPELL.get(), shooter, shooter.getViewVector(1.0F));
        spell.setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
        level.addFreshEntity(spell);
        return spell;
    }

    @Override
    protected void onHit(HitResult result) {
        // Never hit whoever cast the spell.
        if (result instanceof EntityHitResult entityHit && ownedBy(entityHit.getEntity())) {
            return;
        }

        if (!level().isClientSide()) {
            List<LivingEntity> nearby = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4.0D, 2.0D, 4.0D));

            for (LivingEntity living : nearby) {
                double distanceSq = distanceToSqr(living);

                if (distanceSq < 4.0D) {
                    living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 1), this);
                    break;
                } else if (distanceSq < 16.0D) {
                    living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1), this);
                    break;
                }
            }

            discard();
        }
    }

    /** The spell has no impact damage of its own — the blindness on landing is all it does. */
    @Override
    protected void onHitEntity(EntityHitResult result) {
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.LARGE_SMOKE;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModItems.SOLAR_CORE.get());
    }
}
