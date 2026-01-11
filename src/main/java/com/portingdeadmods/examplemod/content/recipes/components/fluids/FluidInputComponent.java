package com.portingdeadmods.examplemod.content.recipes.components.fluids;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.components.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public record FluidInputComponent(FluidIngredient ingredient, int amount, float chance) implements RecipeComponent {
    public static final MapCodec<FluidInputComponent> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            FluidIngredient.CODEC.fieldOf("ingredient").forGetter(FluidInputComponent::ingredient),
            Codec.INT.optionalFieldOf("amount", 1000).forGetter(FluidInputComponent::amount),
            Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(FluidInputComponent::chance)
    ).apply(inst, FluidInputComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidInputComponent> STREAM_CODEC = StreamCodec.composite(
            FluidIngredient.STREAM_CODEC,
            FluidInputComponent::ingredient,
            ByteBufCodecs.INT,
            FluidInputComponent::amount,
            ByteBufCodecs.FLOAT,
            FluidInputComponent::chance,
            FluidInputComponent::new
    );
    public static final Type<FluidInputComponent> TYPE = new Type<>(IndustrialReclassified.rl("fluid_input"), CODEC, STREAM_CODEC);

    public FluidInputComponent(FluidIngredient ingredient, int amount) {
        this(ingredient, amount, 1);
    }

    @Override
    public Type<?> type() {
        return TYPE;
    }

    public boolean test(FluidStack fluidStack) {
        return this.ingredient().test(fluidStack) && fluidStack.getAmount() >= this.amount();
    }

    public boolean isConsumed(RandomSource random) {
        return random.nextFloat() < this.chance();
    }

}
