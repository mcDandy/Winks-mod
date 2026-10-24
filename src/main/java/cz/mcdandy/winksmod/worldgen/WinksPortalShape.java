package cz.mcdandy.winksmod.worldgen;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.Nullable;

/**
 * A rectangular portal frame built out of an arbitrary block, with the portal filling the inside.
 *
 * <p>This is the mod's own version of {@link net.minecraft.world.level.portal.PortalShape}, which is hard coded
 * to the vanilla nether portal frame. The omega and obsidian portals use frames vanilla does not know about,
 * so their shape has to be resolved here instead.
 */
public final class WinksPortalShape {
    public static final int MIN_WIDTH = 2;
    public static final int MAX_WIDTH = 21;
    public static final int MIN_HEIGHT = 3;
    public static final int MAX_HEIGHT = 21;

    private final Direction.Axis axis;
    private final BlockPos bottomLeft;
    private final int width;
    private final int height;
    private final boolean complete;

    private WinksPortalShape(Direction.Axis axis, BlockPos bottomLeft, int width, int height, boolean complete) {
        this.axis = axis;
        this.bottomLeft = bottomLeft;
        this.width = width;
        this.height = height;
        this.complete = complete;
    }

    /**
     * Tries both horizontal axes and returns the first frame around {@code pos} that is completely filled with
     * portal blocks.
     */
    public static @Nullable WinksPortalShape findFilled(LevelReader level, BlockPos pos, Block frame, Block portal) {
        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z}) {
            WinksPortalShape shape = find(level, pos, axis, frame, portal);
            if (shape != null && shape.isComplete()) {
                return shape;
            }
        }

        return null;
    }

    /** @return the frame that contains {@code pos} on the given axis, or {@code null} when there is none. */
    public static @Nullable WinksPortalShape find(LevelReader level, BlockPos pos, Direction.Axis axis, Block frame, Block portal) {
        if (axis == Direction.Axis.Y) {
            return null;
        }

        // Vanilla picks WEST for an X portal and SOUTH for a Z portal; the opposite is the other side of the frame.
        Direction rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        Direction leftDir = rightDir.getOpposite();

        BlockPos cursor = walkDownToFrame(level, pos, portal);
        int edge = distanceToEdgeAboveFrame(level, cursor, leftDir, frame, portal) - 1;
        if (edge < 0) {
            return null;
        }

        BlockPos bottomLeft = cursor.relative(leftDir, edge).immutable();
        int width = distanceToEdgeAboveFrame(level, bottomLeft, rightDir, frame, portal);
        if (width < MIN_WIDTH || width > MAX_WIDTH) {
            return null;
        }

        int[] portalBlocks = new int[1];
        int height = measureHeight(level, bottomLeft, leftDir, rightDir, width, frame, portal, portalBlocks);
        if (height < MIN_HEIGHT || height > MAX_HEIGHT) {
            return null;
        }

        return new WinksPortalShape(axis, bottomLeft, width, height, portalBlocks[0] >= width * height);
    }

    /** Drops {@code pos} down to the bottom of the opening it sits in. */
    private static BlockPos walkDownToFrame(LevelReader level, BlockPos pos, Block portal) {
        BlockPos.MutableBlockPos cursor = pos.mutable();
        int minY = Math.max(level.getMinY(), pos.getY() - MAX_HEIGHT);

        while (cursor.getY() > minY && isEmpty(level.getBlockState(cursor.below()), portal)) {
            cursor.setY(cursor.getY() - 1);
        }

        return cursor.immutable();
    }

    /** Counts the empty/portal columns until a framed edge is reached, {@code 0} when there is no frame. */
    private static int distanceToEdgeAboveFrame(LevelReader level, BlockPos pos, Direction direction, Block frame, Block portal) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int distance = 0; distance <= MAX_WIDTH; distance++) {
            cursor.set(pos).move(direction, distance);
            BlockState state = level.getBlockState(cursor);

            if (!isEmpty(state, portal)) {
                return state.is(frame) ? distance : 0;
            }

            if (!level.getBlockState(cursor.below()).is(frame)) {
                return 0;
            }
        }

        return 0;
    }

    private static int measureHeight(
        LevelReader level,
        BlockPos bottomLeft,
        Direction leftDir,
        Direction rightDir,
        int width,
        Block frame,
        Block portal,
        int[] portalBlockCount
    ) {
        int height = 0;

        while (height < MAX_HEIGHT && isOpenRow(level, bottomLeft, leftDir, rightDir, width, frame, portal, height, portalBlockCount)) {
            height++;
        }

        return hasTopFrame(level, bottomLeft, rightDir, width, frame, height) ? height : 0;
    }

    private static boolean isOpenRow(
        LevelReader level,
        BlockPos bottomLeft,
        Direction leftDir,
        Direction rightDir,
        int width,
        Block frame,
        Block portal,
        int row,
        int[] portalBlockCount
    ) {
        if (!level.getBlockState(bottomLeft.relative(leftDir).above(row)).is(frame)
                || !level.getBlockState(bottomLeft.relative(rightDir, width).above(row)).is(frame)) {
            return false;
        }

        for (int x = 0; x < width; x++) {
            BlockState state = level.getBlockState(bottomLeft.relative(rightDir, x).above(row));

            if (!isEmpty(state, portal)) {
                return false;
            }

            if (state.is(portal)) {
                portalBlockCount[0]++;
            }
        }

        return true;
    }

    private static boolean hasTopFrame(LevelReader level, BlockPos bottomLeft, Direction rightDir, int width, Block frame, int height) {
        for (int x = 0; x < width; x++) {
            if (!level.getBlockState(bottomLeft.relative(rightDir, x).above(height)).is(frame)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isEmpty(BlockState state, Block portal) {
        return state.isAir() || state.is(portal);
    }

    /** The frame only works once it is completely filled with portal blocks. */
    public boolean isComplete() {
        return complete;
    }

    public Direction.Axis axis() {
        return axis;
    }

    public BlockPos minCorner() {
        return bottomLeft;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public BlockUtil.FoundRectangle asRectangle() {
        return new BlockUtil.FoundRectangle(bottomLeft, width, height);
    }

    /** Fills the whole frame with portal blocks. */
    public void fill(Level level, Block portal) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        Direction rightDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        BlockState portalState = portal.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, axis);

        for (int x = 0; x < width; x++) {
            BlockPos column = bottomLeft.relative(rightDir, x);

            for (int y = 0; y < height; y++) {
                level.setBlock(cursor.set(column).above(y), portalState, Block.UPDATE_ALL);
            }
        }
    }

    /**
     * Scans a square of {@code radius} blocks for the closest column of portal blocks and returns its bottom.
     */
    public static Optional<BlockPos> findClosest(ServerLevel level, BlockPos origin, int radius, Block portal) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        BlockPos found = null;
        double closestDistanceSqr = -1.0D;

        for (int x = origin.getX() - radius; x <= origin.getX() + radius; x++) {
            for (int z = origin.getZ() - radius; z <= origin.getZ() + radius; z++) {
                if (!level.hasChunkAt(cursor.set(x, origin.getY(), z))) {
                    continue;
                }

                for (int y = level.getMaxY() - 1; y >= level.getMinY(); y--) {
                    if (!level.getBlockState(cursor.set(x, y, z)).is(portal)) {
                        continue;
                    }

                    while (level.getBlockState(cursor.below()).is(portal)) {
                        cursor.setY(cursor.getY() - 1);
                    }

                    BlockPos bottom = cursor.immutable();
                    double distanceSqr = bottom.distSqr(origin);

                    if (closestDistanceSqr < 0.0D || distanceSqr < closestDistanceSqr) {
                        closestDistanceSqr = distanceSqr;
                        found = bottom;
                    }

                    break;
                }
            }
        }

        return Optional.ofNullable(found);
    }

    /**
     * Builds a fresh 2x3 portal with a frame around it, close to {@code origin}.
     *
     * @return the rectangle of the new portal, or an empty optional when no flat spot was free.
     */
    public static Optional<BlockUtil.FoundRectangle> createPortal(Level level, BlockPos origin, Block frame, Block portal) {
        RandomSource random = level.getRandom();
        Direction.Axis axis = random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
        Direction across = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        Direction inwards = across.getClockWise();

        BlockPos bottomLeft = findAirPocket(level, origin, inwards);
        if (bottomLeft == null) {
            return Optional.empty();
        }

        BlockState frameState = frame.defaultBlockState();
        BlockState portalState = portal.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_AXIS, axis);

        // Frame: a wall that is 4 wide and 5 high with the middle cut out.
        for (int acrossOffset = -1; acrossOffset < 3; acrossOffset++) {
            for (int up = -1; up < 4; up++) {
                boolean inside = acrossOffset >= 0 && acrossOffset < 2 && up >= 0 && up < 3;

                if (!inside) {
                    level.setBlockAndUpdate(bottomLeft.relative(across, acrossOffset).above(up), frameState);
                }
            }
        }

        for (int acrossOffset = 0; acrossOffset < 2; acrossOffset++) {
            for (int up = 0; up < 3; up++) {
                level.setBlockAndUpdate(bottomLeft.relative(across, acrossOffset).above(up), portalState);
            }
        }

        return Optional.of(new BlockUtil.FoundRectangle(bottomLeft, 2, 3));
    }

    /** Looks for a spot with solid ground and four blocks of air above it, closest to {@code origin}. */
    private static @Nullable BlockPos findAirPocket(Level level, BlockPos origin, Direction inwards) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        double closestDistanceSqr = -1.0D;
        BlockPos found = null;

        for (int x = origin.getX() - 16; x <= origin.getX() + 16; x++) {
            for (int z = origin.getZ() - 16; z <= origin.getZ() + 16; z++) {
                for (int y = level.getMaxY() - 5; y >= level.getMinY(); y--) {
                    cursor.set(x, y, z);

                    if (!level.isEmptyBlock(cursor) || level.getBlockState(cursor.below()).isAir()) {
                        continue;
                    }

                    boolean pocketIsFree = true;

                    for (int up = 1; up < 4; up++) {
                        if (!level.isEmptyBlock(cursor.set(x, y + up, z))) {
                            pocketIsFree = false;
                            break;
                        }
                    }

                    if (!pocketIsFree) {
                        continue;
                    }

                    BlockPos candidate = cursor.set(x, y, z).immutable();
                    double distanceSqr = candidate.distSqr(origin);

                    if (closestDistanceSqr < 0.0D || distanceSqr < closestDistanceSqr) {
                        closestDistanceSqr = distanceSqr;
                        found = candidate;
                    }

                    break;
                }
            }
        }

        return found;
    }

    @Override
    public String toString() {
        return "WinksPortalShape[" + axis + " " + width + "x" + height + " at " + bottomLeft + "]";
    }
}
