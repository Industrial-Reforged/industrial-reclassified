package com.portingdeadmods.examplemod.datagen.data;

import com.portingdeadmods.examplemod.registries.IRBlocks;
import com.portingdeadmods.examplemod.registries.IRItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

import static com.portingdeadmods.examplemod.IndustrialReclassified.rl;

public class IRRecipeProvider extends RecipeProvider {
    public IRRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        super.buildRecipes(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IRItems.BASIC_DRILL.get())
                .pattern("IDI")
                .pattern("D D")
                .pattern("IDI")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(Tags.Items.INGOTS_IRON), RecipeCategory.MISC, IRItems.REFINED_IRON_INGOT, 0.7f, 100)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, rl("refined_iron_blasting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Tags.Items.INGOTS_IRON), RecipeCategory.MISC, IRItems.REFINED_IRON_INGOT, 0.7f, 200)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(output, rl("refined_iron_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IRItems.STICKY_RESIN), RecipeCategory.MISC, IRItems.RUBBER, 0.7f, 200)
                .unlockedBy("has_rubber", has(IRItems.STICKY_RESIN))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IRBlocks.MACHINE_FRAME.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', IRItems.REFINED_IRON_INGOT)
                .unlockedBy("has_refined_iron", has(IRItems.REFINED_IRON_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IRItems.MIXED_METAL_INGOT.get())
                .pattern("III")
                .pattern("BBB")
                .pattern("TTT")
                .define('I', IRItems.REFINED_IRON_INGOT)
                .define('B', IRItems.BRONZE_INGOT)
                .define('T', IRItems.TIN_INGOT)
                .unlockedBy("has_bronze", has(IRItems.BRONZE_INGOT))
                .save(output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(IRItems.MIXED_METAL_INGOT), RecipeCategory.MISC, IRItems.ADVANCED_ALLOY_PLATE, 0.7f, 200)
                .unlockedBy("has_mixed_metal", has(IRItems.MIXED_METAL_INGOT))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, IRItems.CARBON_FIBER)
                .requires(IRItems.COAL_DUST, 4)
                .unlockedBy("has_coal_dust", has(IRItems.COAL_DUST))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, IRItems.CARBON_MESH)
                .requires(IRItems.CARBON_FIBER, 2)
                .unlockedBy("has_carbon_fiber", has(IRItems.CARBON_FIBER))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IRItems.WRENCH.get())
                .pattern(" # ")
                .pattern(" ##")
                .pattern("#  ")
                .define('#', IRItems.BRONZE_INGOT)
                .unlockedBy("has_bronze", has(IRItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IRItems.MIXED_METAL_INGOT.get())
                .pattern("III")
                .pattern("BBB")
                .pattern("TTT")
                .define('I', IRItems.REFINED_IRON_INGOT)
                .define('B', IRItems.BRONZE_INGOT)
                .define('T', IRItems.TIN_INGOT)
                .unlockedBy("has_bronze", has(IRItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, IRBlocks.BASIC_GENERATOR.get())
                .pattern("B")
                .pattern("M")
                .pattern("F")
                .define('B', IRItems.REDSTONE_BATTERY)
                .define('M', IRBlocks.MACHINE_FRAME)
                .define('F', Blocks.FURNACE)
                .unlockedBy("has_redstone_battery", has(IRItems.REDSTONE_BATTERY))
                .save(output);

    }
}
