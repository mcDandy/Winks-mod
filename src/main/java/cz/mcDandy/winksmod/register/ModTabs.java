package cz.mcDandy.winksmod.register;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModTabs{
	public static CreativeTabs fmod = new CreativeTabs("fmod"){
		@Override
		public ItemStack getTabIconItem(){
			return new ItemStack(ModItems.fpowder);
		}
		
	};
	public static CreativeTabs[] TABS = new CreativeTabs[]{fmod};
}