package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.examplemod.content.blocks.ExampleBlock;
import com.portingdeadmods.examplemod.content.menus.ExampleMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class EMMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, ExampleMod.MODID);

    public static final Supplier<MenuType<ExampleMenu>> EXAMPLE = MENU_TYPES.register("example", () -> IMenuTypeExtension.create(ExampleMenu::new));
}
