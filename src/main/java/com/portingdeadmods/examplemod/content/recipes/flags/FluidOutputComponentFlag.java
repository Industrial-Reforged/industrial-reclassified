package com.portingdeadmods.examplemod.content.recipes.flags;

import com.portingdeadmods.examplemod.api.recipes.RecipeComponentFlag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public interface FluidOutputComponentFlag extends RecipeComponentFlag {
    List<FluidStack> getOutputs();

    List<Float> getChances();

    boolean isOutputted(RandomSource random, int tank);
}
