package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.flags.FluidInputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.FluidOutputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.ItemInputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.ItemOutputComponentFlag;
import com.portingdeadmods.examplemod.api.recipes.RecipeFlagType;

public final class IRRecipeComponentFlags {
    public static final RecipeFlagType<ItemInputComponentFlag> ITEM_INPUT = new RecipeFlagType<>(IndustrialReclassified.rl("item_input"));
    public static final RecipeFlagType<ItemOutputComponentFlag> ITEM_OUTPUT = new RecipeFlagType<>(IndustrialReclassified.rl("item_output"));
    public static final RecipeFlagType<FluidInputComponentFlag> FLUID_INPUT = new RecipeFlagType<>(IndustrialReclassified.rl("fluid_input"));
    public static final RecipeFlagType<FluidOutputComponentFlag> FLUID_OUTPUT = new RecipeFlagType<>(IndustrialReclassified.rl("fluid_output"));
}
