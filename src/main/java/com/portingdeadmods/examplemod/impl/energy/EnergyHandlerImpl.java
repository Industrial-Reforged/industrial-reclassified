package com.portingdeadmods.examplemod.impl.energy;

import com.portingdeadmods.examplemod.api.capabilities.StorageChangedListener;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EnergyHandlerImpl implements EnergyHandler, StorageChangedListener {
    private int energy;
    private int capacity;
    private final EnergyTier energyTier;
    private Consumer<Integer> changedFunction = amount -> {};

    public EnergyHandlerImpl(Supplier<? extends EnergyTier> energyTier) {
        this.energyTier = energyTier.get();
        this.capacity = this.energyTier.defaultCapacity();
    }

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

    @Override
    public void setOnChangedFunction(Consumer<Integer> onChangedFunction) {
        this.changedFunction = onChangedFunction;
    }

    public static class NoDrain extends EnergyHandlerImpl implements EnergyHandler.NoDrain {
        public NoDrain(Supplier<? extends EnergyTier> energyTier) {
            super(energyTier);
        }

        @Override
        public int drainEnergy(int value, boolean simulate) {
            return EnergyHandler.NoDrain.super.drainEnergy(value, simulate);
        }

    }

    public static class NoFill extends EnergyHandlerImpl implements EnergyHandler.NoFill {
        public NoFill(Supplier<? extends EnergyTier> energyTier) {
            super(energyTier);
        }

        @Override
        public int drainEnergy(int value, boolean simulate) {
            return EnergyHandler.NoFill.super.drainEnergy(value, simulate);
        }

    }

}
