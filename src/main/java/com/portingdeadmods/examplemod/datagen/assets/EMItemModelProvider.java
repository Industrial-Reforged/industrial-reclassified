package com.portingdeadmods.examplemod.datagen.assets;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.examplemod.registries.EMBlocks;
import com.portingdeadmods.examplemod.registries.EMItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class EMItemModelProvider extends ItemModelProvider {
    public EMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExampleMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        EMBlocks.BLOCKS.getBlockItems().stream().map(Supplier::get).map(BlockItem::getBlock).forEach(this::simpleBlockItem);

        basicItem(EMItems.EXAMPLE_ITEM.get());
    }
}
