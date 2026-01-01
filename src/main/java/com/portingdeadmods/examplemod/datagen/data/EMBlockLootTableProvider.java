package com.portingdeadmods.examplemod.datagen.data;

import com.portingdeadmods.examplemod.registries.EMBlocks;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EMBlockLootTableProvider extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    public EMBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.VANILLA_SET, registries);
    }

    @Override
    protected void generate() {
        dropSelf(EMBlocks.EXAMPLE_BLOCK.get());
    }

    @Override
    public @NotNull Set<Block> getKnownBlocks() {
        return knownBlocks;
    }

    @Override
    protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
        super.add(block, table);
        knownBlocks.add(block);
    }

}
