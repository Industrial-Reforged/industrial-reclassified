package com.portingdeadmods.examplemod.api.recipes;

import net.minecraft.resources.ResourceLocation;

public record RecipeFlagType<F extends RecipeComponentFlag>(ResourceLocation id) {
}
