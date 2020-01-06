package cz.mcDandy.winksmod.Entities;

import java.util.List;

import cz.mcDandy.winksmod.Items.ModItems;
import net.minecraft.entity.AreaEffectCloudEntity;
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
import net.minecraft.util.math.BlockPos;
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
	   public SunSpell(EntityType<? extends DamagingProjectileEntity> entityIn, World worldIn) {
		      super(entityIn, worldIn);
		   }

		   @OnlyIn(Dist.CLIENT)
		   public SunSpell(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		      super(ModEntities.SUN_SPELL, x, y, z, accelX, accelY, accelZ, worldIn);
		   }

		   public SunSpell(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		      super(ModEntities.SUN_SPELL, shooter, accelX, accelY, accelZ, worldIn);
		   }

		   /**
		    * Called when this EntityFireball hits a block or entity.
		    */
		   protected void onImpact(RayTraceResult result) {
		      if (result.getType() == RayTraceResult.Type.BLOCK) {
		         if (!this.world.isRemote) {
		     /*       List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
		            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.posX, this.posY, this.posZ);
		            areaeffectcloudentity.setOwner(this.shootingEntity);
		            areaeffectcloudentity.setParticleData(ParticleTypes.DRAGON_BREATH);
		            areaeffectcloudentity.setRadius(3.0F);
		            areaeffectcloudentity.setDuration(600);
		            areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
		            areaeffectcloudentity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
		            if (!list.isEmpty()) {
		               for(LivingEntity livingentity : list) {
		                  double d0 = this.getDistanceSq(livingentity);
		                  if (d0 < 16.0D) {
		                     areaeffectcloudentity.setPosition(livingentity.posX, livingentity.posY, livingentity.posZ);
		                     break;
		                  }
		               }
		            }

		            this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
		            this.world.addEntity(areaeffectcloudentity);
		         */
		        	 
		        	 this.remove();
		         }
		         if (result.getType() == RayTraceResult.Type.ENTITY) {
			         if (!this.world.isRemote) {
			        	 
			        	 this.remove();
			         }
		         }
		      }
		      
		   }

		   /**
		    * Returns true if other Entities should be prevented from moving through this Entity.
		    */
		   public boolean canBeCollidedWith() {
		      return false;
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
