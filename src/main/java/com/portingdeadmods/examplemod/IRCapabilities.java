package com.portingdeadmods.examplemod;

import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.jetbrains.annotations.Nullable;


public final class IRCapabilities {
    public static final BlockCapability<EnergyHandler, @Nullable Direction> ENERGY_BLOCK = BlockCapability.createSided(IndustrialReclassified.rl("energy"), EnergyHandler.class);
    public static final ItemCapability<EnergyHandler, Void> ENERGY_ITEM = ItemCapability.createVoid(IndustrialReclassified.rl("energy"), EnergyHandler.class);

}