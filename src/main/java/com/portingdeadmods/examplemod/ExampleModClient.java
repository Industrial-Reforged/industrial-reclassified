package com.portingdeadmods.examplemod;

import com.portingdeadmods.examplemod.client.screens.ExampleScreen;
import com.portingdeadmods.examplemod.registries.EMMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ExampleMod.MODID, dist = Dist.CLIENT)
public final class ExampleModClient {
    public ExampleModClient(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::registerMenuScreens);

        container.registerConfig(ModConfig.Type.CLIENT, ExampleModClientConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(EMMenuTypes.EXAMPLE.get(), ExampleScreen::new);
    }
}
