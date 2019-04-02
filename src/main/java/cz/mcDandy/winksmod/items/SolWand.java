package cz.mcDandy.winksmod.items;

import cz.mcDandy.winksmod.GUI.TpWandGui;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SolWand extends ItemBase {
	// ItemStack itemstack = new ItemStack(ModItems.solwand);

	public SolWand(String unlocalizedName, CreativeTabs tab, int stack) {
		super(unlocalizedName, tab);

		this.setMaxStackSize(1);
		this.setMaxDamage(10000);

		//isDamageable();
		// getDurabilityForDisplay();
		// TODO Auto-generated constructor stub
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (playerIn.isSneaking()) {
			Minecraft.getMinecraft().displayGuiScreen(new TpWandGui(worldIn, playerIn));
		} else {
			if (itemstack.getMaxDamage() - itemstack.getItemDamage() > 100) {
				EntityLargeFireball fireball = new EntityLargeFireball(worldIn, playerIn, 10, 10, 10);
				itemstack.damageItem(100, playerIn);
				fireball.posX = playerIn.posX;
				fireball.posY = playerIn.posY + playerIn.getEyeHeight();
				fireball.posZ = playerIn.posZ;
				fireball.accelerationX = playerIn.getLookVec().x;
				fireball.accelerationY = playerIn.getLookVec().y;
				fireball.accelerationZ = playerIn.getLookVec().z;
				worldIn.spawnEntity(fireball);
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

}
