package com.portingdeadmods.examplemod.content.recipes.components;

import com.mojang.serialization.Codec;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TimeComponent(int time) implements RecipeComponent {
    public static final Codec<TimeComponent> CODEC = Codec.INT.xmap(TimeComponent::new, TimeComponent::time);
    public static final StreamCodec<RegistryFriendlyByteBuf, TimeComponent> STREAM_CODEC = ByteBufCodecs.INT.map(TimeComponent::new, TimeComponent::time).cast();
    public static final Type<TimeComponent> TYPE = new Type<>(IndustrialReclassified.rl("time"), CODEC, STREAM_CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }
}
