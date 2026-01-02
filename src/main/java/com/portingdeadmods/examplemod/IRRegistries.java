package com.portingdeadmods.examplemod;

import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class IRRegistries {
    public static final ResourceKey<Registry<EnergyTier>> ENERGY_TIER_KEY =
            ResourceKey.createRegistryKey(IndustrialReclassified.rl("energy_tier"));
    public static final Registry<EnergyTier> ENERGY_TIER =
            new RegistryBuilder<>(ENERGY_TIER_KEY).create();
}