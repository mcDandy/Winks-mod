package cz.mcDandy.winksmod.Entities;

import javax.annotation.Nullable;

import cz.mcDandy.winksmod.Main;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGenericFiary extends EntityMob {

	public static final ResourceLocation LOOT = new ResourceLocation(Main.MODID,"loot_tables/fiary_generic");

	public EntityGenericFiary(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(6, new EntityAIWander(this, interpTargetPitch, 10));
		  this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		// tasks.addTask(0, new EntityAIDoorInteract(this));
	}

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 5;
    }
}
