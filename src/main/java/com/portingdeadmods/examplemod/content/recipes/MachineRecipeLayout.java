package com.portingdeadmods.examplemod.content.recipes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import com.portingdeadmods.portingdeadlibs.utils.RecipeUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class MachineRecipeLayout<R extends MachineRecipe> {
    private final ResourceLocation id;
    private BiFunction<ResourceLocation, Map<String, RecipeComponent>, R> recipeFactory;
    private RecipeType<R> recipeType;
    private RecipeSerializer<R> recipeSerializer;
    private final Map<RecipeComponent.Type<?>, String> components;
    private final Map<RecipeComponent.Type<?>, RecipeComponent> defaultComponentValues;

    public MachineRecipeLayout(ResourceLocation id, RecipeType<R> recipeType, BiFunction<ResourceLocation, Map<String, RecipeComponent>, R> recipeFactory) {
        this.id = id;
        this.recipeFactory = recipeFactory;
        this.components = new LinkedHashMap<>();
        this.defaultComponentValues = new LinkedHashMap<>();
        this.recipeType = recipeType;
    }

    public MachineRecipeLayout(ResourceLocation id, BiFunction<ResourceLocation, Map<String, RecipeComponent>, R> recipeFactory) {
        this(id, null, recipeFactory);
    }

    protected <C extends RecipeComponent> void addComponent(RecipeComponent.Type<C> type, String id) {
        this.components.put(type, id);
    }

    protected <C extends RecipeComponent> void addComponent(RecipeComponent.Type<C> type, String id, C defaultComponent) {
        this.addComponent(type, id);
        this.defaultComponentValues.put(type, defaultComponent);
    }

    public String getComponentKey(RecipeComponent.Type<?> componentType) {
        return this.components.get(componentType);
    }

    public ResourceLocation getId() {
        return id;
    }

    private MapCodec<R> createMapCodec(BiFunction<ResourceLocation, Map<String, RecipeComponent>, R> factory) {
        return new MapCodec<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return components.values().stream().map(ops::createString);
            }

            @Override
            public <T> DataResult<R> decode(DynamicOps<T> ops, MapLike<T> input) {
                Map<String, RecipeComponent> recipeComponents = new LinkedHashMap<>();

                for (Map.Entry<RecipeComponent.Type<?>, String> entry : components.entrySet()) {
                    T val = input.get(entry.getValue());
                    RecipeComponent.Type<?> type = entry.getKey();
                    DataResult<? extends Pair<? extends RecipeComponent, T>> result = type.codec().decode(ops, val);
                    if (result.isSuccess()) {
                        RecipeComponent component = result.getOrThrow().getFirst();
                        recipeComponents.put(entry.getValue(), component);
                    } else {
                        RecipeComponent defaultComponent = defaultComponentValues.get(entry.getKey());
                        if (defaultComponent != null) {
                            recipeComponents.put(entry.getValue(), defaultComponent);
                        } else {
                            return DataResult.error(() -> "Failed to decode Recipe Component: " + result.error().get().message(), factory.apply(id, recipeComponents));
                        }
                    }
                }
                return DataResult.success(factory.apply(id, recipeComponents));
            }

            @Override
            public <T> RecordBuilder<T> encode(R input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
                for (Map.Entry<String, ? extends RecipeComponent> entry : input.getComponents().entrySet()) {
                    RecipeComponent.Type<?> type = entry.getValue().type();
                    DataResult<T> result = type.rawCodec().encodeStart(ops, entry.getValue());
                    if (result.isSuccess()) {
                        prefix.add(entry.getKey(), result.getOrThrow());
                    } else {
                        throw new IllegalStateException("Failed to encode recipe");
                    }
                }
                return prefix;
            }
        };
    }

    public R createRecipe(ResourceLocation id, Map<String, RecipeComponent> components) {
        return this.recipeFactory.apply(id, components);
    }

    public RecipeSerializer<R> getRecipeSerializer() {
        if (this.recipeSerializer == null) {
            MapCodec<R> mapCodec = this.createMapCodec(this::createRecipe);
            StreamCodec<RegistryFriendlyByteBuf, R> streamCodec = ByteBufCodecs.fromCodec(mapCodec.codec()).cast();
            this.recipeSerializer = RecipeUtils.newRecipeSerializer(mapCodec, streamCodec);
        }
        return this.recipeSerializer;
    }

    public RecipeType<R> getRecipeType() {
        if (this.recipeType == null) {
            this.recipeType = RecipeType.simple(this.id);
        }
        return this.recipeType;
    }

    public static class Builder {
    }

}
