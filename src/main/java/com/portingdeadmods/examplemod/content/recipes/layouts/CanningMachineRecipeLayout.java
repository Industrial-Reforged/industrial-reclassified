package com.portingdeadmods.examplemod.content.recipes.layouts;

import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.EnergyComponent;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputListComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import com.portingdeadmods.examplemod.registries.IRRecipeLayouts;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.BiFunction;

public class CanningMachineRecipeLayout extends MachineRecipeLayout<MachineRecipe> {
    public CanningMachineRecipeLayout(ResourceLocation id, BiFunction<ResourceLocation, Map<String, RecipeComponent>, MachineRecipe> recipeFactory) {
        super(id, IRRecipeLayouts.CANNING_RECIPE_TYPE, recipeFactory);
        this.addComponent(ItemInputListComponent.TYPE, "inputs");
        this.addComponent(ItemOutputComponent.TYPE, "output");
        this.addComponent(EnergyComponent.TYPE, "energy", new EnergyComponent(800));
        this.addComponent(TimeComponent.TYPE, "duration", new TimeComponent(200));
    }

    public CanningMachineRecipeLayout(ResourceLocation id) {
        this(id, MachineRecipe::new);
    }
}
