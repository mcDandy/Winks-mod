package cz.mcDandy.winksmod.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class MagicRepeater extends BlockHorizontal {
	protected static final AxisAlignedBB Magic_Repeater = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

	public static boolean isDiode(IBlockState state) {
		return Blocks.UNPOWERED_REPEATER.isSameDiode(state) || Blocks.UNPOWERED_COMPARATOR.isSameDiode(state);
	}

	/** Tells whether the repeater is powered or not */
	protected boolean isRepeaterPowered;

	Long PowerPattern = 0L;

	protected MagicRepeater(boolean powered) {
		super(Material.CIRCUITS);
		this.isRepeaterPowered = powered;
	}

	protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		BlockPos blockpos = pos.offset(enumfacing);
		int i = worldIn.getRedstonePower(blockpos, enumfacing);

		if (i >= 15) {
			return i;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(blockpos);
			return Math.max(i,
					iblockstate.getBlock() == Blocks.REDSTONE_WIRE
							? iblockstate.getValue(BlockRedstoneWire.POWER).intValue()
							: 0);
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isFullCube();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isFullCube() ? super.canPlaceBlockAt(worldIn, pos) : false;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change
	 * based on its state.
	 */
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		return 15;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return Magic_Repeater;
	}

	protected abstract int getDelay(IBlockState state);

	protected abstract IBlockState getPoweredState(IBlockState unpoweredState);

	protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return this.isAlternateInput(iblockstate)
				? (block == Blocks.REDSTONE_BLOCK ? 15
						: (block == Blocks.REDSTONE_WIRE ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue()
								: worldIn.getStrongPower(pos, side)))
				: 0;
	}

	protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		EnumFacing enumfacing1 = enumfacing.rotateY();
		EnumFacing enumfacing2 = enumfacing.rotateYCCW();
		return Math.max(this.getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1),
				this.getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getWeakPower(blockAccess, pos, side);
	}

	protected int getTickDelay(IBlockState state) {
		return this.getDelay(state);
	}

	protected abstract IBlockState getUnpoweredState(IBlockState poweredState);

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !this.isPowered(blockState) ? 0
				: (blockState.getValue(FACING) == side ? this.getActiveSignal(blockAccess, pos, blockState) : 0);
	}

	protected boolean isAlternateInput(IBlockState state) {
		return state.canProvidePower();
	}

	@Override
	public boolean isAssociatedBlock(Block other) {
		return this.isSameDiode(other.getDefaultState());
	}

	public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING).getOpposite();
		BlockPos blockpos = pos.offset(enumfacing);
		return isDiode(worldIn.getBlockState(blockpos)) ? worldIn.getBlockState(blockpos).getValue(FACING) != enumfacing
				: false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	protected boolean isPowered(IBlockState state) {
		return this.isRepeaterPowered;
	}

	public boolean isSameDiode(IBlockState state) {
		Block block = state.getBlock();
		return block == this.getPoweredState(this.getDefaultState()).getBlock()
				|| block == this.getUnpoweredState(this.getDefaultState()).getBlock();
	}

	/**
	 * Called when a neighboring block was changed and marks that this state should
	 * perform any checks during a neighbor change. Cases may include when redstone
	 * power is updated, cactus blocks popping off due to a neighboring solid block,
	 * etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (this.canBlockStay(worldIn, pos)) {
			this.updateState(worldIn, pos, state);
		} else {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);

			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}

	protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		BlockPos blockpos = pos.offset(enumfacing.getOpposite());
		if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos),
				java.util.EnumSet.of(enumfacing.getOpposite()), false).isCanceled())
			return;
		worldIn.neighborChanged(blockpos, this, pos);
		worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity
	 * is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.notifyNeighbors(worldIn, pos, state);
	}

	/**
	 * Called when a player destroys this Block
	 */
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		if (this.isRepeaterPowered) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}

		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place
	 * logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (this.shouldBePowered(worldIn, pos, state)) {
			worldIn.scheduleUpdate(pos, this, 1);
		}
	}

	/**
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops to
	 * grow, etc.)
	 */
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
	}

	/*
	 * ======================================== FORGE START
	 * =====================================
	 */
	@Override
	public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
		if (super.rotateBlock(world, pos, axis)) {
			IBlockState state = world.getBlockState(pos);
			state = getUnpoweredState(state);
			world.setBlockState(pos, state);

			if (shouldBePowered(world, pos, state)) {
				world.scheduleUpdate(pos, this, 1);

			}
			return true;
		}
		return false;
	}

	protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
		return this.calculateInputStrength(worldIn, pos, state) > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return side.getAxis() != EnumFacing.Axis.Y;
	}

	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.isLocked(worldIn, pos, state)) {
			boolean flag = this.shouldBePowered(worldIn, pos, state);

			if (this.isRepeaterPowered != flag && !worldIn.isBlockTickPending(pos, this)) {
				int i = -1;

				if (this.isFacingTowardsRepeater(worldIn, pos, state)) {
					i = -3;
				} else if (this.isRepeaterPowered) {
					i = -2;
				}

				worldIn.updateBlockTick(pos, this, this.getDelay(state), i);
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (PowerPattern % 2 == 1) {
			this.isRepeaterPowered = true;
		} else {
			this.isRepeaterPowered = false;
		}
		PowerPattern = PowerPattern / 2;

		if (!this.isLocked(worldIn, pos, state)) {
			boolean flag = this.shouldBePowered(worldIn, pos, state);

			if (this.isRepeaterPowered && !flag) {
				worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
			} else if (!this.isRepeaterPowered) {
				worldIn.setBlockState(pos, this.getPoweredState(state), 2);

				if (!flag) {
					worldIn.updateBlockTick(pos, this.getPoweredState(state).getBlock(), this.getTickDelay(state), -1);
				}
			}

		}
	}
}