package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IRConfig;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.items.ElectricHoeItem;
import com.portingdeadmods.examplemod.content.items.ElectricTreetapItem;
import com.portingdeadmods.examplemod.content.items.ElectricWrenchItem;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLDeferredRegisterItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;

public final class IRItems {
    public static final PDLDeferredRegisterItems ITEMS = PDLDeferredRegisterItems.createItemsRegister(IndustrialReclassified.MODID);

    /* Raw Ores */
    public static final DeferredItem<Item> RAW_TIN = ITEMS.registerSimpleItem("raw_tin");
    public static final DeferredItem<Item> RAW_URANIUM = ITEMS.registerSimpleItem("raw_uranium");
    public static final DeferredItem<Item> RAW_IRIDIUM = ITEMS.registerSimpleItem("raw_iridium");
    /* Ingots */
    public static final DeferredItem<Item> TIN_INGOT = ITEMS.registerSimpleItem("tin_ingot");
    public static final DeferredItem<Item> REFINED_IRON_INGOT = ITEMS.registerSimpleItem("refined_iron_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT = ITEMS.registerSimpleItem("bronze_ingot");
    public static final DeferredItem<Item> IRIDIUM_INGOT = ITEMS.registerSimpleItem("iridium_ingot");
    public static final DeferredItem<Item> URANIUM_INGOT = ITEMS.registerSimpleItem("uranium_ingot");
    public static final DeferredItem<Item> MIXED_METAL_INGOT = ITEMS.registerSimpleItem("mixed_metal_ingot");
    /* Plates */
    public static final DeferredItem<Item> TIN_PLATE = ITEMS.registerSimpleItem("tin_plate");
    public static final DeferredItem<Item> IRIDIUM_PLATE = ITEMS.registerSimpleItem("iridium_plate");
    public static final DeferredItem<Item> ADVANCED_ALLOY_PLATE = ITEMS.registerSimpleItem("advanced_alloy_plate");
    /* Circuits */
    public static final DeferredItem<Item> BASIC_CIRCUIT = ITEMS.registerSimpleItem("basic_circuit");
    public static final DeferredItem<Item> ADVANCED_CIRCUIT = ITEMS.registerSimpleItem("advanced_circuit");
    /* Tools */
    public static final DeferredItem<Item> TREETAP = ITEMS.registerSimpleItem("treetap");
    public static final DeferredItem<Item> WRENCH = ITEMS.registerSimpleItem("wrench");
    public static final DeferredItem<Item> CUTTER = ITEMS.registerSimpleItem("cutter");
    /* Electric Tools */
    public static final DeferredItem<ElectricTreetapItem> ELECTRIC_TREETAP = ITEMS.register("electric_treetap", () -> new ElectricTreetapItem(new Item.Properties(), IREnergyTiers.LOW, () -> IRConfig.electricTreeTapEnergyUsage, () -> IRConfig.electricTreeTapCapacity));
    public static final DeferredItem<ElectricWrenchItem> ELECTRIC_WRENCH = ITEMS.register("electric_wrench", () -> new ElectricWrenchItem(new Item.Properties(), IREnergyTiers.LOW, () -> IRConfig.electricWrenchEnergyUsage, () -> IRConfig.electricWrenchCapacity));
    public static final DeferredItem<ElectricHoeItem> ELECTRIC_HOE = ITEMS.register("electric_hoe",
            () -> new ElectricHoeItem(new Item.Properties(), Tiers.IRON, 1, -2.8F, IREnergyTiers.LOW, () -> IRConfig.electricHoeEnergyUsage, () -> IRConfig.electricHoeCapacity));
    public static final DeferredItem<Item> MINING_LASER = ITEMS.registerSimpleItem("mining_laser");
    public static final DeferredItem<Item> NANO_SABER = ITEMS.registerSimpleItem("nano_saber");
    public static final DeferredItem<Item> BASIC_DRILL = ITEMS.registerSimpleItem("basic_drill");
    public static final DeferredItem<Item> ADVANCED_DRILL = ITEMS.registerSimpleItem("advanced_drill");
    public static final DeferredItem<Item> BASIC_CHAINSAW = ITEMS.registerSimpleItem("basic_chainsaw");
    public static final DeferredItem<Item> ADVANCED_CHAINSAW = ITEMS.registerSimpleItem("advanced_chainsaw");
    /* Electric Armor */
    public static final DeferredItem<Item> NANO_HELMET = ITEMS.registerSimpleItem("nano_helmet");
    public static final DeferredItem<Item> NANO_CHESTPLATE = ITEMS.registerSimpleItem("nano_chestplate");
    public static final DeferredItem<Item> NANO_LEGGINGS = ITEMS.registerSimpleItem("nano_leggings");
    public static final DeferredItem<Item> NANO_BOOTS = ITEMS.registerSimpleItem("nano_boots");

    public static final DeferredItem<Item> QUANTUM_HELMET = ITEMS.registerSimpleItem("quantum_helmet");
    public static final DeferredItem<Item> QUANTUM_CHESTPLATE = ITEMS.registerSimpleItem("quantum_chestplate");
    public static final DeferredItem<Item> QUANTUM_LEGGINGS = ITEMS.registerSimpleItem("quantum_leggings");
    public static final DeferredItem<Item> QUANTUM_BOOTS = ITEMS.registerSimpleItem("quantum_boots");

    public static final DeferredItem<Item> JETPACK = ITEMS.registerSimpleItem("jetpack");
    public static final DeferredItem<Item> ELECTRIC_JETPACK = ITEMS.registerSimpleItem("electric_jetpack");
    /* Batteries */
    public static final DeferredItem<Item> REDSTONE_BATTERY = ITEMS.registerSimpleItem("redstone_battery");
    public static final DeferredItem<Item> ENERGY_CRYSTAL = ITEMS.registerSimpleItem("energy_crystal");
    public static final DeferredItem<Item> LAPOTRON_CRYSTAL = ITEMS.registerSimpleItem("lapotron_crystal");

    /* Misc */
    public static final DeferredItem<Item> FLUID_CELL = ITEMS.registerSimpleItem("fluid_cell");
    public static final DeferredItem<Item> TIN_CAN = ITEMS.registerSimpleItem("tin_can");
    public static final DeferredItem<Item> TIN_CAN_FOOD = ITEMS.registerSimpleItem("tin_can_food");

    /* Coal is way too useful lol */
    public static final DeferredItem<Item> COAL_BALL = ITEMS.registerSimpleItem("coal_ball");
    public static final DeferredItem<Item> COMPRESSED_COAL_BALL = ITEMS.registerSimpleItem("compressed_coal_ball");
    public static final DeferredItem<Item> GRAPHENE = ITEMS.registerSimpleItem("graphene");
    public static final DeferredItem<Item> CARBON_FIBER = ITEMS.registerSimpleItem("carbon_fiber");
    public static final DeferredItem<Item> CARBON_MESH = ITEMS.registerSimpleItem("carbon_mesh");
    public static final DeferredItem<Item> CARBON_PLATE = ITEMS.registerSimpleItem("carbon_plate");

    public static final DeferredItem<Item> SCRAP = ITEMS.registerSimpleItem("scrap");
    public static final DeferredItem<Item> SCRAP_BOX = ITEMS.registerSimpleItem("scrap_box");
    public static final DeferredItem<Item> UU_MATTER = ITEMS.registerSimpleItem("uu_matter");
}
