package cz.mcDandy.winksmod;

import com.google.common.cache.LoadingCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class Size {
    private final IWorld world;
    private final Direction.Axis axis;
    public final Direction rightDir;
    public final Direction leftDir;
    public int portalBlockCount;
    public BlockPos bottomLeft;
    private int height;
    private int width;
    private final Block FRAME_BLOCK;
    private final Block PORTAL_BLOCK;

    public Size(IWorld worldIn, BlockPos pos, Direction.Axis facing, Block FrameBlock, Block PortalBlock) {
        world = worldIn;
        axis = facing;
        FRAME_BLOCK = FrameBlock;
        PORTAL_BLOCK = PortalBlock;
        if (facing == Direction.Axis.X) {
            leftDir = Direction.EAST;
            rightDir = Direction.WEST;
        } else {
            leftDir = Direction.NORTH;
            rightDir = Direction.SOUTH;
        }

        BlockPos blockpos = pos;
        while (pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && isEmptyBlock(worldIn.getBlockState(pos.down()))) {
            pos = pos.down();
        }

        int i = getDistanceUntilEdge(pos, leftDir) - 1;

        if (i >= 0) {
            bottomLeft = pos.offset(leftDir, i);
            width = this.getDistanceUntilEdge(bottomLeft, rightDir);

            if (width < 2 || width > 21) {
                bottomLeft = null;
                width = 0;
            }
        }

        if (this.bottomLeft != null) {
            height = calculatePortalHeight();
        }
    }

    int getDistanceUntilEdge(BlockPos pos, Direction facing) {
        int i;

        for (i = 0; i < 22; ++i) {
            BlockPos blockpos = pos.offset(facing, i);

            if (!isEmptyBlock(world.getBlockState(blockpos)) || world.getBlockState(blockpos.down()) != FRAME_BLOCK.getDefaultState()) {
                break;
            }
        }

        Block block = world.getBlockState(pos.offset(facing, i)).getBlock();
        return block == FRAME_BLOCK ? i : 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    int calculatePortalHeight() {
        label56:

        for (height = 0; height < 21; ++height) {
            for (int i = 0; i < width; ++i) {
                BlockPos blockpos = bottomLeft.offset(rightDir, i).up(height);
                BlockState blockstate = world.getBlockState(blockpos);

                if (!isEmptyBlock(blockstate)) {
                    break label56;
                }

                if (blockstate.getBlock() == PORTAL_BLOCK) {
                    ++this.portalBlockCount;
                }

                if (i == 0) {
                    blockstate = world.getBlockState(blockpos.offset(leftDir));

                    if (blockstate != FRAME_BLOCK.getDefaultState()) {
                        break label56;
                    }
                } else if (i == this.width) {
                    blockstate = world.getBlockState(blockpos.offset(rightDir));

                    if (blockstate != FRAME_BLOCK.getDefaultState()) {
                        break label56;
                    }
                }
            }
        }

        for (int j = 0; j < width; ++j) {
            if (world.getBlockState(bottomLeft.offset(rightDir, j).up(height)) != FRAME_BLOCK.getDefaultState()) {
                height = 0;
                break;
            }
        }

        if (height <= 21 && height >= 3) {
            return height;
        } else {
            this.bottomLeft = null;
            this.width = 0;
            this.height = 0;
            return 0;
        }
    }

    boolean isEmptyBlock(BlockState state) {
        Block block = state.getBlock();

        return state.isAir() || block == Blocks.FIRE || block == PORTAL_BLOCK;
    }

    public boolean isValid() {
        return bottomLeft != null && width >= 2 && width <= 21 && height >= 3 && this.height <= 21;
    }

    public void placePortalBlocks(EnumProperty<Direction.Axis> AXIS) {
        for (int i = 0; i < this.width; ++i) {
            BlockPos blockpos = bottomLeft.offset(rightDir, i);

            for (int j = 0; j < height; ++j) {
                world.setBlockState(blockpos.up(j), PORTAL_BLOCK.getDefaultState().with(AXIS, axis), 2);
            }
        }
    }

    private boolean isLargeEnough() {
        return this.portalBlockCount >= this.width * this.height;
    }

    public boolean canCreatePortal() {
        return this.isValid() && this.isLargeEnough();
    }

    public static BlockPattern.PatternHelper createPatternHelper(IWorld worldIn, BlockPos pos, Block frameBlock, Block portalBlock) {
        Direction.Axis axis = Direction.Axis.Z;
        Size size = new Size(worldIn, pos, Direction.Axis.X, frameBlock, portalBlock);
        LoadingCache<BlockPos, CachedBlockInfo> cache = BlockPattern.createLoadingCache(worldIn, true);
        if (!size.isValid()) {
            axis = Direction.Axis.X;
            size = new Size(worldIn, pos, Direction.Axis.Z, frameBlock, portalBlock);
        }

        if (!size.isValid()) {
            return new BlockPattern.PatternHelper(pos, Direction.NORTH, Direction.UP, cache, 1, 1, 1);
        } else {
            int[] axes = new int[Direction.AxisDirection.values().length];
            Direction direction = size.rightDir.rotateYCCW();
            BlockPos blockpos = size.bottomLeft.up(size.getHeight() - 1);

            for (Direction.AxisDirection axisDir : Direction.AxisDirection.values()) {
                BlockPattern.PatternHelper helper = new BlockPattern.PatternHelper(direction.getAxisDirection() == axisDir ? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), Direction.getFacingFromAxis(axisDir, axis), Direction.UP, cache, size.getWidth(), size.getHeight(), 1);

                for (int i = 0; i < size.getWidth(); ++i) {
                    for (int j = 0; j < size.getHeight(); ++j) {
                        CachedBlockInfo cacheInfo = helper.translateOffset(i, j, 1);
                        if (!cacheInfo.getBlockState().isAir()) {
                            ++axes[axisDir.ordinal()];
                        }
                    }
                }
            }

            Direction.AxisDirection axisDirPos = Direction.AxisDirection.POSITIVE;

            for (Direction.AxisDirection axisDir : Direction.AxisDirection.values()) {
                if (axes[axisDir.ordinal()] < axes[axisDirPos.ordinal()]) {
                    axisDirPos = axisDir;
                }
            }

            return new BlockPattern.PatternHelper(direction.getAxisDirection() == axisDirPos ? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), Direction.getFacingFromAxis(axisDirPos, axis), Direction.UP, cache, size.getWidth(), size.getHeight(), 1);
        }
    }

}
