package com.portingdeadmods.examplemod.content.items.electric;

import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.api.energy.items.EnergyArmorItem;
import com.portingdeadmods.examplemod.registries.IRArmorMaterials;
import com.portingdeadmods.examplemod.registries.IREnergyTiers;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class QuantumArmorItem extends EnergyArmorItem {
    public QuantumArmorItem(Properties properties, Holder<ArmorMaterial> material, Type type, Supplier<? extends EnergyTier> energyTier, IntSupplier energyUsage, IntSupplier energyCapacity) {
        super(properties, material, type, energyTier, energyUsage, energyCapacity);
    }

    public static QuantumArmorItem defaultItem(Properties properties, Type type) {
        return new QuantumArmorItem(properties, IRArmorMaterials.QUANTUM, type, IREnergyTiers.INSANE, () -> 100, () -> 10_000_000);
    }
}
