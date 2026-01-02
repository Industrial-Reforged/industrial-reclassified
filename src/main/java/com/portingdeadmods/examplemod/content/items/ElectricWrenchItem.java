package com.portingdeadmods.examplemod.content.items;

import com.portingdeadmods.examplemod.api.energy.CustomWrenchableBlock;
import com.portingdeadmods.examplemod.api.energy.EnergyHandler;
import com.portingdeadmods.examplemod.api.energy.EnergyTier;
import com.portingdeadmods.examplemod.api.energy.SimpleEnergyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ElectricWrenchItem extends SimpleEnergyItem {
    public ElectricWrenchItem(Item.Properties properties, Supplier<? extends EnergyTier> energyTier, IntSupplier energyUsage, IntSupplier defaultEnergyCapacity) {
        super(properties, energyTier, defaultEnergyCapacity);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        Level level = useOnContext.getLevel();
        BlockPos clickPos = useOnContext.getClickedPos();
        BlockState wrenchableBlock = level.getBlockState(clickPos);
        ItemStack itemInHand = useOnContext.getItemInHand();
        EnergyHandler energyStorage = getEnergyCap(itemInHand);

        // only on the server side
        if (!level.isClientSide) {
            // check if block can be wrenched
            if (wrenchableBlock instanceof CustomWrenchableBlock iWrenchableBlockBlock
                    && iWrenchableBlockBlock.canWrench(level, clickPos, wrenchableBlock)
                    && player.isCrouching()
                    && energyStorage.getEnergyStored() >= 10) {
                // Drop the block itself instead of custom drop
                if (iWrenchableBlockBlock.getCustomDropItem().isEmpty()) {
                    ItemStack dropItem = wrenchableBlock.getBlock().asItem().getDefaultInstance();
                    ItemHandlerHelper.giveItemToPlayer(player, dropItem);
                }
                // Drop the custom drop
                else {
                    ItemStack dropItem = iWrenchableBlockBlock.getCustomDropItem();
                    ItemHandlerHelper.giveItemToPlayer(player, dropItem);
                }
                energyStorage.drainEnergy(10, false);
                level.removeBlock(clickPos, false);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public int getDefaultCapacity() {
        return 10000;
    }

}