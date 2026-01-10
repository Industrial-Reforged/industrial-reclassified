package com.portingdeadmods.examplemod.content.recipes.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public interface RecipeComponent {
    Type<?> type();

    record Type<C extends RecipeComponent>(ResourceLocation id, Codec<C> codec, StreamCodec<RegistryFriendlyByteBuf, C> streamCodec) {
        public Type(ResourceLocation id, MapCodec<C> codec, StreamCodec<RegistryFriendlyByteBuf, C> streamCodec) {
            this(id, codec.codec(), streamCodec);
        }

        public Codec<RecipeComponent> rawCodec() {
            return (Codec<RecipeComponent>) codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, RecipeComponent> rawStreamCodec() {
            return (StreamCodec<RegistryFriendlyByteBuf, RecipeComponent>) streamCodec;
        }

    }
}
