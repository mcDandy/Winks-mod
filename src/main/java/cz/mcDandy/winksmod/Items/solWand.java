package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class solWand extends Item {
	// ItemStack itemstack = new ItemStack(ModItems.solwand);

	public solWand(String unlocalizedName, CreativeTabs tab, int stack) {
		super();
		this.setRegistryName(Main.MODID, unlocalizedName);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(tab);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
		
		isDamageable();
		// getDurabilityForDisplay();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
		}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,EnumHand handIn) {
		
		
ItemStack itemstack = playerIn.getHeldItem(handIn);
Item item = itemstack.getItem();
//(10, playerIn);

			if (itemstack.getMaxDamage() - itemstack.getItemDamage() > 10) {
				EntityLargeFireball fireball = new EntityLargeFireball(worldIn, playerIn, 10, 10, 10);
				item.setDamage(itemstack, item.getDamage(itemstack)-10); 
				//item.getDamage(itemstack);
				fireball.posX = playerIn.posX;
				fireball.posY = playerIn.posY + playerIn.getEyeHeight();
				fireball.posZ = playerIn.posZ;
				fireball.accelerationX = playerIn.getLookVec().x;
				fireball.accelerationY = playerIn.getLookVec().y;
				fireball.accelerationZ = playerIn.getLookVec().z;
				worldIn.spawnEntity(fireball);

				return new ActionResult<ItemStack>( EnumActionResult.SUCCESS, new ItemStack(item));
			}


			return new ActionResult<ItemStack>( EnumActionResult.PASS, itemstack);
	}

}
