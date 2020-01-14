package cz.mcDandy.winksmod.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.awt.*;

public class ModBlocks {
    public static Block FP_BLOCK = new Fp_block(Color.MAGENTA.getRGB(),Block.Properties.create(Material.SAND).hardnessAndResistance(3.0F, 3.0F)).setRegistryName("fp_block");
    public static Block PORTAL_OMEGA_BLOCK = new OmegaPortalBlock(Block.Properties.create(Material.PORTAL).hardnessAndResistance(3.0F, 3.0F)).setRegistryName("portal_omega");

    public static Block[] BLOCKS = new Block[] {FP_BLOCK,PORTAL_OMEGA_BLOCK};
}
