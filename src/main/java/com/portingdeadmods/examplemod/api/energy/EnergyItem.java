package com.portingdeadmods.examplemod.api.energy;

import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.impl.energy.EnergyTierImpl;
import net.minecraft.world.item.ItemStack;

public interface EnergyItem {
    default EnergyHandler getEnergyCap(ItemStack itemStack) {
        return itemStack.getCapability(IRCapabilities.ENERGY_ITEM);
    }

    // We have to pass the energy storage here, as it is not assigned to the capability yet
    default void initEnergyStorage(EnergyHandler energyStorage, ItemStack itemStack) {
    }

    default void onEnergyChanged(ItemStack itemStack, int oldAmount) {
    }

    default int getDefaultCapacity() {
        return this.getEnergyTier().defaultCapacity();
    }

    EnergyTier getEnergyTier();
}