package cz.mcDandy.winksmod.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBase extends Item {
	public ItemBase(String unlocalizedName, CreativeTabs tab) {
		this(unlocalizedName, tab, 64);
	}

	public ItemBase(String unlocalizedName, CreativeTabs tab, int stackSize) {
		// TODO Auto-generated constructor stub
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
	}

	public solWand onItemRightClick(ItemStack itemstack, World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		// TODO Auto-generated method stub
		return null;
	}
}