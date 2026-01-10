package com.portingdeadmods.examplemod.content.menus;

import com.portingdeadmods.examplemod.api.menus.MachineMenu;
import com.portingdeadmods.examplemod.content.blockentities.CompressorBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class CompressorMenu extends MachineMenu<CompressorBlockEntity> {
    public CompressorMenu(int containerId, @NotNull Inventory inv, @NotNull CompressorBlockEntity blockEntity) {
        super(IRMachines.COMPRESSOR.getMenuType(), containerId, inv, blockEntity);
    }

    public CompressorMenu(int containerId, @NotNull Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, (CompressorBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

}
