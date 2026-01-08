package com.portingdeadmods.examplemod.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.examplemod.registries.IRBlockEntityTypes;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.ContainerBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ExampleBlock extends ContainerBlock {
    public ExampleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean tickingEnabled() {
        return false;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return null;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ExampleBlock::new);
    }
}
