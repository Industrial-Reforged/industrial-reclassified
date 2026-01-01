package com.portingdeadmods.examplemod.datagen.data;

import com.portingdeadmods.examplemod.registries.EMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class EMRecipeProvider extends RecipeProvider {
    public EMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        super.buildRecipes(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EMItems.EXAMPLE_ITEM.get())
                .pattern("IDI")
                .pattern("D D")
                .pattern("IDI")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output);
    }
}
