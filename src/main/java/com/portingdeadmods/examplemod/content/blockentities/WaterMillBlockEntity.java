package com.portingdeadmods.examplemod.content.blockentities;

import com.portingdeadmods.examplemod.api.blockentities.GeneratorBlockEntity;
import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WaterMillBlockEntity extends MachineBlockEntity implements GeneratorBlockEntity {
    public WaterMillBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IRMachines.WATER_MILL, blockPos, blockState);
    }

    @Override
    public int getGenerationAmount() {
        return 0;
    }
}
