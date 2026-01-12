package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.flags.InputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.OutputComponentFlag;
import com.portingdeadmods.examplemod.api.recipes.RecipeFlagType;

public final class IRRecipeComponentFlags {
    public static final RecipeFlagType<InputComponentFlag> INPUT = new RecipeFlagType<>(IndustrialReclassified.rl("input"));
    public static final RecipeFlagType<OutputComponentFlag> OUTPUT = new RecipeFlagType<>(IndustrialReclassified.rl("output"));
}
