package com.portingdeadmods.examplemod.content.recipes;

import com.portingdeadmods.examplemod.api.recipes.RecipeComponent;
import com.portingdeadmods.examplemod.api.recipes.RecipeComponentFlag;
import com.portingdeadmods.examplemod.api.recipes.RecipeFlagType;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputComponent;
import com.portingdeadmods.examplemod.content.recipes.components.items.ItemInputListComponent;
import com.portingdeadmods.examplemod.content.recipes.flags.InputComponentFlag;
import com.portingdeadmods.examplemod.registries.IRItems;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FoodCanningMachineRecipe extends MachineRecipe {
    public FoodCanningMachineRecipe(ResourceLocation id, Map<String, RecipeComponent> components) {
        super(id, components);
    }

    @Override
    public <F extends RecipeComponentFlag> F getComponentByFlag(RecipeFlagType<F> flagType) {
        if (flagType == IRRecipeComponentFlags.INPUT) {
            return (F) new ItemInputListComponent(List.of(this.getComponent(ItemInputComponent.TYPE), new ItemInputComponent(Tags.Items.FOODS)));
        }
        return super.getComponentByFlag(flagType);
    }

    @Override
    public boolean matches(MachineRecipeInput machineRecipeInput, Level level) {
        if (machineRecipeInput.getItem(0).has(DataComponents.FOOD) || machineRecipeInput.getItem(1).has(DataComponents.FOOD)) {
            return this.getComponent(ItemInputComponent.TYPE).test(machineRecipeInput.getItem(0))
                    || this.getComponent(ItemInputComponent.TYPE).test(machineRecipeInput.getItem(1));
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull MachineRecipeInput container, HolderLookup.Provider provider) {
        FoodProperties food;
        if (container.getItem(0).has(DataComponents.FOOD)) {
            food = container.getItem(0).get(DataComponents.FOOD);
        } else if (container.getItem(1).has(DataComponents.FOOD)) {
            food = container.getItem(1).get(DataComponents.FOOD);
        } else {
            return ItemStack.EMPTY;
        }

        ItemStack foodCanStack = IRItems.TIN_CAN_FOOD.toStack();

        food = new FoodProperties(food.nutrition(), food.saturation(), food.canAlwaysEat(), food.eatSeconds(), Optional.of(IRItems.TIN_CAN.toStack()), food.effects());

        foodCanStack.set(DataComponents.FOOD, food);

        return foodCanStack;
    }

}
