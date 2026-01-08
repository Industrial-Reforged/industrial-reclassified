package com.portingdeadmods.examplemod.registries;


import com.portingdeadmods.examplemod.api.blocks.MachineBlock;
import com.portingdeadmods.examplemod.content.blockentities.BasicGeneratorBlockEntity;
import com.portingdeadmods.examplemod.content.menus.BasicGeneratorMenu;
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
//    public static final IRMachine CENTRIFUGE = HELPER.registerMachine("centrifuge", IRMachine.builder(IREnergyTiers.LOW)
//            .block(CentrifugeBlock::new)
//            .blockEntity(CentrifugeBlockEntity::new)
//            .menu(CentrifugeMenu::new));
//    public static final IRMachine BATTERY_BOX = HELPER.registerMachine("battery_box", IRMachine.builder(IREnergyTiers.LOW)
//            .block(BatteryBoxBlock::new)
//            .blockEntity(BatteryBoxBlockEntity::new)
//            .menu(BatteryBoxMenu::new));
}