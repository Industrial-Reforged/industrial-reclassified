package com.portingdeadmods.examplemod.content.recipes;

import com.portingdeadmods.examplemod.content.recipes.components.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.flags.InputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.OutputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.RecipeComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.RecipeFlagType;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import com.portingdeadmods.portingdeadlibs.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class MachineRecipe implements PDLRecipe<MachineRecipeInput> {
    private RecipeSerializer<MachineRecipe> serializer;
    private RecipeType<MachineRecipe> type;
    private final Map<String, RecipeComponent> components;
    private final ResourceLocation id;

    public MachineRecipe(ResourceLocation id, Map<String, RecipeComponent> components) {
        this.id = id;
        this.components = components;
    }

    public MachineRecipe(ResourceLocation id) {
        this(id, new LinkedHashMap<>());
    }

    public <R extends RecipeComponent> R getComponent(RecipeComponent.Type<R> type) {
        for (RecipeComponent value : components.values()) {
            if (value.type().equals(type)) {
                return (R) value;
            }
        }
        return null;
    }

    public <F extends RecipeComponentFlag> F getComponentByFlag(RecipeFlagType<F> flagType) {
        for (RecipeComponent component : components.values()) {
            if (component.flags().contains(flagType)) {
                return (F) component;
            }
        }
        return null;
    }

    @Override
    public boolean matches(MachineRecipeInput machineRecipeInput, Level level) {
        InputComponentFlag input = this.getComponentByFlag(RecipeComponentFlags.INPUT);
        if (input != null) {
            return RecipeUtils.compareItems(machineRecipeInput.items(), input.getIngredients());
        }
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        OutputComponentFlag output = this.getComponentByFlag(RecipeComponentFlags.OUTPUT);
        if (output != null) {
            return output.getOutputs().getFirst();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        if (this.serializer == null) {
            this.serializer = RegisterRecipeLayoutEvent.LAYOUTS.get(this.id).getRecipeSerializer();
        }
        return this.serializer;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        if (this.type == null) {
            this.type = RegisterRecipeLayoutEvent.LAYOUTS.get(this.id).getRecipeType();
        }
        return this.type;
    }

    public Map<String, RecipeComponent> getComponents() {
        return this.components;
    }

}
