package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.ExampleMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class EMCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, ExampleMod.MODID);

    public static final Supplier<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(EMItems.EXAMPLE_ITEM::toStack)
            .title(Component.translatable("creative_tabs.examplemod.main"))
            .displayItems((params, out) -> {
                EMItems.ITEMS.getCreativeTabItems().stream().map(Supplier::get).forEach(out::accept);
            })
            .build());
}
