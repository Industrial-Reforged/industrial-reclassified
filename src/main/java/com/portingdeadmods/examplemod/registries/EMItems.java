package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.portingdeadlibs.api.utils.PDLDeferredRegisterItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EMItems {
    public static final PDLDeferredRegisterItems ITEMS = PDLDeferredRegisterItems.createItemsRegister(ExampleMod.MODID);

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerItem("example_item", Item::new);

}
