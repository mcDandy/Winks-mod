package cz.mcDandy.winksmod.Register;

import cz.mcDandy.winksmod.blocks.fp_Block;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static Block fp_block = new fp_Block("fp_block", Material.SAND, ModTabs.fmod);
	public static Block BlockCharger = new fp_Block("BlockCharger", Material.CIRCUITS, ModTabs.fmod);
	public static Block[] BLOCKS = new Block[] { fp_block, BlockCharger };
}