package com.portingdeadmods.examplemod;

import com.portingdeadmods.examplemod.api.energy.EnergyItem;
import com.portingdeadmods.examplemod.content.worldgen.IRPlacerTypes;
import com.portingdeadmods.examplemod.impl.energy.ItemEnergyHandlerWrapper;
import com.portingdeadmods.examplemod.registries.*;
import com.portingdeadmods.portingdeadlibs.api.config.PDLConfigHelper;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IFluidItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(IndustrialReclassified.MODID)
public final class IndustrialReclassified {
    public static final String MODID = "examplemod";
    public static final String MODNAME = "Example Mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public IndustrialReclassified(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerRegistries);

        IRItems.ITEMS.register(modEventBus);
        IREnergyTiers.ENERGY_TIERS.register(modEventBus);
        IRDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
        IRBlocks.BLOCKS.register(modEventBus);
        IRTranslations.TRANSLATIONS.register(modEventBus);
        IRCreativeTabs.TABS.register(modEventBus);
        IRBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        IREntityTypes.ENTITY_TYPES.register(modEventBus);
        IRMenuTypes.MENU_TYPES.register(modEventBus);
        IRPlacerTypes.FOLIAGE_PLACERS.register(modEventBus);
        IRPlacerTypes.TRUNK_PLACERS.register(modEventBus);
        IRMachines.HELPER.register(modEventBus);

        PDLConfigHelper.registerConfig(IRConfig.class, ModConfig.Type.COMMON, modContainer);
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(IRRegistries.ENERGY_TIER);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof EnergyItem energyItem) {
                event.registerItem(IRCapabilities.ENERGY_ITEM,
                        (stack, ctx) -> new ItemEnergyHandlerWrapper(stack, energyItem::getEnergyTier, energyItem.getDefaultCapacity()), item);
            }

            if (item instanceof IFluidItem fluidItem) {
                event.registerItem(Capabilities.FluidHandler.ITEM,
                        (stack, ctx) -> new FluidHandlerItemStackSimple(PDLDataComponents.FLUID, stack, fluidItem.getFluidCapacity()), item);
            }

        }

    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
