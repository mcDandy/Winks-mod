package cz.mcDandy.winksmod.Items;

import cz.mcDandy.winksmod.Main;
import cz.mcDandy.winksmod.register.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class solwand extends Item {
//	ItemStack itemstack = new ItemStack(ModItems.solwand);

	public solwand(String unlocalizedName, CreativeTabs tab, int stack) {
		super();
this.setRegistryName(Main.MODID,unlocalizedName);
this.setUnlocalizedName(unlocalizedName);
this.setCreativeTab(tab);
this.maxStackSize=1;
this.setMaxDamage(100);
		//showDurabilityBar(null);
		isDamageable();
		//getDurabilityForDisplay();
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

//	public EnumAction getItemUseAction(ItemStack stack) {
//		return EnumAction.BOW;
//	}

	/**
	 * Called when the equipped item is right clicked.
	 */
	@Override
	  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	    {
	        ItemStack itemstack = playerIn.getHeldItem(handIn);

	        if (worldIn.isRemote)
	        {
	            return new ActionResult(EnumActionResult.PASS, itemstack);
	        }
	        else
	        {


	                if (itemstack.getMaxDamage() - itemstack.getMetadata() > 10)
	                {
	                    itemstack.damageItem(10, playerIn);
	                    EntityLargeFireball fireball = new EntityLargeFireball(worldIn, playerIn, 10, 10, 10);

	        			fireball.posX = playerIn.posX;
	        			fireball.posY = playerIn.posY + playerIn.eyeHeight;
	        			fireball.posZ = playerIn.posZ;
	        			fireball.accelerationX = playerIn.getLookVec().xCoord;
	        			fireball.accelerationY = playerIn.getLookVec().yCoord;
	        			fireball.accelerationZ = playerIn.getLookVec().zCoord;
	        			worldIn.spawnEntity(fireball);
	                    

	                    return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	                }
	           

	          
	            return new ActionResult(EnumActionResult.PASS, itemstack);
	            }
	        }


	}
