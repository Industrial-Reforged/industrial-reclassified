package com.portingdeadmods.examplemod.content.blocks;

import com.portingdeadmods.examplemod.api.energy.EnergyTier;

import java.util.function.Supplier;

public class BurntCableBlock extends CableBlock{
    public BurntCableBlock(Properties properties, int width, Supplier<? extends EnergyTier> energyTier) {
        super(properties, width, energyTier);
    }

    @Override
    public EnergyTier getEnergyTier() {
        return null;
    }
}