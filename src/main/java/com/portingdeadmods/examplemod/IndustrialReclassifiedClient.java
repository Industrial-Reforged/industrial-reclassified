package com.portingdeadmods.examplemod;

import com.portingdeadmods.examplemod.api.fluid.SimpleFluidItem;
import com.portingdeadmods.examplemod.client.items.IRItemProperties;
import com.portingdeadmods.examplemod.client.screens.BasicGeneratorScreen;
import com.portingdeadmods.examplemod.client.screens.ExampleScreen;
import com.portingdeadmods.examplemod.content.items.electric.BatteryItem;
import com.portingdeadmods.examplemod.registries.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = IndustrialReclassified.MODID, dist = Dist.CLIENT)
public final class IndustrialReclassifiedClient {
    public IndustrialReclassifiedClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::registerMenuScreens);
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerItemColor);
        modEventBus.addListener(this::registerEntityRenderers);

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(IRMachines.BASIC_GENERATOR.getMenuType(), BasicGeneratorScreen::new);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerItemProperties();
            ItemBlockRenderTypes.setRenderLayer(IRBlocks.REINFORCED_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(IRBlocks.REINFORCED_GLASS.get(), RenderType.cutout());
        });
    }

    private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(IREntityTypes.INDUSTRIAL_TNT.get(), TntRenderer::new);
    }

    private static void registerItemProperties() {
        ItemProperties.register(IRItems.NANO_SABER.get(), IRItemProperties.ACTIVE_KEY, (ClampedItemPropertyFunction) IRItemProperties::isActive);
        ItemProperties.register(IRItems.BASIC_CHAINSAW.get(), IRItemProperties.ACTIVE_KEY, (ClampedItemPropertyFunction) IRItemProperties::isItemHeld);
        ItemProperties.register(IRItems.ADVANCED_CHAINSAW.get(), IRItemProperties.ACTIVE_KEY, (ClampedItemPropertyFunction) IRItemProperties::isItemHeld);
        ItemProperties.register(IRItems.BASIC_DRILL.get(), IRItemProperties.ACTIVE_KEY, (ClampedItemPropertyFunction) IRItemProperties::isItemHeld);
        ItemProperties.register(IRItems.ADVANCED_DRILL.get(), IRItemProperties.ACTIVE_KEY, (ClampedItemPropertyFunction) IRItemProperties::isItemHeld);

        // IMPORTANT: WE DON'T USE CLAMPED ITEM PROPERTY FUNCTION HERE CUZ IT MEANS PROPERTIES CANT GO ABOVE 1
//        ItemProperties.register(IRItems.JETPACK.get(), IRItemProperties.JETPACK_STAGE_KEY, IRItemProperties::getJetpackStage);
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof BatteryItem) {
                ItemProperties.register(item, IRItemProperties.BATTERY_STAGE_KEY, IRItemProperties::getBatteryStage);
            }
        }
    }

    private void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register(new SimpleFluidItem.Colors(), IRItems.FLUID_CELL.get());
//        for (PDLFluid fluid : IRFluids.HELPER.getFluids()) {
//            if (fluid instanceof MoltenMetalFluid) {
//                event.register(new DynamicFluidContainerModel.Colors(), fluid.getDeferredBucket());
//            }
//        }
    }

}
