package com.portingdeadmods.examplemod.content.blockentities;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.api.blockentities.GeneratorBlockEntity;
import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class WaterMillBlockEntity extends MachineBlockEntity implements GeneratorBlockEntity {
    public WaterMillBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IRMachines.WATER_MILL, blockPos, blockState);
    }

    @Override
    protected void tickRecipe() {
        BlockPos pos = this.worldPosition;
        BlockState state = this.getBlockState();
        BlockPos fluidPos = pos.relative(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
        BlockState facingState = this.level.getBlockState(fluidPos);
        FluidState fluidState = facingState.getFluidState();
        if (!fluidState.isEmpty()) {
            Fluid fluid = fluidState.getType();
            if (fluid instanceof FlowingFluid flowingFluid) {
                Vec3 flow = fluidState.getFlow(level, fluidPos);
                IndustrialReclassified.LOGGER.debug("Flow: {}", flow);
            }
        }
    }

    @Override
    public int getGenerationAmount() {
        return 0;
    }
}
