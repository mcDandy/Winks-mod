package cz.mcDandy.winksmod.Items;


import cz.mcDandy.winksmod.register.ModPotions;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class mPotion extends ItemPotion {
public mPotion(String name, CreativeTabs tab, int max)
	{
		this.setMaxStackSize(max);
		this.setCreativeTab(tab);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		
	}
@Override
public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) 
	{
	entityLiving.addPotionEffect(ModPotions.MisticPot);
	return stack;
	}
}
