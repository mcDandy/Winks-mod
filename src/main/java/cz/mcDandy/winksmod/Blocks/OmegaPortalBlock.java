package cz.mcDandy.winksmod.Blocks;

import cz.mcDandy.winksmod.Dimensions.ModDimensions;
import cz.mcDandy.winksmod.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@Utils.NoAutomaticBlockItem
public class OmegaPortalBlock extends FallingBlock {

	public OmegaPortalBlock(Properties properties) {
		super(properties);
	}

	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && VoxelShapes.compare(VoxelShapes.create(entityIn.getBoundingBox().offset((double) (-pos.getX()), (double) (-pos.getY()), (double) (-pos.getZ()))), state.getShape(worldIn, pos), IBooleanFunction.AND)) {
			entityIn.changeDimension(worldIn.dimension.getType() == ModDimensions.DIM_OMEGA ? DimensionType.OVERWORLD : ModDimensions.DIM_OMEGA);
		}
	}
}
