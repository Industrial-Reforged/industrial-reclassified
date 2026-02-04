package com.portingdeadmods.examplemod.content.menus;

import com.portingdeadmods.examplemod.api.menus.MachineMenu;
import com.portingdeadmods.examplemod.content.blockentities.RecyclerBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.registries.IRMenuTypes;
import com.portingdeadmods.examplemod.registries.IRTranslations;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecyclerMenu extends MachineMenu<RecyclerBlockEntity> {
    public RecyclerMenu(int containerId, @NotNull Inventory inv, @NotNull RecyclerBlockEntity blockEntity) {
        super(IRMachines.RECYCLER.getMenuType(), containerId, inv, blockEntity);
    }

    public RecyclerMenu(int containerId, @NotNull Inventory inv, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId, inv, (RecyclerBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }
}
