package com.portingdeadmods.examplemod.registries;


import com.portingdeadmods.examplemod.api.blocks.MachineBlock;
import com.portingdeadmods.examplemod.content.blockentities.*;
import com.portingdeadmods.examplemod.content.menus.*;
import com.portingdeadmods.examplemod.utils.machines.IRMachine;
import com.portingdeadmods.examplemod.utils.machines.MachineRegistrationHelper;

public final class IRMachines {
    public static final MachineRegistrationHelper HELPER = new MachineRegistrationHelper(IRBlocks.BLOCKS, IRItems.ITEMS, IRBlockEntityTypes.BLOCK_ENTITY_TYPES, IRMenuTypes.MENU_TYPES);

    public static final IRMachine BASIC_GENERATOR = HELPER.registerMachine("basic_generator", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(BasicGeneratorBlockEntity::new)
            .menu(BasicGeneratorMenu::new));
//    public static final IRMachine GEOTHERMAL_GENERATOR = HELPER.registerMachine("geothermal_generator", IRMachine.builder(IREnergyTiers.MEDIUM)
//            .block(MachineBlock::new, builder -> builder
//                    .activatable()
//                    .rotatable()
//                    .ticking()
//            )
//            .blockEntity(BasicGeneratorBlockEntity::new)
//            .menu(BasicGeneratorMenu::new));
    public static final IRMachine ELECTRIC_FURNACE = HELPER.registerMachine("electric_furnace", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(ElectricFurnaceBlockEntity::new)
            .menu(ElectricFurnaceMenu::new));
    public static final IRMachine CHARGE_PAD = HELPER.registerMachine("charge_pad", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(ChargePadBlockEntity::new));
    public static final IRMachine COMPRESSOR = HELPER.registerMachine("compressor", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(CompressorBlockEntity::new)
            .menu(CompressorMenu::new));
    public static final IRMachine RECYCLER = HELPER.registerMachine("recycler", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(RecyclerBlockEntity::new)
            .menu(RecyclerMenu::new));
    public static final IRMachine MACERATOR = HELPER.registerMachine("macerator", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(MaceratorBlockEntity::new)
            .menu(MaceratorMenu::new));
    public static final IRMachine EXTRACTOR = HELPER.registerMachine("extractor", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(ExtractorBlockEntity::new)
            .menu(ExtractorMenu::new));
    public static final IRMachine CANNING_MACHINE = HELPER.registerMachine("canning_machine", IRMachine.builder(IREnergyTiers.MEDIUM)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(CanningMachineBlockEntity::new)
            .menu(CanningMachineMenu::new));
    public static final IRMachine BASIC_SOLAR_PANEL = HELPER.registerMachine("basic_solar_panel", IRMachine.builder(IREnergyTiers.LOW)
            .block(MachineBlock::new, builder -> builder
                    .activatable()
                    .rotatable()
                    .ticking()
            )
            .blockEntity(SolarPanelBlockEntity::new)
            .menu(SolarPanelMenu::new));
//    public static final IRMachine CENTRIFUGE = HELPER.registerMachine("centrifuge", IRMachine.builder(IREnergyTiers.LOW)
//            .block(CentrifugeBlock::new)
//            .blockEntity(CentrifugeBlockEntity::new)
//            .menu(CentrifugeMenu::new));
//    public static final IRMachine BATTERY_BOX = HELPER.registerMachine("battery_box", IRMachine.builder(IREnergyTiers.LOW)
//            .block(BatteryBoxBlock::new)
//            .blockEntity(BatteryBoxBlockEntity::new)
//            .menu(BatteryBoxMenu::new));
}