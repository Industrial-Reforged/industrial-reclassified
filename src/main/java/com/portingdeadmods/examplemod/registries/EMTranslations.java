package com.portingdeadmods.examplemod.registries;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.portingdeadlibs.api.translations.DeferredTranslation;
import com.portingdeadmods.portingdeadlibs.api.translations.DeferredTranslationRegister;
import com.portingdeadmods.portingdeadlibs.api.translations.TranslatableConstant;
import com.portingdeadmods.portingdeadlibs.api.translations.TranslationCategory;

public final class EMTranslations {
    public static final DeferredTranslationRegister TRANSLATIONS = DeferredTranslationRegister.createTranslations(ExampleMod.MODID);

    // -- Messages --
    private static final TranslationCategory MESSAGES_CATEGORY = TRANSLATIONS.createCategory("messages");

    public static final DeferredTranslation<TranslatableConstant> NICE_MESSAGE = MESSAGES_CATEGORY.registerWithDefault("nice_message", "Hello, silly :3");

    // -- Creative Tabs --
    private static final TranslationCategory CREATIVE_TAB_CATEGORY = TRANSLATIONS.createCategory("creative_tabs");

    public static final DeferredTranslation<TranslatableConstant> MAIN_TAB = CREATIVE_TAB_CATEGORY.registerWithDefault("main", ExampleMod.MODNAME);

    // -- Screens --
    private static final TranslationCategory SCREEN_CATEGORY = TRANSLATIONS.createCategory("screens");

    public static final DeferredTranslation<TranslatableConstant> EXAMPLE_SCREEN_TITLE = CREATIVE_TAB_CATEGORY.registerWithDefault("title.example", "Example");
}
