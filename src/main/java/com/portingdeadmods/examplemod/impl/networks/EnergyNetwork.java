package com.portingdeadmods.examplemod.impl.networks;

import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.api.blocks.PipeBlock;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.content.blocks.BurntCableBlock;
import com.portingdeadmods.examplemod.content.blocks.CableBlock;
import com.portingdeadmods.examplemod.registries.IRBlocks;
import com.portingdeadmods.portingdeadlibs.utils.capabilities.CapabilityUtils;
import com.thepigcat.transportlib.api.NetworkNode;
import com.thepigcat.transportlib.api.cache.NetworkRoute;
import com.thepigcat.transportlib.impl.TransportNetworkImpl;
import com.thepigcat.transportlib.utils.NetworkHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class EnergyNetwork extends TransportNetworkImpl<Integer> {
    protected EnergyNetwork(Builder<Integer> builder) {
        super(builder);
    }

    @Override
    public Integer transport(ServerLevel serverLevel, BlockPos pos, Integer value, Direction... directions) {
        // TODO: Get cap of block instead of be
        BlockEntity blockEntity = serverLevel.getBlockEntity(pos);
        if (blockEntity != null) {
            EnergyHandler euStorage = CapabilityUtils.blockEntityCapability(IRCapabilities.ENERGY_BLOCK, blockEntity);
            if (euStorage != null) {
                Direction[] directions1;
                if (directions.length == 0) {
                    directions1 = Direction.values();
                } else {
                    directions1 = directions;
                }
                for (Direction direction : directions1) {
                    NetworkNode<Integer> node = this.getNodeAt(serverLevel, pos.relative(direction));
                    if (node != null) {
                        int remainder = super.transport(serverLevel, pos, value, directions);
                        if (euStorage.getEnergyTier() != this.getEnergyTierOfCable(serverLevel, pos.relative(direction)) && value > 0) {
                            List<NetworkRoute<Integer>> routes = this.getRouteCache(serverLevel).routes().get(pos);
                            if (routes != null && !routes.isEmpty()) {
                                NetworkNode<Integer> nextNode = this.findNextNode(node, serverLevel, pos.relative(direction), direction);
                                if (nextNode != null) {
                                    NetworkHelper.iterBlocksBetweenNodes(node, nextNode, pos1 -> {
                                        serverLevel.setBlockAndUpdate(pos1, copyConnections(serverLevel.getBlockState(pos1), IRBlocks.BURNT_CABLE.get().defaultBlockState()
                                                .setValue(BurntCableBlock.BURNT, true)));
                                    });
                                    serverLevel.setBlockAndUpdate(node.getPos(), copyConnections(serverLevel.getBlockState(node.getPos()), IRBlocks.BURNT_CABLE.get().defaultBlockState()
                                            .setValue(BurntCableBlock.BURNT, true)));
                                    serverLevel.setBlockAndUpdate(nextNode.getPos(), copyConnections(serverLevel.getBlockState(nextNode.getPos()), IRBlocks.BURNT_CABLE.get().defaultBlockState()
                                            .setValue(BurntCableBlock.BURNT, true)));
                                }
                            }
                        }
                        return remainder;
                    }
                }
            }
        }
        return value;
    }

    private static BlockState copyConnections(BlockState prevState, BlockState newState) {
        BlockState state0 = newState;
        for (Direction direction : Direction.values()) {
            if (prevState.hasProperty(PipeBlock.CONNECTION[direction.get3DDataValue()])) {
                state0 = state0.setValue(PipeBlock.CONNECTION[direction.get3DDataValue()], prevState.getValue(PipeBlock.CONNECTION[direction.get3DDataValue()]));
            }
        }
        return state0;
    }

    private EnergyTier getEnergyTierOfCable(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof CableBlock energyTierBlock ? energyTierBlock.getEnergyTier() : null;
    }

    public static EnergyNetwork build(Builder<Integer> builder) {
        return new EnergyNetwork(builder);
    }

}
