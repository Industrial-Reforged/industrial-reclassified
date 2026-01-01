package com.portingdeadmods.examplemod.content.blockentities;

import com.portingdeadmods.examplemod.content.menus.ExampleMenu;
import com.portingdeadmods.examplemod.registries.EMBlockEntityTypes;
import com.portingdeadmods.examplemod.registries.EMTranslations;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import com.portingdeadmods.portingdeadlibs.utils.capabilities.HandlerUtils;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ExampleBlockEntity extends ContainerBlockEntity implements MenuProvider {
    public ExampleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EMBlockEntityTypes.EXAMPLE.get(), blockPos, blockState);
        addItemHandler(HandlerUtils::newItemStackHandler, builder -> builder
                .slots(9)
                .onChange(slot -> this.updateData()));
    }

    @Override
    public Component getDisplayName() {
        return EMTranslations.EXAMPLE_SCREEN_TITLE.component();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ExampleMenu(i, inventory, this);
    }
}
