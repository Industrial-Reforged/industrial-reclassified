package com.portingdeadmods.examplemod.impl.energy;

import com.portingdeadmods.examplemod.api.energy.EnergyTier;

public record EnergyTierImpl(int maxInput, int maxOutput, int color, int defaultCapacity) implements EnergyTier {
    public EnergyTierImpl(int maxTransfer, int color, int defaultCapacity) {
        this(maxTransfer, maxTransfer, color, defaultCapacity);
    }
}
