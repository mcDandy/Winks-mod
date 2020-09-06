package cz.mcDandy.winksmod.Blocks;

import com.google.common.cache.LoadingCache;
import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import cz.mcDandy.winksmod.Teleporters.OmegaTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class OmegaPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public OmegaPortalBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.X));
    }

    @Override
    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case Z:
                return Z_AABB;
            case X:
            default:
                return X_AABB;
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        Direction.Axis directionAxis = facing.getAxis();
        Direction.Axis directionAxis1 = stateIn.get(AXIS);
        boolean flag = directionAxis1 != directionAxis && directionAxis.isHorizontal();
        return !flag && facingState.getBlock() != this && !(new Size(worldIn, currentPos, directionAxis1)).canCreatePortal() ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
        if (!entity.isPassenger() && !entity.isBeingRidden() && entity.isNonBoss()) {
            if (entity.timeUntilPortal > 0) {
                entity.timeUntilPortal = entity.getPortalCooldown();
            } else {
                if (!entity.world.isRemote && !pos.equals(entity)) {
                    entity.setPortal(new BlockPos(pos));
                }

                if (entity.world instanceof ServerWorld) {
                    if (!entity.isPassenger()) {
                        entity.timeUntilPortal = entity.getPortalCooldown();
                        DimensionType type = worldIn.dimension.getType() == DimensionType.byName(ModDimensions.OMEGA_RL) ? DimensionType.OVERWORLD : DimensionType.byName(ModDimensions.OMEGA_RL);
                        entity.changeDimension(type, new OmegaTeleporter((ServerWorld) entity.world));
                    }
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(100) == 0) {
            worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        switch (rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.get(AXIS)) {
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public static BlockPattern.PatternHelper createPatternHelper(IWorld p_181089_0_, BlockPos worldIn) {
        Direction.Axis direction$axis = Direction.Axis.Z;
        OmegaPortalBlock.Size omegaportalblock$size = new OmegaPortalBlock.Size(p_181089_0_, worldIn, Direction.Axis.X);
        LoadingCache<BlockPos, CachedBlockInfo> loadingcache = BlockPattern.createLoadingCache(p_181089_0_, true);
        if (!omegaportalblock$size.isValid()) {
            direction$axis = Direction.Axis.X;
            omegaportalblock$size = new OmegaPortalBlock.Size(p_181089_0_, worldIn, Direction.Axis.Z);
        }

        if (!omegaportalblock$size.isValid()) {
            return new BlockPattern.PatternHelper(worldIn, Direction.NORTH, Direction.UP, loadingcache, 1, 1, 1);
        } else {
            int[] aint = new int[Direction.AxisDirection.values().length];
            Direction direction = omegaportalblock$size.rightDir.rotateYCCW();
            BlockPos blockpos = omegaportalblock$size.bottomLeft.up(omegaportalblock$size.getHeight() - 1);

            for(Direction.AxisDirection direction$axisdirection : Direction.AxisDirection.values()) {
                BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(direction.getAxisDirection() == direction$axisdirection ? blockpos : blockpos.offset(omegaportalblock$size.rightDir, omegaportalblock$size.getWidth() - 1), Direction.getFacingFromAxis(direction$axisdirection, direction$axis), Direction.UP, loadingcache, omegaportalblock$size.getWidth(), omegaportalblock$size.getHeight(), 1);

                for(int i = 0; i < omegaportalblock$size.getWidth(); ++i) {
                    for(int j = 0; j < omegaportalblock$size.getHeight(); ++j) {
                        CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.translateOffset(i, j, 1);
                        if (!cachedblockinfo.getBlockState().isAir()) {
                            ++aint[direction$axisdirection.ordinal()];
                        }
                    }
                }
            }

            Direction.AxisDirection direction$axisdirection1 = Direction.AxisDirection.POSITIVE;

            for(Direction.AxisDirection direction$axisdirection2 : Direction.AxisDirection.values()) {
                if (aint[direction$axisdirection2.ordinal()] < aint[direction$axisdirection1.ordinal()]) {
                    direction$axisdirection1 = direction$axisdirection2;
                }
            }

            return new BlockPattern.PatternHelper(direction.getAxisDirection() == direction$axisdirection1 ? blockpos : blockpos.offset(omegaportalblock$size.rightDir, omegaportalblock$size.getWidth() - 1), Direction.getFacingFromAxis(direction$axisdirection1, direction$axis), Direction.UP, loadingcache, omegaportalblock$size.getWidth(), omegaportalblock$size.getHeight(), 1);
        }
    }

    public static class Size {
        private final IWorld world;
        private final Direction.Axis axis;
        private final Direction rightDir;
        private final Direction leftDir;
        private int portalBlockCount;
        @Nullable
        private BlockPos bottomLeft;
        private int height;
        private int width;

        public Size(IWorld worldIn, BlockPos pos, Direction.Axis axisIn) {
            this.world = worldIn;
            this.axis = axisIn;
            if (axisIn == Direction.Axis.X) {
                this.leftDir = Direction.EAST;
                this.rightDir = Direction.WEST;
            } else {
                this.leftDir = Direction.NORTH;
                this.rightDir = Direction.SOUTH;
            }

            for(BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0 && this.func_196900_a(worldIn.getBlockState(pos.down())); pos = pos.down()) {
                ;
            }

            int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
            if (i >= 0) {
                this.bottomLeft = pos.offset(this.leftDir, i);
                this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
                if (this.width < 2 || this.width > 21) {
                    this.bottomLeft = null;
                    this.width = 0;
                }
            }

            if (this.bottomLeft != null) {
                this.height = this.calculatePortalHeight();
            }

        }

        protected int getDistanceUntilEdge(BlockPos pos, Direction directionIn) {
            int i;
            for(i = 0; i < 22; ++i) {
                BlockPos blockpos = pos.offset(directionIn, i);
                if (!this.func_196900_a(this.world.getBlockState(blockpos)) || !this.world.getBlockState(blockpos.down()).isPortalFrame(this.world, blockpos.down())) {
                    break;
                }
            }

            BlockPos framePos = pos.offset(directionIn, i);
            return this.world.getBlockState(framePos).isPortalFrame(this.world, framePos) ? i : 0;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        protected int calculatePortalHeight() {
            label56:
            for(this.height = 0; this.height < 21; ++this.height) {
                for(int i = 0; i < this.width; ++i) {
                    BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                    BlockState blockstate = this.world.getBlockState(blockpos);
                    if (!this.func_196900_a(blockstate)) {
                        break label56;
                    }

                    Block block = blockstate.getBlock();
                    if (block == ModBlocks.PORTAL_OMEGA_BLOCK) {
                        ++this.portalBlockCount;
                    }

                    if (i == 0) {
                        BlockPos framePos = blockpos.offset(this.leftDir);
                        if (!this.world.getBlockState(framePos).isPortalFrame(this.world, framePos)) {
                            break label56;
                        }
                    } else if (i == this.width - 1) {
                        BlockPos framePos = blockpos.offset(this.rightDir);
                        if (!this.world.getBlockState(framePos).isPortalFrame(this.world, framePos)) {
                            break label56;
                        }
                    }
                }
            }

            for(int j = 0; j < this.width; ++j) {
                BlockPos framePos = this.bottomLeft.offset(this.rightDir, j).up(this.height);
                if (!this.world.getBlockState(framePos).isPortalFrame(this.world, framePos)) {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3) {
                return this.height;
            } else {
                this.bottomLeft = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean func_196900_a(BlockState pos) {
            Block block = pos.getBlock();
            return pos.isAir() || block == Blocks.FIRE || block == ModBlocks.PORTAL_OMEGA_BLOCK;
        }

        public boolean isValid() {
            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void placePortalBlocks() {
            for(int i = 0; i < this.width; ++i) {
                BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                for(int j = 0; j < this.height; ++j) {
                    this.world.setBlockState(blockpos.up(j), ModBlocks.PORTAL_OMEGA_BLOCK.getDefaultState().with(OmegaPortalBlock.AXIS, this.axis), 18);
                }
            }

        }

        private boolean func_196899_f() {
            return this.portalBlockCount >= this.width * this.height;
        }

        public boolean canCreatePortal() {
            return this.isValid() && this.func_196899_f();
        }
    }
}