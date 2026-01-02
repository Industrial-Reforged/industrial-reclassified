package com.portingdeadmods.examplemod.datagen.assets;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.registries.EMBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EMBlockStateProvider extends BlockStateProvider {
    public EMBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialReclassified.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(EMBlocks.EXAMPLE_BLOCK.get());
    }
}
