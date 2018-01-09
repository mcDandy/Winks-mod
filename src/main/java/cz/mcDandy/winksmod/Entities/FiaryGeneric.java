package cz.mcDandy.winksmod.Entities;

import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class FiaryGeneric extends EntityMob {

	public FiaryGeneric(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAILookIdle(this));
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(0, new EntityAIWander(this, interpTargetPitch, 10));

		// tasks.addTask(0, new EntityAIDoorInteract(this));
	}

}