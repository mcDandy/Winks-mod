package cz.mcDandy.winksmod.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		


		playerIn.motionX=playerIn.getLookVec().x;
		playerIn.motionY=playerIn.getLookVec().y;
		playerIn.motionZ=playerIn.getLookVec().z;
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}