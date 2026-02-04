package com.portingdeadmods.examplemod.content.blockentities;

import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.content.menus.RecyclerMenu;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.registries.IRTranslations;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RecyclerBlockEntity extends MachineBlockEntity implements MenuProvider {
    public RecyclerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IRMachines.RECYCLER.getBlockEntityType(), blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return IRTranslations.RECYCLER.component();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RecyclerMenu(i, inventory, this);
    }
}
