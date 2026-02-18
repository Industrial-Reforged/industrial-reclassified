package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.layouts.*;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class IRRecipeLayouts {
    public static final Map<ResourceLocation, MachineRecipeLayout<?>> LAYOUTS = new HashMap<>();

    public static final CompressorRecipeLayout COMPRESSOR = register("compressor", CompressorRecipeLayout::new);
    public static final MaceratorRecipeLayout MACERATOR = register("macerator", MaceratorRecipeLayout::new);
    public static final ExtractorRecipeLayout EXTRACTOR = register("extractor", ExtractorRecipeLayout::new);
    public static final CanningMachineRecipeLayout CANNING_MACHINE = register("canning_machine", CanningMachineRecipeLayout::new);
    public static final RecyclerRecipeLayout RECYCLER = register("recycler", RecyclerRecipeLayout::new);
    public static final GeothermalGeneratorRecipeLayout GEOTHERMAL_GENERATOR = register("geothermal_generator", GeothermalGeneratorRecipeLayout::new);

    private static <L extends MachineRecipeLayout<?>> L register(String key, Function<ResourceLocation, L> factory) {
        ResourceLocation id = IndustrialReclassified.rl(key);
        L layout = factory.apply(id);
        LAYOUTS.put(id, layout);
        return layout;
    }

}
