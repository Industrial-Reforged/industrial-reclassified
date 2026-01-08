package com.portingdeadmods.examplemod.api.menus;

import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.gui.menus.PDLAbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public class MachineMenu<BE extends MachineBlockEntity> extends PDLAbstractContainerMenu<BE> {
    public MachineMenu(MenuType<?> menuType, int containerId, @NotNull Inventory inv, @NotNull BE blockEntity) {
        super(menuType, containerId, inv, blockEntity);
    }

    @Override
    protected int getMergeableSlotCount() {
        return 0;
    }
}
