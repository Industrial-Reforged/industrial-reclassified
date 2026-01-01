package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.examplemod.content.blockentities.ExampleBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class EMBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ExampleMod.MODID);

    public static final Supplier<BlockEntityType<ExampleBlockEntity>> EXAMPLE = BLOCK_ENTITY_TYPES.register("example", () -> BlockEntityType.Builder.of(ExampleBlockEntity::new, EMBlocks.EXAMPLE_BLOCK.get())
            .build(null));
}
