package com.portingdeadmods.examplemod.content.recipes.components.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import com.portingdeadmods.examplemod.api.recipes.RecipeFlagType;
import com.portingdeadmods.examplemod.content.recipes.flags.OutputComponentFlag;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public record ItemOutputListComponent(List<ItemOutputComponent> outputs) implements RecipeComponent, OutputComponentFlag {
    public static final Codec<ItemOutputListComponent> CODEC = ItemOutputComponent.CODEC.listOf().xmap(ItemOutputListComponent::new, ItemOutputListComponent::outputs);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemOutputListComponent> STREAM_CODEC = ItemOutputComponent.STREAM_CODEC.apply(ByteBufCodecs.list()).map(ItemOutputListComponent::new, ItemOutputListComponent::outputs);
    public static final Type<ItemOutputListComponent> TYPE = new Type<>(IndustrialReclassified.rl("item_output_list"), CODEC, STREAM_CODEC);
    public static final Set<RecipeFlagType<?>> FLAGS = Set.of(IRRecipeComponentFlags.OUTPUT);

    public ItemOutputListComponent(ItemStack item) {
        this(List.of(new ItemOutputComponent(item)));
    }

    public ItemOutputListComponent(ItemLike item) {
        this(List.of(new ItemOutputComponent(item)));
    }

    public ItemOutputListComponent(ItemLike item, int count) {
        this(List.of(new ItemOutputComponent(new ItemStack(item, count))));
    }

    @Override
    public RecipeComponent.Type<?> type() {
        return TYPE;
    }

    @Override
    public Set<RecipeFlagType<?>> flags() {
        return FLAGS;
    }

    public boolean isOutputted(RandomSource random, int slot) {
        if (slot < this.outputs.size()) {
            return random.nextFloat() < this.outputs().get(slot).chance();
        }
        return true;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return this.outputs().stream().map(ItemOutputComponent::item).toList();
    }

    @Override
    public List<Float> getChances() {
        return this.outputs().stream().map(ItemOutputComponent::chance).toList();
    }

}
