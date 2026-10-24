package cz.mcdandy.winksmod.block;

import com.mojang.logging.LogUtils;
import cz.mcdandy.winksmod.WinksMod;
import cz.mcdandy.winksmod.worldgen.WinksPortalShape;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.BlockUtil;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

/**
 * The portal block of both custom dimensions.
 *
 * <p>Vanilla's {@link NetherPortalBlock} is the template here: the block stores which axis its opening faces,
 * breaks itself when the frame around it disappears and teleports whatever touches it into (or back out of) the
 * dimension it was wired to in {@link cz.mcdandy.winksmod.worldgen.ModDimensions}.
 */
public class WinksPortalBlock extends Block implements Portal {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    private static final int SEARCH_RADIUS = 16;
    private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateHorizontalAxis(Block.column(4.0, 16.0, 0.0, 16.0));

    private final Block frameBlock;
    private final ResourceKey<Level> dimension;

    public WinksPortalBlock(Block frameBlock, ResourceKey<Level> dimension, BlockBehaviour.Properties properties) {
        super(properties);
        this.frameBlock = frameBlock;
        this.dimension = dimension;
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    public ResourceKey<Level> dimension() {
        return dimension;
    }

    @Override
    protected VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(AXIS));
    }

    @Override
    protected BlockState updateShape(
        BlockState state,
        LevelReader level,
        ScheduledTickAccess ticks,
        BlockPos pos,
        Direction directionToNeighbour,
        BlockPos neighbourPos,
        BlockState neighbourState,
        RandomSource random
    ) {
        Direction.Axis updateAxis = directionToNeighbour.getAxis();
        Direction.Axis axis = state.getValue(AXIS);
        boolean wrongAxis = axis != updateAxis && updateAxis.isHorizontal();

        return !wrongAxis && !neighbourState.is(this) && WinksPortalShape.findFilled(level, pos, frameBlock, this) == null
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void entityInside(
        BlockState state, Level level, BlockPos pos, Entity entity, net.minecraft.world.entity.InsideBlockEffectApplier effectApplier, boolean isPrecise
    ) {
        if (entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    /**
     * Same rules as a vanilla nether portal: instant for a player in creative/spectator (an invulnerable ability
     * set) and {@code players_nether_portal_default_delay} ticks, 80 by default, in survival/adventure. Other
     * entities always go through the {@link Portal#getPortalTransitionTime} default of 0.
     */
    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        return entity instanceof Player player
            ? Math.max(
                0,
                level.getGameRules()
                    .get(player.getAbilities().invulnerable ? GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY)
            )
            : 0;
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel current, Entity entity, BlockPos entryPos) {
        ResourceKey<Level> target = current.dimension() == dimension ? Level.OVERWORLD : dimension;
        ServerLevel destination = current.getServer().getLevel(target);

        if (destination == null) {
            LOGGER.warn("Dimension {} is not loaded, cannot teleport through {}", target.identifier(), this);
            return null;
        }

        double scale = net.minecraft.world.level.dimension.DimensionType.getTeleportationScale(current.dimensionType(), destination.dimensionType());
        BlockPos approximateExit = destination.getWorldBorder().clampToBounds(
            Mth.floor(entity.getX() * scale), Mth.floor(entity.getY()), Mth.floor(entity.getZ() * scale)
        );

        BlockUtil.FoundRectangle exitPortal;
        Optional<BlockPos> existing = WinksPortalShape.findClosest(destination, approximateExit, SEARCH_RADIUS, this);

        if (existing.isPresent()) {
            WinksPortalShape shape = WinksPortalShape.findFilled(destination, existing.get(), frameBlock, this);

            if (shape == null) {
                return null;
            }

            exitPortal = shape.asRectangle();
        } else {
            Optional<BlockUtil.FoundRectangle> created = WinksPortalShape.createPortal(destination, approximateExit, frameBlock, this);

            if (created.isEmpty()) {
                LOGGER.warn("Unable to create a {} portal around {}", WinksMod.MODID, approximateExit);
                return null;
            }

            exitPortal = created.get();
        }

        Direction.Axis sourceAxis = current.getBlockState(entryPos).getOptionalValue(AXIS).orElse(Direction.Axis.X);
        BlockUtil.FoundRectangle sourceRect = BlockUtil.getLargestRectangleAround(
            entryPos, sourceAxis, WinksPortalShape.MAX_WIDTH, Direction.Axis.Y, WinksPortalShape.MAX_HEIGHT, pos -> current.getBlockState(pos).is(this)
        );
        Vec3 offset = entity.getRelativePortalPosition(sourceAxis, sourceRect);

        return createTransition(
            destination, exitPortal, sourceAxis, offset, entity, TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
        );
    }

    private static TeleportTransition createTransition(
        ServerLevel destination,
        BlockUtil.FoundRectangle found,
        Direction.Axis portalAxis,
        Vec3 offset,
        Entity entity,
        TeleportTransition.PostTeleportTransition postTeleportTransition
    ) {
        BlockPos minCorner = found.minCorner;
        Direction.Axis axis = destination.getBlockState(minCorner).getOptionalValue(AXIS).orElse(Direction.Axis.X);
        double width = found.axis1Size;
        double height = found.axis2Size;
        EntityDimensions dimensions = entity.getDimensions(entity.getPose());
        int rotation = portalAxis == axis ? 0 : 90;
        boolean xAligned = axis == Direction.Axis.X;
        double offsetRight = dimensions.width() / 2.0D + (width - dimensions.width()) * offset.x();
        double offsetUp = (height - dimensions.height()) * offset.y();
        double offsetForward = 0.5D + offset.z();
        Vec3 targetPos = new Vec3(
            minCorner.getX() + (xAligned ? offsetRight : offsetForward),
            minCorner.getY() + offsetUp,
            minCorner.getZ() + (xAligned ? offsetForward : offsetRight)
        );
        Vec3 collisionFree = PortalShape.findCollisionFreePosition(targetPos, destination, entity, dimensions);

        return new TeleportTransition(
            destination,
            collisionFree,
            Vec3.ZERO,
            rotation,
            0.0F,
            Relative.union(Relative.DELTA, Relative.ROTATION),
            postTeleportTransition
        );
    }

    /** Nausea while travelling, like the old portals did. */
    @Override
    public Portal.Transition getLocalTransition() {
        return Transition.CONFUSION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D,
                SoundEvents.PORTAL_AMBIENT,
                SoundSource.BLOCKS,
                0.5F,
                random.nextFloat() * 0.4F + 0.8F,
                false
            );
        }
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                default -> state;
            };
            default -> state;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
