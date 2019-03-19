package cz.mcDandy.winksmod.Entities;

import java.util.UUID;

import javax.annotation.Nullable;

import cz.mcDandy.winksmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityFairyGeneric extends EntityMob {

	public static final ResourceLocation LOOT = new ResourceLocation(Main.MODID, "loot_tables/fiary_generic");
	private int angerLevel;
	private int randomSoundDelay;
	private UUID angerTargetUUID;

	public EntityFairyGeneric(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEntityAI() {
		this.targetTasks.addTask(1, new EntityFairyGeneric.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityFairyGeneric.AITargetAggressor(this));
		tasks.addTask(4, new EntityAILookIdle(this));
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(5, new EntityAIWander(this, interpTargetPitch, 10));
		tasks.addTask(1, new EntityAIMoveThroughVillage(this, 1.0D, false));
		// this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityWitch.class, false, true));
		// S this.targetTasks.addTask(6, new EntityAiFly());
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
	}

	/**
	 * Checks that the entity is not colliding with any blocks / liquids
	 */
	@Override
	public boolean isNotColliding() {
		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
				&& this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()
				&& !this.world.containsAnyLiquid(this.getEntityBoundingBox());
	}

	/*
	 * public static void registerFixesPigZombie(DataFixer fixer) {
	 * EntityLiving.registerFixesMob(fixer, EntityPigZombie.class); }
	 */
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setShort("Anger", (short) this.angerLevel);

		if (this.angerTargetUUID != null) {
			compound.setString("HurtBy", this.angerTargetUUID.toString());
		} else {
			compound.setString("HurtBy", "");
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.angerLevel = compound.getShort("Anger");
		String s = compound.getString("HurtBy");

		if (!s.isEmpty()) {
			this.angerTargetUUID = UUID.fromString(s);
			EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
			this.setRevengeTarget(entityplayer);

			if (entityplayer != null) {
				this.attackingPlayer = entityplayer;
				this.recentlyHit = this.getRevengeTimer();
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	/**
	 * Causes this PigZombie to become angry at the supplied Entity (which will be a
	 * player).
	 */
	private void becomeAngryAt(Entity p_70835_1_) {
		this.angerLevel = 400 + this.rand.nextInt(400);
		this.randomSoundDelay = this.rand.nextInt(40);

		if (p_70835_1_ instanceof EntityLivingBase) {
			this.setRevengeTarget((EntityLivingBase) p_70835_1_);
		}
	}

	static class AIHurtByAggressor extends EntityAIHurtByTarget {
		public AIHurtByAggressor(EntityFairyGeneric entityFairyGeneric) {
			super(entityFairyGeneric, true);
		}

		@Override
		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityFairyGeneric) {
				((EntityFairyGeneric) creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer> {
		public AITargetAggressor(EntityFairyGeneric p_i45829_1_) {
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		@Override
		public boolean shouldExecute() {
			return ((EntityFairyGeneric) this.taskOwner).isAngry() && super.shouldExecute();
		}
	}

	public boolean isAngry() {
		return this.angerLevel > 0;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (isEntityInvulnerable(par1DamageSource)) {
			return false;
		} else {
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return LOOT;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 5;
	}
}
