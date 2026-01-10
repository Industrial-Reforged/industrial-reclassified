package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.CompressorRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class IRRecipeLayouts {
    public static final Map<ResourceLocation, MachineRecipeLayout> LAYOUTS = new HashMap<>();

    public static final CompressorRecipeLayout COMPRESSOR = register("compressor", CompressorRecipeLayout::new);

    private static <L extends MachineRecipeLayout> L register(String key, Function<ResourceLocation, L> factory) {
        ResourceLocation id = IndustrialReclassified.rl(key);
        L layout = factory.apply(id);
        LAYOUTS.put(id, layout);
        return layout;
    }

}
