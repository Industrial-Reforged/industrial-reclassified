package com.portingdeadmods.examplemod.content.menus;

import com.portingdeadmods.examplemod.api.menus.MachineMenu;
import com.portingdeadmods.examplemod.content.blockentities.WaterMillBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class WaterMillMenu extends MachineMenu<WaterMillBlockEntity> {
    public WaterMillMenu(int containerId, @NotNull Inventory inv, @NotNull WaterMillBlockEntity blockEntity) {
        super(IRMachines.WATER_MILL.getMenuType(), containerId, inv, blockEntity);
    }

    public WaterMillMenu(int containerId, @NotNull Inventory inv, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId, inv, (WaterMillBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

}
