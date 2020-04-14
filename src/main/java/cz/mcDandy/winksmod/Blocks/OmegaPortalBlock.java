package cz.mcDandy.winksmod.Blocks;

import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import cz.mcDandy.winksmod.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;


import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.DimensionManager;

@Utils.NoAutomaticBlockItem
public class OmegaPortalBlock extends Block {

	public OmegaPortalBlock(Properties properties) {
		super(properties);
	}



	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (DimensionType.byName(ModDimensions.OMEGA_RL) == null) {
			DimensionManager.registerDimension(ModDimensions.OMEGA_RL, ModDimensions.DIM_OMEGA, null, false);
		}
		player.changeDimension(worldIn.dimension.getType() == DimensionType.byName(ModDimensions.OMEGA_RL) ? DimensionType.OVERWORLD : DimensionType.byName(ModDimensions.OMEGA_RL));

		return true;
	}
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	/*public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && VoxelShapes.compare(VoxelShapes.create(entityIn.getBoundingBox().offset((double) (-pos.getX()), (double) (-pos.getY()), (double) (-pos.getZ()))), state.getShape(worldIn, pos), IBooleanFunction.AND)) {
			entityIn.changeDimension(worldIn.dimension.getType() == ModDimensions.DIM_OMEGA ? DimensionType.OVERWORLD : ModDimensions.DIM_OMEGA);
		}
	}*/
}
