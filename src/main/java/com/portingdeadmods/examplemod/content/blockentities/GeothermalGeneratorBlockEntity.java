package com.portingdeadmods.examplemod.content.blockentities;

import com.portingdeadmods.examplemod.IRConfig;
import com.portingdeadmods.examplemod.api.blockentities.GeneratorBlockEntity;
import com.portingdeadmods.examplemod.api.blockentities.MachineBlockEntity;
import com.portingdeadmods.examplemod.content.menus.GeothermalGeneratorMenu;
import com.portingdeadmods.examplemod.impl.energy.EnergyHandlerImpl;
import com.portingdeadmods.examplemod.registries.IREnergyTiers;
import com.portingdeadmods.examplemod.registries.IRMachines;
import com.portingdeadmods.examplemod.registries.IRTranslations;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import com.portingdeadmods.portingdeadlibs.utils.capabilities.CapabilityUtils;
import com.portingdeadmods.portingdeadlibs.utils.capabilities.HandlerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeothermalGeneratorBlockEntity extends MachineBlockEntity implements MenuProvider, GeneratorBlockEntity {
    public GeothermalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IRMachines.GEOTHERMAL_GENERATOR, blockPos, blockState);
        this.addEuStorage(EnergyHandlerImpl.NoFill::new, IREnergyTiers.MEDIUM, IRConfig.geothermalGeneratorEnergyCapacity, this::onEuChanged);
        this.addFluidHandler(HandlerUtils::newFluidTank, builder -> builder
                .slotLimit(tank -> IRConfig.geothermalGeneratorFluidCapacity)
                .onChange(this::onFluidChanged)
                .validator((tank, fluid) -> true));
    }

    private void onFluidChanged(int tank) {
        this.updateData();
    }

    @Override
    public int getGenerationAmount() {
        return IRConfig.geothermalGeneratorEnergyProduction;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return IRTranslations.GEOTHERMAL_GENERATOR.component();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new GeothermalGeneratorMenu(i, inventory, this);
    }

}
