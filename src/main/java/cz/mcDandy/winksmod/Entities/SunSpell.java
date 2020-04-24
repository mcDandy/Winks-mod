package cz.mcDandy.winksmod.Entities;

import java.util.Iterator;
import java.util.List;

import cz.mcDandy.winksmod.Items.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SunSpell extends DamagingProjectileEntity implements IRendersAsItem {
/*	public SunSpell(EntityType<SunSpell> p_i50171_1_, World p_i50171_2_) {
		super(p_i50171_1_, p_i50171_2_);
	}
*/
	@OnlyIn(Dist.CLIENT)
	public SunSpell(World p_i46775_1_, double p_i46775_2_, double p_i46775_4_, double p_i46775_6_, double p_i46775_8_, double p_i46775_10_, double p_i46775_12_) {
		super(ModEntities.SUN_SPELL, p_i46775_2_, p_i46775_4_, p_i46775_6_, p_i46775_8_, p_i46775_10_, p_i46775_12_, p_i46775_1_);
	}

	public SunSpell(World p_i46776_1_, LivingEntity p_i46776_2_, double p_i46776_3_, double p_i46776_5_, double p_i46776_7_) {
		super(ModEntities.SUN_SPELL, p_i46776_2_, p_i46776_3_, p_i46776_5_, p_i46776_7_, p_i46776_1_);
	}

	public SunSpell(EntityType<SunSpell> sunSpellEntityType, World world) {
		super(sunSpellEntityType,world);
	}

	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult)result).getEntity().isEntityEqual(this.shootingEntity)) {
			if (!this.world.isRemote) {
				List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
				if (!list.isEmpty()) {
					for(LivingEntity livingentity : list) {
						double d0 = this.getDistanceSq(livingentity);
						if (d0 < 4.0D) {
							livingentity.addPotionEffect(new EffectInstance(Effects.BLINDNESS,120,1));
							break;
						}
						else if (d0 < 16.0D) {
							livingentity.addPotionEffect(new EffectInstance(Effects.BLINDNESS,60,1));
							break;
						}

					}
				}
				this.remove();
			}

		}
	}

	public boolean canBeCollidedWith() {
		return false;
	}

	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		return false;
	}

	protected IParticleData getParticle() {
		return ParticleTypes.LARGE_SMOKE;
	}

	protected boolean isFireballFiery() {
		return false;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(ModItems.SOLAR_CORE);
	}
	@Override

	public IPacket<?> createSpawnPacket() {

		return NetworkHooks.getEntitySpawningPacket(this);

	}
}

