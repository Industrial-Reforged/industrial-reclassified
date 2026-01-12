package com.portingdeadmods.examplemod.content.recipes.flags;

import com.portingdeadmods.examplemod.api.recipes.RecipeComponentFlag;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface OutputComponentFlag extends RecipeComponentFlag {
    List<ItemStack> getOutputs();

    List<Float> getChances();
}
