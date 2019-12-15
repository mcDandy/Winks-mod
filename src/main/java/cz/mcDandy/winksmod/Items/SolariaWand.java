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
		return UseAction.BOW;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
		Main.LOGGER.info("Right clicked with SolariaWand");
		SunSpell s = new SunSpell(worldIn, entityLiving, entityLiving.getLookVec().getX() * speed,
				entityLiving.getLookVec().getY() * speed, entityLiving.getLookVec().getZ() * speed);
		s.posX = entityLiving.posX + s.getMotion().x * 4.0D;
		s.posY = entityLiving.posY + entityLiving.getEyeHeight();
		s.posZ = entityLiving.posZ + s.getMotion().z * 4.0D;
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
