package com.portingdeadmods.examplemod.content.recipes.layouts;

import com.portingdeadmods.examplemod.content.recipes.FoodCanningMachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.EnergyComponent;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.registries.IRRecipeLayouts;
import net.minecraft.resources.ResourceLocation;

public class FoodCanningMachineRecipeLayout extends MachineRecipeLayout<MachineRecipe> {
    public FoodCanningMachineRecipeLayout(ResourceLocation id) {
        super(id, IRRecipeLayouts.CANNING_RECIPE_TYPE, FoodCanningMachineRecipe::new);
        this.addComponent(ItemInputComponent.TYPE, "input");
        this.addComponent(EnergyComponent.TYPE, "energy", new EnergyComponent(800));
        this.addComponent(TimeComponent.TYPE, "duration", new TimeComponent(200));
    }
}
