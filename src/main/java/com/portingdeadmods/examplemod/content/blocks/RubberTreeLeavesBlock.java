package com.portingdeadmods.examplemod.content.blocks;

import com.portingdeadmods.examplemod.content.blocks.RubberTreeLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class RubberTreeLeavesBlock extends LeavesBlock {

    public RubberTreeLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static RubberTreeLeavesBlock defaultBlock(BlockBehaviour.Properties properties) {
        return new RubberTreeLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }
}