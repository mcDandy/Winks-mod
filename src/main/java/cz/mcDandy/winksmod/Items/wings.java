package cz.mcDandy.winksmod.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class wings extends ItemBase {
	public wings(String unlocalizedName, CreativeTabs tab, int stack) {
		super(unlocalizedName, tab, stack);
		// TODO Auto-generated constructor stub
	}



	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}


	/**
	 * Called when the equipped item is right clicked.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		


		playerIn.motionX=playerIn.getLookVec().xCoord;
		playerIn.motionY=playerIn.getLookVec().yCoord;
		playerIn.motionZ=playerIn.getLookVec().zCoord;
		playerIn.setActiveHand(handIn);
		return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	}

}
