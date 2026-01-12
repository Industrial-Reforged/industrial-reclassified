package com.portingdeadmods.examplemod.content.recipes.components.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import com.portingdeadmods.examplemod.content.recipes.flags.OutputComponentFlag;
import com.portingdeadmods.examplemod.api.recipes.RecipeFlagType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Set;

public record ItemOutputComponent(ItemStack item, float chance) implements RecipeComponent, OutputComponentFlag {
    public static final MapCodec<ItemOutputComponent> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.CODEC.fieldOf("items").forGetter(ItemOutputComponent::item),
            Codec.FLOAT.optionalFieldOf("chances", 1f).forGetter(ItemOutputComponent::chance)
    ).apply(inst, ItemOutputComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemOutputComponent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            ItemOutputComponent::item,
            ByteBufCodecs.FLOAT,
            ItemOutputComponent::chance,
            ItemOutputComponent::new
    );
    public static final Type<ItemOutputComponent> TYPE = new Type<>(IndustrialReclassified.rl("item_output"), CODEC, STREAM_CODEC);
    public static final Set<RecipeFlagType<?>> FLAGS = Set.of(IRRecipeComponentFlags.OUTPUT);

    public ItemOutputComponent(ItemStack item) {
        this(item, 1);
    }

    public ItemOutputComponent(ItemLike item) {
        this(new ItemStack(item), 1);
    }

    @Override
    public RecipeComponent.Type<?> type() {
        return TYPE;
    }

    @Override
    public Set<RecipeFlagType<?>> flags() {
        return FLAGS;
    }

    public boolean isOutputted(RandomSource random) {
        return random.nextFloat() < this.chance();
    }

    @Override
    public List<ItemStack> getOutputs() {
        return List.of(this.item());
    }

    @Override
    public List<Float> getChances() {
        return List.of(this.chance());
    }
}
