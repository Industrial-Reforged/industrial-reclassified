package com.portingdeadmods.examplemod.content.recipes.components.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.components.RecipeComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public record ItemInputComponent(Ingredient ingredient, int count, float chance) implements RecipeComponent {
    public static final MapCodec<ItemInputComponent> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ItemInputComponent::ingredient),
            Codec.INT.fieldOf("count").forGetter(ItemInputComponent::count),
            Codec.FLOAT.fieldOf("chance").forGetter(ItemInputComponent::chance)
    ).apply(inst, ItemInputComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemInputComponent> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            ItemInputComponent::ingredient,
            ByteBufCodecs.INT,
            ItemInputComponent::count,
            ByteBufCodecs.FLOAT,
            ItemInputComponent::chance,
            ItemInputComponent::new
    );
    public static final Type<ItemInputComponent> TYPE = new Type<>(IndustrialReclassified.rl("item_input"), CODEC, STREAM_CODEC);

    public ItemInputComponent(Ingredient ingredient, int count) {
        this(ingredient, count, 1);
    }

    public ItemInputComponent(Ingredient ingredient) {
        this(ingredient, 1);
    }

    public ItemInputComponent(ItemLike item) {
        this(Ingredient.of(item), 1);
    }

    public ItemInputComponent(TagKey<Item> tag) {
        this(Ingredient.of(tag), 1);
    }

    @Override
    public Type<?> type() {
        return TYPE;
    }

    public boolean test(ItemStack itemStack) {
        return this.ingredient().test(itemStack) && itemStack.getCount() >= this.count();
    }

    public boolean isConsumed(RandomSource random) {
        return random.nextFloat() < this.chance();
    }

}
