package cz.mcdandy.winksmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class SunLightBlockEntity extends BlockEntity {
    /** How long a single point of light lasts before it fades away. */
    public static final int TICKS_PER_LEVEL = 5;

    private static final String TAG_TICKS_UNTIL_FADE = "ticks_until_fade";

    /** Ticks left before the light drops by one. Saved, so an unloaded patch does not restart the fade. */
    private int ticksUntilFade = TICKS_PER_LEVEL;

    public SunLightBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.SUN_LIGHT.get(), worldPosition, blockState);
    }

    /** Server tick of a placed sun light: counts down and hands the new (lower) state back to the level. */
    public static void serverTick(Level level, BlockPos pos, BlockState state, SunLightBlockEntity entity) {
        if (--entity.ticksUntilFade > 0) {
            return;
        }

        int faded = state.getValue(SunLightBlock.LEVEL) - 1;
        entity.ticksUntilFade = TICKS_PER_LEVEL;

        if (faded <= 0) {
            level.removeBlock(pos, false);
        } else {
            level.setBlockAndUpdate(pos, state.setValue(SunLightBlock.LEVEL, faded));
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt(TAG_TICKS_UNTIL_FADE, this.ticksUntilFade);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.getInt(TAG_TICKS_UNTIL_FADE).ifPresent(value -> this.ticksUntilFade = value);
    }
}
