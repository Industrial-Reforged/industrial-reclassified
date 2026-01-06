package com.portingdeadmods.examplemod.datagen.assets;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.registries.IRBlocks;
import com.portingdeadmods.examplemod.registries.IRItems;
import com.portingdeadmods.examplemod.registries.IRTranslations;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class IREnUsLangProvider extends LanguageProvider {
    public IREnUsLangProvider(PackOutput output) {
        super(output, IndustrialReclassified.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        IRTranslations.TRANSLATIONS.getDefaultTranslations().forEach(this::add);

        addItem(IRItems.TIN_INGOT, "Tin Ingot");
        addItem(IRItems.REFINED_IRON_INGOT, "Uranium Ingot");
        addItem(IRItems.URANIUM_INGOT, "Uranium Ingot");
        addItem(IRItems.BRONZE_INGOT, "Bronze Ingot");
        addItem(IRItems.IRIDIUM_INGOT, "Iridium Ingot");
        
        addItem(IRItems.TIN_PLATE, "Tin Plate");
        addItem(IRItems.ADVANCED_ALLOY_PLATE, "Advanced Alloy Plate");
        
        addItem(IRItems.BASIC_CIRCUIT, "Basic Circuit");
        addItem(IRItems.ADVANCED_CIRCUIT, "Advanced Circuit");
        
        addItem(IRItems.WRENCH, "Wrench");
        addItem(IRItems.TREETAP, "Treetap");
        addItem(IRItems.CUTTER, "Cutter");
        
        addItem(IRItems.ELECTRIC_TREETAP, "Electric Treetap");
        addItem(IRItems.ELECTRIC_WRENCH, "Electric Wrench");
        addItem(IRItems.ELECTRIC_HOE, "Electric Hoe");
        addItem(IRItems.MINING_LASER, "Mining Laser");
        addItem(IRItems.NANO_SABER, "Nano Saber");
        addItem(IRItems.BASIC_DRILL, "Basic Drill");
        addItem(IRItems.ADVANCED_DRILL, "Advanced Drill");
        addItem(IRItems.BASIC_CHAINSAW, "Basic Chainsaw");
        addItem(IRItems.ADVANCED_CHAINSAW, "Advanced Chainsaw");

        addItem(IRItems.NANO_HELMET, "Nano Helmet");
        addItem(IRItems.NANO_CHESTPLATE, "Nano Chestplate");
        addItem(IRItems.NANO_LEGGINGS, "Nano Leggings");
        addItem(IRItems.NANO_BOOTS, "Nano Boots");

        addItem(IRItems.QUANTUM_HELMET, "Quantum Helmet");
        addItem(IRItems.QUANTUM_CHESTPLATE, "Quantum Chestplate");
        addItem(IRItems.QUANTUM_LEGGINGS, "Quantum Leggings");
        addItem(IRItems.QUANTUM_BOOTS, "Quantum Boots");

        addItem(IRItems.JETPACK, "Jetpack");
        addItem(IRItems.ELECTRIC_JETPACK, "Electric Jetpack");

        addItem(IRItems.REDSTONE_BATTERY, "Redstone Battery");
        addItem(IRItems.ENERGY_CRYSTAL, "Energy Crystal");
        addItem(IRItems.LAPOTRON_CRYSTAL, "Lapotoron Crystal");

        addItem(IRItems.FLUID_CELL, "Fluid Cell");
        addItem(IRItems.TIN_CAN, "Tin Can");
        addItem(IRItems.TIN_CAN_FOOD, "Tin Can With Food");
        addItem(IRItems.STICKY_RESIN, "Sticky Resin");
        addItem(IRItems.RUBBER, "Rubber");

        addItem(IRItems.COAL_BALL, "Coal Ball");
        addItem(IRItems.COMPRESSED_COAL_BALL, "Compressed Coal Ball");
        addItem(IRItems.GRAPHENE, "Graphene");
        addItem(IRItems.CARBON_FIBER, "Carbon Fiber");
        addItem(IRItems.CARBON_MESH, "Carbon Mesh");
        addItem(IRItems.CARBON_PLATE, "Carbon Plate");

        addItem(IRItems.SCRAP, "Scrap");
        addItem(IRItems.SCRAP_BOX, "Scrap Box");
        addItem(IRItems.UU_MATTER, "UU Matter");

        addItem(IRItems.PLANT_BALL, "Plant Ball");

        addBlock(IRBlocks.INDUSTRIAL_TNT, "Industrial Tnt");

        addBlock(IRBlocks.MACHINE_FRAME, "Machine Frame");

        addBlock(IRBlocks.REINFORCED_DOOR, "Reinforced Door");
        addBlock(IRBlocks.REINFORCED_GLASS, "Reinforced Glass");
        addBlock(IRBlocks.REINFORCED_STONE, "Reinforced Stone");

        addBlock(IRBlocks.TIN_BLOCK, "Tin Block");
        addBlock(IRBlocks.URANIUM_BLOCK, "Uranium Block");
        addBlock(IRBlocks.BRONZE_BLOCK, "Bronze Block");

        addBlock(IRBlocks.RUBBER_TREE_LOG, "Rubber Tree Log");
        addBlock(IRBlocks.RUBBER_TREE_WOOD, "Rubber Tree Wood");
        addBlock(IRBlocks.STRIPPED_RUBBER_TREE_LOG, "Rubber Tree Stripped Log");
        addBlock(IRBlocks.STRIPPED_RUBBER_TREE_WOOD, "Rubber Tree Stripped Wood");
        addBlock(IRBlocks.RUBBER_TREE_LEAVES, "Rubber Tree Leaves");
        addBlock(IRBlocks.RUBBER_TREE_SAPLING, "Rubber Tree Sapling");
        addBlock(IRBlocks.RUBBER_TREE_PLANKS, "Rubber Tree Planks");
        addBlock(IRBlocks.RUBBER_TREE_DOOR, "Rubber Tree Door");
        addBlock(IRBlocks.RUBBER_TREE_TRAPDOOR, "Rubber Tree Trapdoor");
        addBlock(IRBlocks.RUBBER_TREE_FENCE, "Rubber Tree Fence");
        addBlock(IRBlocks.RUBBER_TREE_FENCE_GATE, "Rubber Tree Fence Gate");
        addBlock(IRBlocks.RUBBER_TREE_PRESSURE_PLATE, "Rubber Tree Pressure Plate");
        addBlock(IRBlocks.RUBBER_TREE_BUTTON, "Rubber Tree Button");
        addBlock(IRBlocks.RUBBER_TREE_SLAB, "Rubber Tree Slab");
        addBlock(IRBlocks.RUBBER_TREE_STAIRS, "Rubber Tree Stairs");
    }

}
