package com.portingdeadmods.examplemod.content.recipes.components.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.recipes.components.RecipeComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record ItemOutputComponent(ItemStack item, float chance) implements RecipeComponent {
    public static final MapCodec<ItemOutputComponent> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.CODEC.fieldOf("item").forGetter(ItemOutputComponent::item),
            Codec.FLOAT.fieldOf("chance").forGetter(ItemOutputComponent::chance)
    ).apply(inst, ItemOutputComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemOutputComponent> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            ItemOutputComponent::item,
            ByteBufCodecs.FLOAT,
            ItemOutputComponent::chance,
            ItemOutputComponent::new
    );
    public static final Type<ItemOutputComponent> TYPE = new Type<>(IndustrialReclassified.rl("item_output"), CODEC, STREAM_CODEC);

    public ItemOutputComponent(ItemStack item) {
        this(item, 1);
    }
    public ItemOutputComponent(Item item) {
        this(new ItemStack(item), 1);
    }

    @Override
    public Type<?> type() {
        return TYPE;
    }

    public boolean isOutputted(RandomSource random) {
        return random.nextFloat() < this.chance();
    }

}
