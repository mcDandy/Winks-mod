package cz.mcDandy.winksmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class fp_Block extends BlockBaseFalling {
	public fp_Block(String unlocalizedName, Material mat, CreativeTabs tab) {
		super(unlocalizedName, mat, tab);
		// TileEntityBeaconRenderer.renderBeamSegment(x, y, z, partialTicks,
		// textureScale, totalWorldTime, yOffset, height, colors, beamRadius,
		// glowRadius);
		this.lightValue = 8;
	}
}
