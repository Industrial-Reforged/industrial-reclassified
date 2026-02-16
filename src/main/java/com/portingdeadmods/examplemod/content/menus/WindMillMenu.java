package com.portingdeadmods.examplemod.content.menus;

import com.portingdeadmods.examplemod.api.menus.MachineMenu;
import com.portingdeadmods.examplemod.content.blockentities.WindMillBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class WindMillMenu extends MachineMenu<WindMillBlockEntity> {
    public WindMillMenu(int containerId, @NotNull Inventory inv, @NotNull WindMillBlockEntity blockEntity) {
        super(IRMachines.WIND_MILL.getMenuType(), containerId, inv, blockEntity);
    }

    public WindMillMenu(int containerId, @NotNull Inventory inv, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId, inv, (WindMillBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }
}
