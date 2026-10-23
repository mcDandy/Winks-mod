package cz.mcdandy.winksmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** The falling "fairy powder" block. Its colour is what the falling dust particles use. */
public class FairyDustBlock extends FallingBlock {
    /** ARGB colour of the dust particles, previously passed in as {@code Color.MAGENTA.getRGB()}. */
    public static final int MAGENTA = 0xFFFF00FF;

    private final int dustColor;

    public FairyDustBlock(int dustColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.dustColor = dustColor;
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return dustColor;
    }
}
