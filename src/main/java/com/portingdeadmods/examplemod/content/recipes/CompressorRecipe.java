package com.portingdeadmods.examplemod.content.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.components.EnergyComponent;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemOutputComponent;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import com.portingdeadmods.portingdeadlibs.utils.RecipeUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CompressorRecipe(ItemInputComponent input, ItemOutputComponent output, TimeComponent duration, EnergyComponent energy) implements PDLRecipe<SingleRecipeInput> {
    public static final RecipeType<CompressorRecipe> TYPE = RecipeType.simple(IndustrialReclassified.rl("compressor"));
    public static final MapCodec<CompressorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemInputComponent.CODEC.fieldOf("input").forGetter(CompressorRecipe::input),
            ItemOutputComponent.CODEC.fieldOf("output").forGetter(CompressorRecipe::output),
            TimeComponent.CODEC.fieldOf("duration").forGetter(CompressorRecipe::duration),
            EnergyComponent.CODEC.fieldOf("energy").forGetter(CompressorRecipe::energy)
    ).apply(inst, CompressorRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, CompressorRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemInputComponent.STREAM_CODEC,
            CompressorRecipe::input,
            ItemOutputComponent.STREAM_CODEC,
            CompressorRecipe::output,
            TimeComponent.STREAM_CODEC,
            CompressorRecipe::duration,
            EnergyComponent.STREAM_CODEC,
            CompressorRecipe::energy,
            CompressorRecipe::new
    );
    public static final RecipeSerializer<CompressorRecipe> SERIALIZER = RecipeUtils.newRecipeSerializer(CODEC, STREAM_CODEC);

    @Override
    public boolean matches(SingleRecipeInput singleRecipeInput, Level level) {
        return this.input().test(singleRecipeInput.item());
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output().item();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return TYPE;
    }

}
