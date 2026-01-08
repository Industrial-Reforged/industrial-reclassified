package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.blockentities.BasicGeneratorBlockEntity;
import com.portingdeadmods.examplemod.content.blockentities.ExampleBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class IRBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IndustrialReclassified.MODID);

//    public static final Supplier<BlockEntityType<BasicGeneratorBlockEntity>> BASIC_GENERATOR = BLOCK_ENTITY_TYPES.register("basic_generator", () -> BlockEntityType.Builder.of(ExampleBlockEntity::new, IRBlocks.EXAMPLE_BLOCK.get())
//            .build(null));
}
