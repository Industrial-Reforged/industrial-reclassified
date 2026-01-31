package com.portingdeadmods.examplemod.content.items.electric;

import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.api.energy.items.EnergyArmorItem;
import com.portingdeadmods.examplemod.registries.IRArmorMaterials;
import com.portingdeadmods.examplemod.registries.IREnergyTiers;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class NanoArmorItem extends EnergyArmorItem {
    public NanoArmorItem(Properties properties, Holder<ArmorMaterial> material, Type type, Supplier<? extends EnergyTier> energyTier, IntSupplier energyUsage, IntSupplier energyCapacity) {
        super(properties, material, type, energyTier, energyUsage, energyCapacity);
    }

    public static NanoArmorItem defaultItem(Properties properties, Type type) {
        return new NanoArmorItem(properties, IRArmorMaterials.NANO, type, IREnergyTiers.HIGH, () -> 100, () -> 64000);
    }

}
