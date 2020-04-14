package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Entities.SunSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import cz.mcDandy.winksmod.Main;

public class SolariaWand extends Item {
	int speed = 1;

	public SolariaWand(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}

	public int getUseDuration(ItemStack stack) {
		return 7200;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
		Main.LOGGER.info("Loook vector:" + entityLiving.getLookVec());
		SunSpell s = new SunSpell(worldIn, entityLiving,0,0,0);
		s.setPosition(entityLiving.getPosition().getX(),entityLiving.getPosition().getY()+ entityLiving.getEyeHeight(),entityLiving.getPosition().getZ());
		 s.accelerationX = entityLiving.getLookVec().x;
		 s.accelerationY = entityLiving.getLookVec().y;
		 s.accelerationZ = entityLiving.getLookVec().z;
		worldIn.addEntity(s);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		super.onItemRightClick(worldIn, playerIn, handIn);
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}
}
