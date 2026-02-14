package com.portingdeadmods.examplemod.content.recipes.layouts;

import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.EnergyComponent;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputListComponent;
import net.minecraft.resources.ResourceLocation;

public class ExtractorRecipeLayout extends MachineRecipeLayout<MachineRecipe> {
    public ExtractorRecipeLayout(ResourceLocation id) {
        super(id, MachineRecipe::new);
        this.addComponent(ItemInputComponent.TYPE, "input");
        this.addComponent(ItemOutputListComponent.TYPE, "outputs");
        this.addComponent(EnergyComponent.TYPE, "energy", () -> new EnergyComponent(800));
        this.addComponent(TimeComponent.TYPE, "duration", () -> new TimeComponent(200));
    }
}
