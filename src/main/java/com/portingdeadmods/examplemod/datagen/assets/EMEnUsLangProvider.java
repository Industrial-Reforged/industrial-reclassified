package com.portingdeadmods.examplemod.datagen.assets;

import com.portingdeadmods.examplemod.ExampleMod;
import com.portingdeadmods.examplemod.registries.EMBlocks;
import com.portingdeadmods.examplemod.registries.EMItems;
import com.portingdeadmods.examplemod.registries.EMTranslations;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EMEnUsLangProvider extends LanguageProvider {
    public EMEnUsLangProvider(PackOutput output) {
        super(output, ExampleMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        EMTranslations.TRANSLATIONS.getDefaultTranslations().forEach(this::add);

        addItem(EMItems.EXAMPLE_ITEM, "Example Item");

        addBlock(EMBlocks.EXAMPLE_BLOCK, "Example Block");
    }

}
