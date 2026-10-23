package cz.mcdandy.winksmod.block;

import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.worldgen.ModDimensions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * All blocks the mod adds. Items for these blocks are registered in {@link cz.mcdandy.winksmod.item.ModItems}.
 *
 * <p>Blocks are registered through {@code registerBlock}/{@code registerSimpleBlock} with a {@link
 * BlockBehaviour.Properties} <em>supplier</em>: modern Minecraft derives the loot table key and translation key from
 * the block's own id, which NeoForge can only stamp onto the properties once it knows that id.
 */
public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WinksMod.MODID);

    /** Fairy powder: a magenta falling block, the old {@code Fp_block}. */
    public static final DeferredBlock<FairyDustBlock> FP_BLOCK = BLOCKS.registerBlock(
        "fp_block",
        properties -> new FairyDustBlock(FairyDustBlock.MAGENTA, properties),
        () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).sound(SoundType.SAND).strength(3.0F, 3.0F)
    );

    /** The frame block of the obsidian dimension's portals. */
    public static final DeferredBlock<Block> DARK_OBSIDIAN = BLOCKS.registerSimpleBlock("dark_obsidian", () ->
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .lightLevel(state -> 6)
    );

    /** Portal between the overworld and the omega dimension; frame is blue ice. */
    public static final DeferredBlock<WinksPortalBlock> PORTAL_OMEGA = BLOCKS.registerBlock(
        "portal_omega",
        properties -> new WinksPortalBlock(Blocks.BLUE_ICE, ModDimensions.OMEGA, properties),
        () -> BlockBehaviour.Properties.of().noCollision().strength(3.0F, 3.0F)
    );

    /** Portal between the overworld and the obsidian dimension; frame is dark obsidian. */
    public static final DeferredBlock<WinksPortalBlock> PORTAL_OBSIDIAN = BLOCKS.registerBlock(
        "portal_obsidian",
        properties -> new WinksPortalBlock(DARK_OBSIDIAN.get(), ModDimensions.OBSIDIAN, properties),
        () -> BlockBehaviour.Properties.of().noCollision().strength(3.0F, 3.0F)
    );

    private ModBlocks() {
    }
}
