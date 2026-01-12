package com.portingdeadmods.examplemod.datagen;

import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class MachineRecipeBuilder implements RecipeBuilder {
    private final MachineRecipeLayout.Builder builder;

    public MachineRecipeBuilder(MachineRecipeLayout layout) {
        this.builder = layout.builder();
    }

    public MachineRecipeBuilder component(RecipeComponent component) {
        this.builder.component(component);
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.builder.getRecipe().getComponentByFlag(IRRecipeComponentFlags.OUTPUT).getOutputs().getFirst().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        recipeOutput.accept(resourceLocation, this.builder.build(), null);
    }
}
