package cz.mcDandy.winksmod.Teleporters;

import java.util.*;
import java.util.function.Function;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import cz.mcDandy.winksmod.Blocks.ModBlocks;
import cz.mcDandy.winksmod.Blocks.OmegaPortalBlock;
import cz.mcDandy.winksmod.Main;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class OmegaTeleporter implements net.minecraftforge.common.util.ITeleporter {
    protected final Map<ColumnPos, PortalPosition> destinationCoordinateCache = Maps.newHashMapWithExpectedSize(4096);
    private final Object2LongMap<ColumnPos> columnMap = new Object2LongOpenHashMap<>();

    public boolean placeInPortal(ServerWorld world, Entity entity, float yaw) {
        Vec3d vec3d = entity.getLastPortalVec();
        Direction direction = entity.getTeleportDirection();
        BlockPattern.PortalInfo pattern = this.placeInExistingPortal(world, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()), entity.getMotion(), direction, vec3d.x, vec3d.y, entity instanceof PlayerEntity);
        if (pattern == null) {
            return false;
        } else {
            Vec3d position = pattern.pos;
            Vec3d motion = pattern.motion;
            entity.setMotion(motion);
            entity.rotationYaw = yaw + (float) pattern.rotation;
            entity.setLocationAndAngles(position.x, position.y, position.z,entity.rotationYaw,entity.rotationPitch);
            return true;
        }
    }

    @Nullable
    public BlockPattern.PortalInfo placeInExistingPortal(ServerWorld world, BlockPos pos, Vec3d motion, Direction direction, double x, double y, boolean isPlayer) {
        boolean isFrame = true;
        BlockPos blockpos = null;
        ColumnPos columnpos = new ColumnPos(pos);
        if (!isPlayer && this.columnMap.containsKey(columnpos)) {
            return null;
        } else {
            OmegaTeleporter.PortalPosition position = this.destinationCoordinateCache.get(columnpos);
            if (position != null) {
                blockpos = position.pos;
                position.lastUpdateTime = world.getGameTime();
                isFrame = false;
            } else {
                double dist = Double.MAX_VALUE;

                for(int eX = -128; eX <= 128; ++eX) {
                    BlockPos blockpos2;
                    for(int eZ = -128; eZ <= 128; ++eZ) {
                        for(BlockPos blockpos1 = pos.add(eX, world.getActualHeight() - 1 - pos.getY(), eZ); blockpos1.getY() >= 0; blockpos1 = blockpos2) {
                            blockpos2 = blockpos1.down();
                            if (world.getBlockState(blockpos1).getBlock() == ModBlocks.PORTAL_OMEGA_BLOCK) {
                                for(blockpos2 = blockpos1.down(); world.getBlockState(blockpos2).getBlock() == ModBlocks.PORTAL_OMEGA_BLOCK; blockpos2 = blockpos2.down()) {
                                    blockpos1 = blockpos2;
                                }

                                double distance = blockpos1.distanceSq(pos);
                                if (dist < 0.0D || distance < dist) {
                                    dist = distance;
                                    blockpos = blockpos1;
                                }
                            }
                        }
                    }
                }
            }

            if (blockpos == null) {
                long factor = world.getGameTime() + 300L;
                this.columnMap.put(columnpos, factor);
                return null;
            } else {
                if (isFrame) {
                    this.destinationCoordinateCache.put(columnpos, new OmegaTeleporter.PortalPosition(blockpos, world.getGameTime()));
                    world.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3, new BlockPos(columnpos.x, blockpos.getY(), columnpos.z));
                }

                BlockPattern.PatternHelper helper = OmegaPortalBlock.createPatternHelper(world, blockpos);
                return helper.getPortalInfo(direction, blockpos, y, motion, x);
            }
        }
    }

    public void makePortal(ServerWorld world, Entity entity) {
        Random random = new Random(world.getSeed());
        double d0 = -1.0D;
        int entityX = MathHelper.floor(entity.getPosX());
        int entityY = MathHelper.floor(entity.getPosY());
        int entityZ = MathHelper.floor(entity.getPosZ());
        int xPos = entityX;
        int yPos = entityY;
        int zPos = entityZ;
        int baseAxis = 0;
        int randAxis = random.nextInt(4);
        BlockPos.Mutable blockposm = new BlockPos.Mutable();

        for (int originX = entityX - 16; originX <= entityX + 16; ++originX) {
            double ePosX = (double) originX + 0.5D - entity.getPosX();

            for (int originZ = entityZ - 16; originZ <= entityZ + 16; ++originZ) {
                double ePosZ = (double) originZ + 0.5D - entity.getPosZ();

                searchpos:
                for (int originY = world.getActualHeight() - 1; originY >= 0; --originY) {
                    if (world.isAirBlock(blockposm.setPos(originX, originY, originZ))) {
                        while (originY > 0 && world.isAirBlock(blockposm.setPos(originX, originY - 1, originZ))) {
                            --originY;
                        }

                        for (int k3 = randAxis; k3 < randAxis + 4; ++k3) {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;
                            if (k3 % 4 >= 2) {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for (int j4 = 0; j4 < 3; ++j4) {
                                for (int k4 = 0; k4 < 4; ++k4) {
                                    for (int portalHeight = -1; portalHeight < 4; ++portalHeight) {
                                        int sPosX = originX + (k4 - 1) * l3 + j4 * i4;
                                        int sPosY = originY + portalHeight;
                                        int sPosZ = originZ + (k4 - 1) * i4 - j4 * l3;
                                        blockposm.setPos(sPosX, sPosY, sPosZ);
                                        if (portalHeight < 0 && !world.getBlockState(blockposm).getMaterial().isSolid() || portalHeight >= 0 && !world.isAirBlock(blockposm)) {
                                            continue searchpos;
                                        }
                                    }
                                }
                            }

                            double ePosY = (double) originY + 0.5D - entity.getPosY();
                            double eArea = ePosX * ePosX + ePosY * ePosY + ePosZ * ePosZ;
                            if (d0 < 0.0D || eArea < d0) {
                                d0 = eArea;
                                xPos = originX;
                                yPos = originY;
                                zPos = originZ;
                                baseAxis = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for (int originX2 = entityX - 16; originX2 <= entityX + 16; ++originX2) {
                double ePosX2 = (double) originX2 + 0.5D - entity.getPosX();

                for (int originZ2 = entityZ - 16; originZ2 <= entityZ + 16; ++originZ2) {
                    double ePosZ2 = (double) originZ2 + 0.5D - entity.getPosZ();

                    label214:
                    for (int originY2 = world.getActualHeight() - 1; originY2 >= 0; --originY2) {
                        if (world.isAirBlock(blockposm.setPos(originX2, originY2, originZ2))) {
                            while (originY2 > 0 && world.isAirBlock(blockposm.setPos(originX2, originY2 - 1, originZ2))) {
                                --originY2;
                            }

                            for (int l7 = randAxis; l7 < randAxis + 2; ++l7) {
                                int l8 = l7 % 2;
                                int k9 = 1 - l8;

                                for (int i10 = 0; i10 < 4; ++i10) {
                                    for (int portalHeight2 = -1; portalHeight2 < 4; ++portalHeight2) {
                                        int sPosX2 = originX2 + (i10 - 1) * l8;
                                        int sPosY2 = originY2 + portalHeight2;
                                        int sPosZ2 = originZ2 + (i10 - 1) * k9;
                                        blockposm.setPos(sPosX2, sPosY2, sPosZ2);
                                        if (portalHeight2 < 0 && !world.getBlockState(blockposm).getMaterial().isSolid() || portalHeight2 >= 0 && !world.isAirBlock(blockposm)) {
                                            continue label214;
                                        }
                                    }
                                }

                                double ePosY2 = (double) originY2 + 0.5D - entity.getPosY();
                                double eArea2 = ePosX2 * ePosX2 + ePosY2 * ePosY2 + ePosZ2 * ePosZ2;
                                if (d0 < 0.0D || eArea2 < d0) {
                                    d0 = eArea2;
                                    xPos = originX2;
                                    yPos = originY2;
                                    zPos = originZ2;
                                    baseAxis = l7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int pStructureX = xPos;
        int pStructureY = yPos;
        int pStructureZ = zPos;
        int xAxis = baseAxis % 2;
        int zAxis = 1 - xAxis;
        if (baseAxis % 4 >= 2) {
            xAxis = -xAxis;
            zAxis = -zAxis;
        }
        Main.LOGGER.debug(blockposm);
        if (d0 < 0.0D) {
            yPos = MathHelper.clamp(yPos, 70, world.getActualHeight() - 10);
            pStructureY = yPos;

            for (int j7 = -1; j7 <= 1; ++j7) {
                for (int i8 = 1; i8 < 3; ++i8) {
                    for (int i9 = -1; i9 < 3; ++i9) {
                        int frameX = pStructureX + (i8 - 1) * xAxis + j7 * zAxis;
                        int frameY = pStructureY + i9;
                        int frameZ = pStructureZ + (i8 - 1) * zAxis - j7 * xAxis;
                        boolean flag = i9 < 0;
                        blockposm.setPos(frameX, frameY, frameZ);
                        world.setBlockState(blockposm, flag ? Blocks.BLUE_ICE.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        for (int fWidth = -1; fWidth < 3; ++fWidth) {
            for (int fHeight = -1; fHeight < 4; ++fHeight) {
                if (fWidth == -1 || fWidth == 2 || fHeight == -1 || fHeight == 3) {
                    blockposm.setPos(pStructureX + fWidth * xAxis, pStructureY + fHeight, pStructureZ + fWidth * zAxis);
                    world.setBlockState(blockposm, Blocks.BLUE_ICE.getDefaultState(), 3);
                }
            }
        }

        BlockState portal = ModBlocks.PORTAL_OMEGA_BLOCK.getDefaultState().with(OmegaPortalBlock.AXIS, xAxis == 0 ? Direction.Axis.Z : Direction.Axis.X);

        for (int pWidth = 0; pWidth < 2; ++pWidth) {
            for (int pHeight = 0; pHeight < 3; ++pHeight) {
                blockposm.setPos(pStructureX + pWidth * xAxis, pStructureY + pHeight, pStructureZ + pWidth * zAxis);
                world.setBlockState(blockposm, portal, 18);
            }
        }

    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false);

        if (!placeInPortal(destWorld, newEntity, newEntity.rotationYaw)) {
            makePortal(destWorld, newEntity);
            placeInPortal(destWorld, newEntity, newEntity.rotationYaw);
        }

        return newEntity;
    }

    static class PortalPosition {
        public final BlockPos pos;
        public long lastUpdateTime;

        public PortalPosition(BlockPos pos, long time) {
            this.pos = pos;
            this.lastUpdateTime = time;
        }
    }
}