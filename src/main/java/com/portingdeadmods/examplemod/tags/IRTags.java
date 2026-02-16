package com.portingdeadmods.examplemod.tags;

import com.mojang.datafixers.util.Either;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.registries.IRBlocks;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class IRTags {
    public static class ItemTags {
        public static final Map<TagKey<Item>, Supplier<List<Either<ItemLike, TagKey<Item>>>>> TAGS = new HashMap<>();

        public static final TagKey<Item> RUBBER_LOGS = createTag("rubber_logs", () -> List.of(item(IRBlocks.RUBBER_TREE_LOG), item(IRBlocks.STRIPPED_RUBBER_TREE_LOG), item(IRBlocks.RUBBER_TREE_WOOD), item(IRBlocks.STRIPPED_RUBBER_TREE_WOOD)));

        private static TagKey<Item> createTag(String id, Supplier<List<Either<ItemLike, TagKey<Item>>>> items) {
            TagKey<Item> tag = TagKey.create(Registries.ITEM, IndustrialReclassified.rl(id));
            TAGS.put(tag, items);
            return tag;
        }

        private static Either<ItemLike, TagKey<Item>> item(ItemLike item) {
            return Either.left(item);
        }

        private static Either<ItemLike, TagKey<Item>> tag(TagKey<Item> tag) {
            return Either.right(tag);
        }

    }

    public static class BlockTags {
        public static final Map<TagKey<Block>, Supplier<List<Either<Block, TagKey<Block>>>>> TAGS = new HashMap<>();

        public static final TagKey<Block> WRENCHABLE = createTag("wrenchable", () -> IRMachines.HELPER.getMachines().stream().map(IRMachine::getBlock).map(BlockTags::block).toList());

        private static TagKey<Block> createTag(String id, Supplier<List<Either<Block, TagKey<Block>>>> items) {
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, IndustrialReclassified.rl(id));
            TAGS.put(tag, items);
            return tag;
        }

        private static Either<Block, TagKey<Block>> block(Block item) {
            return Either.left(item);
        }

        private static Either<Block, TagKey<Block>> tag(TagKey<Block> tag) {
            return Either.right(tag);
        }
    }

}
