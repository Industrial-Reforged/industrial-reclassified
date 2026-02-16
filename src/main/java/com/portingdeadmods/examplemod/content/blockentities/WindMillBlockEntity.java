package com.portingdeadmods.examplemod.content.blockentities;

import com.portingdeadmods.examplemod.api.blockentities.GeneratorBlockEntity;
import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WindMillBlockEntity extends MachineBlockEntity implements GeneratorBlockEntity {
    public WindMillBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IRMachines.WIND_MILL, blockPos, blockState);
    }

    @Override
    public int getGenerationAmount() {
        return 0;
    }


}
