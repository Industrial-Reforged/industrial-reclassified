package com.portingdeadmods.examplemod.compat.jei;

import com.portingdeadmods.examplemod.content.recipes.MachineRecipe;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeInput;
import com.portingdeadmods.examplemod.content.recipes.MachineRecipeLayout;
import com.portingdeadmods.examplemod.content.recipes.components.TimeComponent;
import com.portingdeadmods.examplemod.content.recipes.flags.InputComponentFlag;
import com.portingdeadmods.examplemod.content.recipes.flags.OutputComponentFlag;
import com.portingdeadmods.examplemod.registries.IRItems;
import com.portingdeadmods.examplemod.registries.IRRecipeComponentFlags;
import com.portingdeadmods.examplemod.registries.IRRecipeLayouts;
import com.portingdeadmods.portingdeadlibs.api.recipes.IngredientWithCount;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class MachineRecipeCategory extends AbstractRecipeCategory<MachineRecipe> {
    public MachineRecipeCategory(IGuiHelper guiHelper, MachineRecipeLayout<?> layout, Component title, ItemLike machineIcon) {
        super(RecipeType.create(layout.getId().getNamespace(), layout.getId().getPath(), MachineRecipe.class), title, guiHelper.createDrawableItemLike(machineIcon), 80, 64);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MachineRecipe recipe, IFocusGroup focuses) {
        InputComponentFlag input = recipe.getComponentByFlag(IRRecipeComponentFlags.INPUT);
        OutputComponentFlag output = recipe.getComponentByFlag(IRRecipeComponentFlags.OUTPUT);

        for (int i = 0; i < input.getIngredients().size(); i++) {
            IngredientWithCount ingredient = input.getIngredients().get(i);
            float chance;
            if (input.getChances().size() > i) {
                chance = input.getChances().get(i);
            } else {
                chance = 1f;
            }

            IRecipeSlotBuilder slotBuilder = builder.addInputSlot(i * 16, this.getHeight() / 2 - 8).addIngredients(ingredient.toIngredientSaveCount());
            if (chance < 1f) {
                slotBuilder.addRichTooltipCallback((view, tooltip) -> {
                    tooltip.add(Component.literal("Chance " + chance * 100 + "%").withStyle(ChatFormatting.DARK_GRAY));
                });
            }
        }

        if (output != null) {
            for (int i = 0; i < output.getOutputs().size(); i++) {
                ItemStack outputItem = output.getOutputs().get(i);
                float chance;
                if (output.getChances().size() > i) {
                    chance = output.getChances().get(i);
                } else {
                    chance = 1f;
                }

                IRecipeSlotBuilder slotBuilder = builder.addOutputSlot(this.getWidth() - output.getOutputs().size() * 16 + i * 16, this.getHeight() / 2 - 8).addItemStack(outputItem);
                if (chance < 1f) {
                    slotBuilder.addRichTooltipCallback((view, tooltip) -> {
                        tooltip.add(Component.literal("Chance " + chance * 100 + "%").withStyle(ChatFormatting.DARK_GRAY));
                    });
                }
            }
        } else if (recipe.getId().equals(IRRecipeLayouts.FOOD_CANNING_MACHINE.getId())) {
            builder.addOutputSlot(this.getWidth() - 16, this.getHeight() / 2 - 8)
                    .addItemStack(IRItems.TIN_CAN_FOOD.toStack())
                    .addRichTooltipCallback((view, tooltip) -> tooltip.add(Component.literal("Any Food Item").withStyle(ChatFormatting.DARK_GRAY)));
        }

    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, MachineRecipe recipe, IFocusGroup focuses) {
        InputComponentFlag input = recipe.getComponentByFlag(IRRecipeComponentFlags.INPUT);
        builder.addAnimatedRecipeArrow(recipe.getComponent(TimeComponent.TYPE).time()).setPosition(this.getWidth() / 2 - 12, this.getHeight() / 2 - 8);
    }
}
