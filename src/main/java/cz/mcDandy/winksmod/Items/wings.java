package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.capatibilities.FiaryLVLProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	/**
	 * Called when the equipped item is right clicked.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		ItemStack itemstack = playerIn.getHeldItem(handIn);

			playerIn.getCapability(FiaryLVLProvider.FIARYLVL_CAP, null).set(1.0F);
			if (playerIn.getCapability(FiaryLVLProvider.FIARYLVL_CAP, null).IsFiary()) {
				playerIn.motionX = playerIn.getLookVec().x;
				playerIn.motionY = playerIn.getLookVec().y;
				playerIn.motionZ = playerIn.getLookVec().z;
			}

		

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

}