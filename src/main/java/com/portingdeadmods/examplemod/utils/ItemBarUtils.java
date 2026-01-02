package com.portingdeadmods.examplemod.utils;

import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;

public final class ItemBarUtils {
    public static int energyBarWidth(ItemStack stack) {
        EnergyHandler energyStorage = stack.getCapability(IRCapabilities.ENERGY_ITEM);
        float ratio = (float) energyStorage.getEnergyStored() / energyStorage.getEnergyCapacity();
        return Math.round(13.0F - ((1 - ratio) * 13.0F));
    }

    public static int energyBarColor(ItemStack stack) {
        return FastColor.ARGB32.color(255, 215, 0, 0);
    }

}