package cz.mcDandy.winksmod.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

import java.awt.*;


public class ModBlocks {
    public static Block FP_BLOCK = new Fp_block(Color.MAGENTA.getRGB(), Block.Properties.create(Material.SAND).hardnessAndResistance(3.0F, 3.0F)).setRegistryName("fp_block");
    public static Block PORTAL_OMEGA_BLOCK = new OmegaPortalBlock(Block.Properties.create(Material.PORTAL).hardnessAndResistance(3.0F, 3.0F).doesNotBlockMovement()).setRegistryName("portal_omega");
    public static Block DARK_OBSIDIAN = new Block(Block.Properties.create(Material.ROCK, MaterialColor.BLACK).hardnessAndResistance(50.0F, 1200.0F).harvestLevel(3).lightValue(6)).setRegistryName("dark_obsidian");
    public static Block PORTAL_OBSIDIAN_BLOCK = new ObsidianPortalBlock(Block.Properties.create(Material.PORTAL).hardnessAndResistance(3.0F, 3.0F).doesNotBlockMovement()).setRegistryName("portal_obsidian");
    public static Block[] BLOCKS = new Block[]{FP_BLOCK, PORTAL_OMEGA_BLOCK, DARK_OBSIDIAN, PORTAL_OBSIDIAN_BLOCK};
}
