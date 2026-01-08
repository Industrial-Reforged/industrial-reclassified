package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.menus.BasicGeneratorMenu;
import com.portingdeadmods.examplemod.content.menus.ExampleMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class IRMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, IndustrialReclassified.MODID);

    public static final Supplier<MenuType<BasicGeneratorMenu>> EXAMPLE = MENU_TYPES.register("example", () -> IMenuTypeExtension.create(BasicGeneratorMenu::new));
}
