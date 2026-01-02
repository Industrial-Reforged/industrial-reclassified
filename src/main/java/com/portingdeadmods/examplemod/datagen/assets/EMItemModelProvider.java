package com.portingdeadmods.examplemod.datagen.assets;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.registries.EMBlocks;
import com.portingdeadmods.examplemod.registries.IRItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class EMItemModelProvider extends ItemModelProvider {
    public EMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IndustrialReclassified.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        EMBlocks.BLOCKS.getBlockItems().stream().map(Supplier::get).map(BlockItem::getBlock).forEach(this::simpleBlockItem);

        basicItem(IRItems.RAW_TIN.get());
        basicItem(IRItems.RAW_URANIUM.get());
        basicItem(IRItems.RAW_IRIDIUM.get());

        basicItem(IRItems.TIN_INGOT.get());
        basicItem(IRItems.REFINED_IRON_INGOT.get());
        basicItem(IRItems.BRONZE_INGOT.get());
        basicItem(IRItems.IRIDIUM_INGOT.get());
        basicItem(IRItems.MIXED_METAL_INGOT.get());
        basicItem(IRItems.URANIUM_INGOT.get());

        basicItem(IRItems.TIN_PLATE.get());
        basicItem(IRItems.ADVANCED_ALLOY_PLATE.get());

        basicItem(IRItems.BASIC_CIRCUIT.get());
        basicItem(IRItems.ADVANCED_CIRCUIT.get());

        basicItem(IRItems.TREETAP.get());
        handheldItem(IRItems.WRENCH.get());
        handheldItem(IRItems.CUTTER.get());

        handheldItem(IRItems.ELECTRIC_HOE.get());
        handheldItem(IRItems.ELECTRIC_WRENCH.get());
        handheldItem(IRItems.ELECTRIC_TREETAP.get());
        handheldItem(IRItems.MINING_LASER.get());
        handheldItem(IRItems.NANO_SABER.get());
        handheldItem(IRItems.BASIC_DRILL.get());
        handheldItem(IRItems.ADVANCED_DRILL.get());
        handheldItem(IRItems.BASIC_CHAINSAW.get());
        handheldItem(IRItems.ADVANCED_CHAINSAW.get());

        basicItem(IRItems.NANO_HELMET.get());
        basicItem(IRItems.NANO_CHESTPLATE.get());
        basicItem(IRItems.NANO_LEGGINGS.get());
        basicItem(IRItems.NANO_BOOTS.get());

        basicItem(IRItems.QUANTUM_HELMET.get());
        basicItem(IRItems.QUANTUM_CHESTPLATE.get());
        basicItem(IRItems.QUANTUM_LEGGINGS.get());
        basicItem(IRItems.QUANTUM_BOOTS.get());

        basicItem(IRItems.JETPACK.get());
        basicItem(IRItems.ELECTRIC_JETPACK.get());

        basicItem(IRItems.REDSTONE_BATTERY.get());
        basicItem(IRItems.ENERGY_CRYSTAL.get());
        basicItem(IRItems.LAPOTRON_CRYSTAL.get());

        basicItem(IRItems.FLUID_CELL.get());
        basicItem(IRItems.TIN_CAN.get());
        basicItem(IRItems.TIN_CAN_FOOD.get());

        basicItem(IRItems.COAL_BALL.get());
        basicItem(IRItems.COMPRESSED_COAL_BALL.get());
        basicItem(IRItems.GRAPHENE.get());
        basicItem(IRItems.CARBON_FIBER.get());
        basicItem(IRItems.CARBON_MESH.get());
        basicItem(IRItems.CARBON_PLATE.get());

        basicItem(IRItems.SCRAP.get());
        basicItem(IRItems.SCRAP_BOX.get());
        basicItem(IRItems.UU_MATTER.get());
    }
}
