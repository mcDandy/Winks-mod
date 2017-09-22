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

public class solWand extends ItemBase {
	public solWand(String unlocalizedName, CreativeTabs tab, int stack) {
		super(unlocalizedName, tab, stack);
		// TODO Auto-generated constructor stub
	}
	
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {System.out.println("Sucess");
    ItemStack itemstack = playerIn.getHeldItem(handIn);
        //boolean flag = !this.findAmmo(playerIn).isEmpty();

    EntityLargeFireball fireball= new EntityLargeFireball(worldIn,playerIn, 10, 10, 10);
    
	fireball.posX = playerIn.posX; // Null pointer exception
	fireball.posY = playerIn.posY + playerIn.eyeHeight;
	fireball.posZ = playerIn.posZ;
	fireball.accelerationX = playerIn.getLookVec().xCoord;
	fireball.accelerationY = playerIn.getLookVec().yCoord;
	fireball.accelerationZ = playerIn.getLookVec().zCoord;
	worldIn.spawnEntity(fireball);
            playerIn.setActiveHand(handIn);
            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
        }
    


	}



