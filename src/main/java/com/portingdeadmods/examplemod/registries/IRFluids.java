package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.IndustrialReclassified;
import com.portingdeadmods.examplemod.content.fluids.BioFuelFluid;
import com.portingdeadmods.portingdeadlibs.utils.FluidRegistrationHelper;

public final class IRFluids {
    public static final FluidRegistrationHelper HELPER = new FluidRegistrationHelper(IRBlocks.BLOCKS, IRItems.ITEMS, IndustrialReclassified.MODID);

    public static final BioFuelFluid BIO_FUEL = HELPER.registerFluid(new BioFuelFluid("bio_fuel"));
}
