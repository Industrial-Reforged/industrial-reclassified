package com.portingdeadmods.examplemod.impl.energy;

import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;

public class EnergyHandlerImpl implements EnergyHandler {
    private int energy;
    private int capacity;
    private final EnergyTier energyTier;

    public EnergyHandlerImpl(EnergyTier energyTier, int capacity) {
        this.capacity = capacity;
        this.energyTier = energyTier;
    }

    @Override
    public EnergyTier getEnergyTier() {
        return this.energyTier;
    }

    @Override
    public int getEnergyStored() {
        return this.energy;
    }

    @Override
    public void setEnergyStored(int value) {
        this.energy = value;
    }

    @Override
    public int getEnergyCapacity() {
        return this.capacity;
    }

    @Override
    public void setEnergyCapacity(int value) {
        this.capacity = value;
    }
}
