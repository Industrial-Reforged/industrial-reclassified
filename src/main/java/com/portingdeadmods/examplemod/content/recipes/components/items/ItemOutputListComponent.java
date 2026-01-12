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

public record ItemOutputListComponent(List<ItemStack> items, List<Float> chances) implements RecipeComponent, OutputComponentFlag {
    public static final MapCodec<ItemOutputListComponent> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.CODEC.listOf().fieldOf("items").forGetter(ItemOutputListComponent::items),
            Codec.FLOAT.listOf().optionalFieldOf("chances", List.of()).forGetter(ItemOutputListComponent::chances)
    ).apply(inst, ItemOutputListComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemOutputListComponent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ItemOutputListComponent::items,
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs.list()),
            ItemOutputListComponent::chances,
            ItemOutputListComponent::new
    );
    public static final Type<ItemOutputListComponent> TYPE = new Type<>(IndustrialReclassified.rl("item_output_list"), CODEC, STREAM_CODEC);
    public static final Set<RecipeFlagType<?>> FLAGS = Set.of(IRRecipeComponentFlags.OUTPUT);

    public ItemOutputListComponent(ItemStack item) {
        this(List.of(item), FloatList.of(1));
    }

    public ItemOutputListComponent(ItemLike item) {
        this(List.of(new ItemStack(item)), FloatList.of(1));
    }

    @Override
    public RecipeComponent.Type<?> type() {
        return TYPE;
    }

    @Override
    public Set<RecipeFlagType<?>> flags() {
        return FLAGS;
    }

    public boolean isOutputted(RandomSource random, int i) {
        if (i < this.chances().size()) {
            return random.nextFloat() < this.chances().get(i);
        }
        return true;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return this.items();
    }

    @Override
    public List<Float> getChances() {
        return this.chances();
    }

}
