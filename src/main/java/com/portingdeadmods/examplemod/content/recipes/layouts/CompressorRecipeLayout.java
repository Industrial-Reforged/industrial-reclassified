package com.portingdeadmods.examplemod.content.recipes.layouts;

import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.energy.EnergyInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import net.minecraft.resources.ResourceLocation;

public class CompressorRecipeLayout extends MachineRecipeLayout<MachineRecipe> {
    public CompressorRecipeLayout(ResourceLocation id) {
        super(id, MachineRecipe::new);
        this.addComponent(ItemInputComponent.TYPE, "input");
        this.addComponent(ItemOutputComponent.TYPE, "output");
        this.addComponent(EnergyInputComponent.TYPE, "energy", () -> new EnergyInputComponent(800));
        this.addComponent(TimeComponent.TYPE, "duration", () -> new TimeComponent(200));
    }
}
