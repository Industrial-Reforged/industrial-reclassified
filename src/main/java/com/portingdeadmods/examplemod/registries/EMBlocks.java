package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.examplemod.content.blocks.ExampleBlock;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLDeferredRegisterBlocks;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EMBlocks {
    public static final PDLDeferredRegisterBlocks BLOCKS = PDLDeferredRegisterBlocks.createBlocksRegister(ExampleMod.MODID, EMItems.ITEMS);

    public static final DeferredBlock<ExampleBlock> EXAMPLE_BLOCK = BLOCKS.registerBlockWithItem("example_block", ExampleBlock::new);

}
