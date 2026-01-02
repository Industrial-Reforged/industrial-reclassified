package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.blocks.ExampleBlock;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLDeferredRegisterBlocks;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class EMBlocks {
    public static final PDLDeferredRegisterBlocks BLOCKS = PDLDeferredRegisterBlocks.createBlocksRegister(IndustrialReclassified.MODID, IRItems.ITEMS);

    public static final DeferredBlock<ExampleBlock> EXAMPLE_BLOCK = BLOCKS.registerBlockWithItem("example_block", ExampleBlock::new);

}
