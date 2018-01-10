package cz.mcDandy.winksmod.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBase extends Block {

	public BlockBase(String unlocalizedName, Material mat, CreativeTabs tab) {
		super(mat);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		setCreativeTab(tab);// TODO Auto-generated constructor stub
	}

	public BlockBase(String unlocalizedName, Material mat, CreativeTabs tab, float hardness, float resistence) {
		// TODO Auto-generated constructor stub
		this(unlocalizedName, mat, tab);

		setHardness(hardness);
		setResistance(resistence);
	}

	public BlockBase(String unlocalizedName, Material mat, CreativeTabs tab, float Hardness, float resistence,
			String tool, int harvestLevl) {
		this(unlocalizedName, mat, tab, Hardness, resistence);
		setHarvestLevel(tool, harvestLevl);
		// TODO Auto-generated constructor stub
	}

}