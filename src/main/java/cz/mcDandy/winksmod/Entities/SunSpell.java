package cz.mcDandy.winksmod.Entities;

import java.util.Iterator;
import java.util.List;

import cz.mcDandy.winksmod.Items.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SunSpell extends DamagingProjectileEntity implements IRendersAsItem {
/*	public SunSpell(EntityType<SunSpell> p_i50171_1_, World p_i50171_2_) {
		super(p_i50171_1_, p_i50171_2_);
	}
*/
	@OnlyIn(Dist.CLIENT)
	public SunSpell(World p_i46775_1_, double p_i46775_2_, double p_i46775_4_, double p_i46775_6_, double p_i46775_8_, double p_i46775_10_, double p_i46775_12_) {
		super(EntityType.DRAGON_FIREBALL, p_i46775_2_, p_i46775_4_, p_i46775_6_, p_i46775_8_, p_i46775_10_, p_i46775_12_, p_i46775_1_);
	}

	public SunSpell(World p_i46776_1_, LivingEntity p_i46776_2_, double p_i46776_3_, double p_i46776_5_, double p_i46776_7_) {
		super(EntityType.DRAGON_FIREBALL, p_i46776_2_, p_i46776_3_, p_i46776_5_, p_i46776_7_, p_i46776_1_);
	}

	public SunSpell(EntityType<SunSpell> sunSpellEntityType, World world) {
		super(sunSpellEntityType,world);
	}

	protected void onImpact(RayTraceResult p_70227_1_) {
		super.onImpact(p_70227_1_);
		if (p_70227_1_.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult)p_70227_1_).getEntity().isEntityEqual(this.shootingEntity)) {
			if (!this.world.isRemote) {
				List<LivingEntity> livingEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
				AreaEffectCloudEntity areaCloudEffect = new AreaEffectCloudEntity(this.world, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_());
				areaCloudEffect.setOwner(this.shootingEntity);
				areaCloudEffect.setParticleData(ParticleTypes.DRAGON_BREATH);
				areaCloudEffect.setRadius(3.0F);
				areaCloudEffect.setDuration(600);
				areaCloudEffect.setRadiusPerTick((7.0F - areaCloudEffect.getRadius()) / (float)areaCloudEffect.getDuration());
				areaCloudEffect.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
				if (!livingEntities.isEmpty()) {
					Iterator var4 = livingEntities.iterator();

					while(var4.hasNext()) {
						LivingEntity lvt_5_1_ = (LivingEntity)var4.next();
						double lvt_6_1_ = this.getDistanceSq(lvt_5_1_);
						if (lvt_6_1_ < 16.0D) {
							areaCloudEffect.setPosition(lvt_5_1_.func_226277_ct_(), lvt_5_1_.func_226278_cu_(), lvt_5_1_.func_226281_cx_());
							break;
						}
					}
				}

				this.world.playEvent(2006, new BlockPos(this), 0);
				this.world.addEntity(areaCloudEffect);
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
		return ParticleTypes.DRAGON_BREATH;
	}

	protected boolean isFireballFiery() {
		return false;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(ModItems.SOLAR_CORE);
	}
}

