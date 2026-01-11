package com.portingdeadmods.examplemod.content.recipes.flags;

import net.minecraft.resources.ResourceLocation;

public record RecipeFlagType<F extends RecipeComponentFlag>(ResourceLocation id) {
}
