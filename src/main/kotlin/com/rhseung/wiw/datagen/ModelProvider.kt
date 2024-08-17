package com.rhseung.wiw.datagen

import com.rhseung.wiw.WhatIWant
import com.rhseung.wiw.init.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import java.util.*

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {}

    private fun item(parent: String, vararg requiredTextureKeys: TextureKey): Model {
        return Model(Optional.of(Identifier.ofVanilla("item/$parent")), Optional.empty(), *requiredTextureKeys)
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        ModItems.MODULAR_PICKAXES.forEach {
            item("handheld", TextureKey.LAYER0, TextureKey.LAYER1, TextureKey.LAYER2).upload(
                ModelIds.getItemModelId(it),
                TextureMap.layered(
                    Identifier.of(WhatIWant.MOD_ID, "item/handle"),
                    Identifier.of(WhatIWant.MOD_ID, "item/head"),
                    Identifier.of(WhatIWant.MOD_ID, "item/binding")
                ),
                itemModelGenerator.writer
            )
        }
    }
}