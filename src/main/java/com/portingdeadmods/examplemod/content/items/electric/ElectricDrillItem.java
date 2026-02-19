package com.portingdeadmods.examplemod.content.items.electric;

import com.portingdeadmods.examplemod.IRConfig;
import com.portingdeadmods.examplemod.IRDataComponents;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.items.ElectricDiggerItem;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.registries.IREnergyTiers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ElectricDrillItem extends ElectricDiggerItem {
    public ElectricDrillItem(Properties properties, float attackSpeed, float baseAttackDamage, Tier tier, Supplier<? extends EnergyTier> energyTier, IntSupplier energyUsage, IntSupplier energyCapacity) {
        super(properties, attackSpeed, baseAttackDamage, tier, BlockTags.MINEABLE_WITH_PICKAXE, energyTier, energyUsage, energyCapacity);
    }

    public static ElectricDrillItem basicItem(Properties properties) {
        return new ElectricDrillItem(properties, -2.8F, 1, Tiers.IRON, IREnergyTiers.LOW, () -> IRConfig.basicDrillEnergyUsage, () -> IRConfig.basicDrillCapacity);
    }

    public static ElectricDrillItem advancedItem(Properties properties) {
        return new ElectricDrillItem(properties, -2.8F, 1, Tiers.DIAMOND, IREnergyTiers.HIGH, () -> IRConfig.advancedDrillEnergyUsage, () -> IRConfig.advancedDrillCapacity);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility) || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
    }
}