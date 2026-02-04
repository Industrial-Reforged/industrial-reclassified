package com.portingdeadmods.examplemod.client.screens;

import com.portingdeadmods.examplemod.api.client.screens.MachineScreen;
import com.portingdeadmods.examplemod.content.menus.RecyclerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class RecyclerScreen extends MachineScreen<RecyclerMenu> {
    public RecyclerScreen(RecyclerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return null;
    }
}
