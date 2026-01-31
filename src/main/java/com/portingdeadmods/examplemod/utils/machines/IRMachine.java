package com.portingdeadmods.examplemod.utils.machines;

import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.api.blocks.MachineBlock;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.api.menus.MachineMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public record IRMachine(String name, Supplier<? extends EnergyTier> energyTierSupplier,
                        Supplier<? extends MachineBlock> blockSupplier, Supplier<BlockItem> blockItemSupplier,
                        Supplier<BlockEntityType<? extends MachineBlockEntity>> blockEntityTypeSupplier,
                        @Nullable Supplier<MenuType<? extends MachineMenu<?>>> menuTypeSupplier) implements ItemLike {
    public static final Map<ResourceLocation, Supplier<BlockEntityType<? extends MachineBlockEntity>>> BLOCK_ENTITY_TYPES = Collections.synchronizedMap(new HashMap<>());
    public static final AtomicReference<MachineBlock.Builder> MACHINE_BLOCK_BUILDER = new AtomicReference<>();

    public EnergyTier getEnergyTier() {
        return energyTierSupplier.get();
    }

    public MachineBlock getBlock() {
        return blockSupplier.get();
    }

    public BlockItem getBlockItem() {
        return blockItemSupplier.get();
    }

    public BlockEntityType<? extends MachineBlockEntity> getBlockEntityType() {
        return blockEntityTypeSupplier.get();
    }

    public <T extends MachineMenu<?>> MenuType<T> getMenuType() {
        return menuTypeSupplier != null ? ((MenuType<T>) menuTypeSupplier.get()) : null;
    }

    public static Builder builder(Supplier<? extends EnergyTier> energyTierSupplier) {
        return new Builder(energyTierSupplier);
    }

    @Override
    public Item asItem() {
        return this.getBlockItem();
    }

    public static class Builder {
        private BiFunction<MachineBlock.Builder, Supplier<? extends EnergyTier>, ? extends MachineBlock> blockFactory;
        private Function<MachineBlock.Builder, MachineBlock.Builder> machineBuilderFactory;
        private Supplier<? extends BlockItem> blockItemSupplier;
        private IContainerFactory<? extends MachineMenu<?>> menuSupplier;
        private BlockEntityType.BlockEntitySupplier<? extends MachineBlockEntity> blockEntitySupplier;
        private final Supplier<? extends EnergyTier> energyTierSupplier;

        private Builder(Supplier<? extends EnergyTier> energyTierSupplier) {
            this.energyTierSupplier = energyTierSupplier;
        }

        public Builder blockEntity(BlockEntityType.BlockEntitySupplier<? extends MachineBlockEntity> blockEntitySupplier) {
            this.blockEntitySupplier = blockEntitySupplier;
            return this;
        }

        public Builder block(BiFunction<MachineBlock.Builder, Supplier<? extends EnergyTier>, ? extends MachineBlock> blockFactory, Function<MachineBlock.Builder, MachineBlock.Builder> builder) {
            this.blockFactory = blockFactory;
            this.machineBuilderFactory = builder;
            return this;
        }

        public <T extends MachineBlockEntity> Builder menu(IContainerFactory<? extends MachineMenu<T>> menuSupplier) {
            this.menuSupplier = menuSupplier;
            return this;
        }

        public Builder blockItem(Function<Item.Properties, ? extends BlockItem> blockItemFunction, Item.Properties properties) {
            this.blockItemSupplier = () -> blockItemFunction.apply(properties);
            return this;
        }

        public IRMachine build(String name, MachineRegistrationHelper registrationHelper) {
            Objects.requireNonNull(this.blockFactory, "%s machine's block was not initialized".formatted(name));
            Objects.requireNonNull(this.blockEntitySupplier, "%s machine's block entity was not initialized".formatted(name));

            MachineBlock.Builder builder = this.machineBuilderFactory.apply(MachineBlock.builder());
            Supplier<? extends MachineBlock> blockSupplier = () -> this.blockFactory.apply(builder, this.energyTierSupplier);
            MACHINE_BLOCK_BUILDER.set(builder);
            DeferredHolder<Block, ? extends MachineBlock> registeredBlock = registrationHelper.getBlockRegister().register(name, blockSupplier);
            if (this.blockItemSupplier == null) {
                this.blockItemSupplier = () -> new BlockItem(registeredBlock.get(), new Item.Properties());
            }

            DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends MachineBlockEntity>> typeSupplier = registrationHelper.getBlockEntityRegister().register(name,
                    () -> BlockEntityType.Builder.of(this.blockEntitySupplier, registeredBlock.get()).build(null));
            BLOCK_ENTITY_TYPES.put(ResourceLocation.fromNamespaceAndPath(registrationHelper.getBlockEntityRegister().getNamespace(), name), typeSupplier);
            return new IRMachine(
                    name,
                    this.energyTierSupplier,
                    registeredBlock,
                    registrationHelper.getItemRegister().register(name, this.blockItemSupplier),
                    typeSupplier,
                    this.menuSupplier != null ? registrationHelper.getMenuTypeRegister().register(name, () -> IMenuTypeExtension.create(this.menuSupplier)) : null
            );
        }
    }
}