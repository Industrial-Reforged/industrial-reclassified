package com.portingdeadmods.examplemod.client.screens;

import com.portingdeadmods.examplemod.content.menus.ExampleMenu;
import com.portingdeadmods.portingdeadlibs.api.client.screens.PDLAbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ExampleScreen extends PDLAbstractContainerScreen<ExampleMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/dispenser.png");

    public ExampleScreen(ExampleMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundTexture() {
        return BACKGROUND_TEXTURE;
    }
}
