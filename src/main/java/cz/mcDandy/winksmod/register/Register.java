package cz.mcDandy.winksmod.register;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
public class Register{
public static void registerAll(FMLPreInitializationEvent pre){
		registerBlocks(pre,Blocks.fp_block);
	}
public static void registerBlocks(FMLPreInitializationEvent pre,Block...blocks){
	for(Block block:blocks)
	{
		final ItemBlock itemblock=new ItemBlock(block);
		if(pre.getSide()==Side.CLIENT)
		{
			GameRegistry.register(block);
			GameRegistry.register(itemblock,block.getRegistryName());
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),0,new ModelResourceLocation(block.getRegistryName(),"inventory"));
		}
	}
}

public static void registerItems(FMLPreInitializationEvent pre,Item...items){
	for(Item item:items)
	{

		if(pre.getSide()==Side.CLIENT)
		{
			GameRegistry.register(item);
			
			ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
		}
	}
}
}