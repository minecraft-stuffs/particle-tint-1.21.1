package com.rhseung.wiw.datagen

import com.rhseung.wiw.init.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class LanguageProvider(
    dataOutput: FabricDataOutput,
    languageCode: String,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricLanguageProvider(dataOutput, languageCode, registryLookup) {

    override fun generateTranslations(
        registryLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: TranslationBuilder
    ) {
        ModItems.MODULAR_PICKAXES.forEachIndexed { index, item ->
            translationBuilder.add(item, "Modular Pickaxe ${index + 1}");
        }
    }
}