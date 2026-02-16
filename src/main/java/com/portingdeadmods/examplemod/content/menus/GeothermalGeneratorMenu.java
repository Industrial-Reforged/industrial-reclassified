package com.portingdeadmods.examplemod.content.menus;

import com.portingdeadmods.examplemod.api.menus.MachineMenu;
import com.portingdeadmods.examplemod.content.blockentities.GeothermalGeneratorBlockEntity;
import com.portingdeadmods.examplemod.registries.IRMachines;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class GeothermalGeneratorMenu extends MachineMenu<GeothermalGeneratorBlockEntity> {
    public GeothermalGeneratorMenu(int containerId, @NotNull Inventory inv, @NotNull GeothermalGeneratorBlockEntity blockEntity) {
        super(IRMachines.GEOTHERMAL_GENERATOR.getMenuType(), containerId, inv, blockEntity);
        // do stuff
    }

    public GeothermalGeneratorMenu(int containerId, @NotNull Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, (GeothermalGeneratorBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

}
