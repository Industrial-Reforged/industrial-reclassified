package com.portingdeadmods.examplemod.content.recipes.components;

import com.mojang.serialization.Codec;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EnergyComponent(int energy) implements RecipeComponent {
    public static final Codec<EnergyComponent> CODEC = Codec.INT.xmap(EnergyComponent::new, EnergyComponent::energy);
    public static final StreamCodec<RegistryFriendlyByteBuf, EnergyComponent> STREAM_CODEC = ByteBufCodecs.INT.map(EnergyComponent::new, EnergyComponent::energy).cast();
    public static final Type<EnergyComponent> TYPE = new Type<>(IndustrialReclassified.rl("energy_component"), CODEC, STREAM_CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }
}
