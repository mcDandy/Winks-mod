package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Entities.SunSpell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolariaWand extends Item {
int speed = 1;
	public SolariaWand(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	@Override
	 public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) 
	{
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
		worldIn.addEntity(new CowEntity(null, worldIn));
		SunSpell s = new SunSpell(worldIn,entityLiving,entityLiving.getLookVec().getX()*speed,entityLiving.getLookVec().getY()*speed,entityLiving.getLookVec().getZ()*speed);
        s.posX = entityLiving.posX + s.getMotion().x * 4.0D;
        s.posY = entityLiving.posY + entityLiving.getEyeHeight();
        s.posZ = entityLiving.posZ + s.getMotion().z * 4.0D;
		worldIn.addEntity(s);
	}
}
