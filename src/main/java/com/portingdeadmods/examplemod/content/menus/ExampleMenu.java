package com.portingdeadmods.examplemod.content.menus;

import com.portingdeadmods.examplemod.content.blockentities.ExampleBlockEntity;
import com.portingdeadmods.examplemod.registries.EMMenuTypes;
import com.portingdeadmods.portingdeadlibs.api.gui.menus.PDLAbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExampleMenu extends PDLAbstractContainerMenu<ExampleBlockEntity> {
    public ExampleMenu(int containerId, @NotNull Inventory inv, @NotNull FriendlyByteBuf byteBuf) {
        this(containerId, inv, (ExampleBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

    public ExampleMenu(int containerId, @NotNull Inventory inv, @NotNull ExampleBlockEntity blockEntity) {
        super(EMMenuTypes.EXAMPLE.get(), containerId, inv, blockEntity);

        int startX = 62;
        int startY = 17;
        int index = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                addSlot(new SlotItemHandler(blockEntity.getItemHandler(), index++, startX + x * 18, startY + y * 18));
            }
        }

        addPlayerInventory(inv, 83 + 1);
        addPlayerHotbar(inv, 141 + 1);

    }

    @Override
    protected int getMergeableSlotCount() {
        return 9;
    }
}
