package com.portingdeadmods.examplemod.content.recipes.flags;

import com.portingdeadmods.portingdeadlibs.api.recipes.IngredientWithCount;

import java.util.List;

public interface InputComponentFlag extends RecipeComponentFlag {
    List<IngredientWithCount> getIngredients();

    List<Float> getChances();
}
