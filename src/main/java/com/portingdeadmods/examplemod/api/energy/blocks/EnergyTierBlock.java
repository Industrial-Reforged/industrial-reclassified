package com.portingdeadmods.examplemod.api.energy.blocks;

import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import org.jetbrains.annotations.Nullable;

public interface EnergyTierBlock {
    @Nullable EnergyTier getEnergyTier();
}