package com.portingdeadmods.examplemod.datagen.data;

import com.mojang.datafixers.util.Either;
import com.portingdeadmods.examplemod.tags.IRTags;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.tags.CTags;
import com.portingdeadmods.portingdeadlibs.api.fluids.PDLFluid;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class IRTagsProvider {
    public static void createTagProviders(DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean isServer) {
        BlocksProvider provider = new BlocksProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(isServer, provider);
        generator.addProvider(isServer, new ItemsProvider(packOutput, lookupProvider, provider.contentsGetter()));
        generator.addProvider(isServer, new FluidsProvider(packOutput, lookupProvider, existingFileHelper));
    }

    protected static class ItemsProvider extends ItemTagsProvider {
        public ItemsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
            super(output, lookupProvider, blockTags);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            CTags.ItemTags.TAGS.forEach(this::addTag);
            IRTags.ItemTags.TAGS.forEach(this::addTag);
        }

        private void addTag(TagKey<Item> itemTagKey, Supplier<List<Either<ItemLike, TagKey<Item>>>> listSupplier) {
            IntrinsicTagAppender<Item> tag = tag(itemTagKey);
            for (Either<ItemLike, TagKey<Item>> entry : listSupplier.get()) {
                entry.ifLeft(item -> tag.add(item.asItem()));
                entry.ifRight(tag::addTag);
            }
        }

        private void tag(TagKey<Item> itemTagKey, ItemLike... items) {
            IntrinsicTagAppender<Item> tag = tag(itemTagKey);
            for (ItemLike item : items) {
                tag.add(item.asItem());
            }
        }

        @SafeVarargs
        private void tag(TagKey<Item> itemTagKey, TagKey<Item>... items) {
            IntrinsicTagAppender<Item> tag = tag(itemTagKey);
            for (TagKey<Item> item : items) {
                tag.addTag(item);
            }
        }
    }

    protected static class BlocksProvider extends BlockTagsProvider {
        public BlocksProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, IndustrialReclassified.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
        }

        private void tag(TagKey<Block> itemTagKey, Block... blocks) {
            IntrinsicTagAppender<Block> tag = tag(itemTagKey);
            for (Block block : blocks) {
                tag.add(block);
            }
        }

        @SafeVarargs
        private void tag(TagKey<Block> itemTagKey, TagKey<Block>... blocks) {
            IntrinsicTagAppender<Block> tag = tag(itemTagKey);
            for (TagKey<Block> block : blocks) {
                tag.addTag(block);
            }
        }
    }

    public static class FluidsProvider extends FluidTagsProvider {
        public FluidsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, provider, IndustrialReclassified.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
        }

        private void tag(TagKey<Fluid> fluidTagKey, PDLFluid... fluids) {
            IntrinsicTagAppender<Fluid> tag = tag(fluidTagKey);
            for (PDLFluid fluid : fluids) {
                tag.add(fluid.getStillFluid());
            }
        }

        private void tag(TagKey<Fluid> fluidTagKey, Fluid... fluids) {
            IntrinsicTagAppender<Fluid> tag = tag(fluidTagKey);
            for (Fluid fluid : fluids) {
                tag.add(fluid);
            }
        }

        @SafeVarargs
        private void tag(TagKey<Fluid> fluidTagKey, TagKey<Fluid>... fluids) {
            IntrinsicTagAppender<Fluid> tag = tag(fluidTagKey);
            for (TagKey<Fluid> fluid : fluids) {
                tag.addTag(fluid);
            }
        }
    }
}
