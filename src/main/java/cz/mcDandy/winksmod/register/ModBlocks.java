package cz.mcDandy.winksmod.register;

import cz.mcDandy.winksmod.Blocks.fp_Block;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {

	public static Block fp_block = new fp_Block("fp_block", Material.SAND, ModTabs.fmod);
	public static Block[] BLOCKS = new Block[] { fp_block };
}