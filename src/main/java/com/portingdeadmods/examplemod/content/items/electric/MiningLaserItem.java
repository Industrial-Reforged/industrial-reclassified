package com.portingdeadmods.examplemod.content.items.electric;

import com.portingdeadmods.examplemod.api.energy.items.ElectricToolItem;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.api.energy.items.SimpleEnergyItem;
import com.portingdeadmods.examplemod.registries.IREnergyTiers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class MiningLaserItem extends SimpleEnergyItem implements ElectricToolItem {
    public MiningLaserItem(Properties properties, Supplier<? extends EnergyTier> energyTier, IntSupplier defaultEnergyCapacity) {
        super(properties, energyTier, defaultEnergyCapacity);
    }

    public static MiningLaserItem defaultItem(Properties properties) {
        return new MiningLaserItem(properties, IREnergyTiers.EXTREME, () -> 64000);
    }

    @Override
    public boolean requireEnergyToWork(ItemStack itemStack, @Nullable Entity entity) {
        return true;
    }

    @Override
    public int getEnergyUsage(ItemStack itemStack, @Nullable Entity entity) {
        return 0;
    }
}
