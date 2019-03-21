package cz.mcDandy.winksmod.blocks;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBaseFalling extends BlockFalling {

	public BlockBaseFalling(String unlocalizedName, Material mat, CreativeTabs tab) {
		super(mat);
		setTranslationKey(unlocalizedName);
		setRegistryName(unlocalizedName);
		setCreativeTab(tab);// TODO Auto-generated constructor stub
	}

	public BlockBaseFalling(String unlocalizedName, Material mat, CreativeTabs tab, float hardness, float resistence) {
		// TODO Auto-generated constructor stub
		this(unlocalizedName, mat, tab);

		setHardness(hardness);
		setResistance(resistence);
	}

	public BlockBaseFalling(String unlocalizedName, Material mat, CreativeTabs tab, float Hardness, float resistence,
			String tool, int harvestLevl) {
		this(unlocalizedName, mat, tab, Hardness, resistence);
		setHarvestLevel(tool, harvestLevl);
		// TODO Auto-generated constructor stub
	}

}