package com.portingdeadmods.examplemod.content.recipes;

import com.portingdeadmods.examplemod.content.recipes.components.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import com.portingdeadmods.examplemod.utils.IRRecipeUtils;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean matches(MachineRecipeInput machineRecipeInput, Level level) {
        ItemInputComponent input = this.getComponent(ItemInputComponent.TYPE);
        if (input != null) {
            return IRRecipeUtils.matches(machineRecipeInput.items(), List.of(input));
        }
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        ItemOutputComponent output = this.getComponent(ItemOutputComponent.TYPE);
        if (output != null) {
            return output.item();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        if (this.serializer == null) {
            this.serializer = RegisterRecipeLayoutEvent.LAYOUTS.get(this.id).getRecipeSerializer();
        }
        return this.serializer;
    }

    @Override
    public RecipeType<?> getType() {
        if (this.type == null) {
            this.type = RegisterRecipeLayoutEvent.LAYOUTS.get(this.id).getRecipeType();
        }
        return this.type;
    }

    public Map<String, RecipeComponent> components() {
        return components;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MachineRecipe) obj;
        return Objects.equals(this.components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(components);
    }

    @Override
    public String toString() {
        return "MachineRecipe[" +
                "components=" + components + ']';
    }

}
