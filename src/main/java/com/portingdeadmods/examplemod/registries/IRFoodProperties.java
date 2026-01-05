package com.portingdeadmods.examplemod.registries;

import net.minecraft.world.food.FoodProperties;

public final class IRFoodProperties {
    public static final FoodProperties EMPTY = new FoodProperties.Builder().usingConvertsTo(IRItems.TIN_CAN).build();
}
