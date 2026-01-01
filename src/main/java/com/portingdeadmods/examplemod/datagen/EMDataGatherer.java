package com.portingdeadmods.examplemod.datagen;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.examplemod.datagen.assets.EMBlockStateProvider;
import com.portingdeadmods.examplemod.datagen.assets.EMEnUsLangProvider;
import com.portingdeadmods.examplemod.datagen.assets.EMItemModelProvider;
import com.portingdeadmods.examplemod.datagen.data.EMBlockLootTableProvider;
import com.portingdeadmods.examplemod.datagen.data.EMRecipeProvider;
import com.portingdeadmods.examplemod.datagen.data.EMTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ExampleMod.MODID)
public final class EMDataGatherer {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new EMBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new EMItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new EMEnUsLangProvider(packOutput));

        EMTagsProvider.createTagProviders(generator, packOutput, lookupProvider, existingFileHelper, event.includeServer());
        generator.addProvider(event.includeServer(), new EMRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(EMBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
    }
}
