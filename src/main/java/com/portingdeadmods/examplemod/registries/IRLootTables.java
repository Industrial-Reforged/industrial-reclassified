package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class IRLootTables {
    public static final ResourceKey<LootTable> SCRAP_BOX = ResourceKey.create(Registries.LOOT_TABLE, IndustrialReclassified.rl("scrap_box"));
}
