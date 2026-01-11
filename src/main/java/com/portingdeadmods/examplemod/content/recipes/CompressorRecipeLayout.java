package com.portingdeadmods.examplemod.content.recipes;

import com.portingdeadmods.examplemod.content.recipes.components.EnergyComponent;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import net.minecraft.resources.ResourceLocation;

public class CompressorRecipeLayout extends MachineRecipeLayout {
    public CompressorRecipeLayout(ResourceLocation id) {
        super(id);
        this.addComponent(ItemInputComponent.TYPE, "input");
        this.addComponent(ItemOutputComponent.TYPE, "output");
        this.addComponent(EnergyComponent.TYPE, "energy", new EnergyComponent(800));
        this.addComponent(TimeComponent.TYPE, "duration", new TimeComponent(200));
    }
}
