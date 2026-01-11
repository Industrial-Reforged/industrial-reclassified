package com.portingdeadmods.examplemod.content.recipes.components.fluids;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.components.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public record FluidOutputComponent(FluidStack fluid, float chance) implements RecipeComponent {
    public static final MapCodec<FluidOutputComponent> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            FluidStack.CODEC.fieldOf("fluid").forGetter(FluidOutputComponent::fluid),
            Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(FluidOutputComponent::chance)
    ).apply(inst, FluidOutputComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidOutputComponent> STREAM_CODEC = StreamCodec.composite(
            FluidStack.STREAM_CODEC,
            FluidOutputComponent::fluid,
            ByteBufCodecs.FLOAT,
            FluidOutputComponent::chance,
            FluidOutputComponent::new
    );
    public static final Type<FluidOutputComponent> TYPE = new Type<>(IndustrialReclassified.rl("fluid_output"), CODEC, STREAM_CODEC);

    public FluidOutputComponent(FluidStack fluid) {
        this(fluid, 1);
    }

    @Override
    public Type<?> type() {
        return TYPE;
    }

    public boolean isOutputted(RandomSource random) {
        return random.nextFloat() < this.chance();
    }

}
