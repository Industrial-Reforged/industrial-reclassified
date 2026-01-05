package com.portingdeadmods.examplemod.content.items;

import com.portingdeadmods.examplemod.registries.IRFoodProperties;
import net.minecraft.world.item.Item;

public class TinCanWithFoodItem extends Item {
    public TinCanWithFoodItem(Properties properties) {
        super(properties);
    }

    public static TinCanWithFoodItem defaultItem(Properties properties) {
        return new TinCanWithFoodItem(properties.food(IRFoodProperties.EMPTY));
    }
}
