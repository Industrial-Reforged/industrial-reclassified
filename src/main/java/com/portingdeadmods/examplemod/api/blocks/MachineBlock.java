package com.portingdeadmods.examplemod.api.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.registries.IRBlocks;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.ContainerBlock;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MachineBlock extends ContainerBlock {
    private final Supplier<? extends EnergyTier> energyTier;
    private BlockEntityType<? extends MachineBlockEntity> blockEntityType;
    private final boolean ticking;
    private final boolean rotatable;
    private final boolean activatable;

    public MachineBlock(Builder builder, Supplier<? extends EnergyTier> energyTier) {
        super(builder.properties);
        this.ticking = builder.ticking;
        this.rotatable = builder.rotatable;
        this.activatable = builder.activatable;
        this.energyTier = energyTier;

        BlockState defaultState = this.defaultBlockState();

        if (this.rotatable) {
            defaultState = defaultState.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
        }

        if (this.activatable) {
            defaultState = defaultState.setValue(PDLBlockStateProperties.ACTIVE, false);
        }

        this.registerDefaultState(defaultState);
    }

    public static boolean isActive(BlockState state) {
        return state.hasProperty(PDLBlockStateProperties.ACTIVE) && state.getValue(PDLBlockStateProperties.ACTIVE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        Builder builder1 = IRMachine.MACHINE_BLOCK_BUILDER.get();

        if (builder1.rotatable) {
            builder.add(BlockStateProperties.HORIZONTAL_FACING);
        }

        if (builder1.activatable) {
            builder.add(PDLBlockStateProperties.ACTIVE);
        }

        super.createBlockStateDefinition(builder);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            return state.setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }
        return null;
    }

    @Override
    public boolean tickingEnabled() {
        return this.ticking;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        if (this.blockEntityType == null) {
            this.blockEntityType = IRMachine.BLOCK_ENTITY_TYPES.get(BuiltInRegistries.BLOCK.getKey(this)).get();
        }
        return this.blockEntityType;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BlockBehaviour.Properties properties = IRBlocks.MACHINE_FRAME_PROPS;
        private boolean ticking;
        private boolean activatable;
        private boolean rotatable;

        private Builder() {
        }

        public Builder ticking() {
            this.ticking = true;
            return this;
        }

        public Builder properties(BlockBehaviour.Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder rotatable() {
            this.rotatable = true;
            return this;
        }

        public Builder activatable() {
            this.activatable = true;
            return this;
        }

    }

}
