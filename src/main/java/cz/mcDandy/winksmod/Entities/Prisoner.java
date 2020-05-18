package cz.mcDandy.winksmod.Entities;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Prisoner extends AbstractSkeletonEntity {
    public Prisoner(EntityType<? extends Prisoner> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_STEP;
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        Entity entity = source.getTrueSource();
        if (entity instanceof CreeperEntity) {
            CreeperEntity creeperentity = (CreeperEntity)entity;
            if (creeperentity.ableToCauseSkullDrop()) {
                creeperentity.incrementDroppedSkulls();
                this.entityDropItem(Items.WITHER_SKELETON_SKULL);
            }
        }

    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    /**
     * Enchants Entity's current equipments based on given DifficultyInstance
     */
    protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData ilivingentitydata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.setCombatTask();
        return ilivingentitydata;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 1.7F;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        if (!super.attackEntityAsMob(entityIn)) {
            return false;
        } else {
          /*  if (entityIn instanceof LivingEntity) {
                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.WITHER, 200));
            }
          */
            return true;
        }
    }

}