package cz.mcDandy.winksmod.Blocks;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.creativetab.CreativeTabs;

public class fp_Block extends BlockBaseFalling {
	public fp_Block(String unlocalizedName, Material mat, CreativeTabs tab) {
		super(unlocalizedName, mat, tab);
		//TileEntityBeaconRenderer.renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, beamRadius, glowRadius);
	}
}
