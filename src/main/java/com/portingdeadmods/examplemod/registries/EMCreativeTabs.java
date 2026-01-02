package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IRCapabilities;
import com.portingdeadmods.examplemod.IRDataComponents;
import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class EMCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, IndustrialReclassified.MODID);

    public static final Supplier<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(IRItems.EXAMPLE_ITEM::toStack)
            .title(Component.translatable("creative_tabs.examplemod.main"))
            .displayItems((params, out) -> {
                IRItems.ITEMS.getCreativeTabItems().stream().map(Supplier::get).map(ItemStack::new).peek(stack -> {
                    ItemStack copy = stack.copy();
                    EnergyHandler handler = copy.getCapability(IRCapabilities.ENERGY_ITEM);
                    if (handler != null) {
                        handler.setEnergyStored(handler.getEnergyCapacity());
                        out.accept(copy);
                    }
                }).forEach(out::accept);
            })
            .build());
}
