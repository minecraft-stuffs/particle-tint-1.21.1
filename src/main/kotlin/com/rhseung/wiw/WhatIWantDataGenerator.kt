package com.rhseung.wiw

import com.rhseung.wiw.datagen.LanguageProvider
import com.rhseung.wiw.datagen.ModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object WhatIWantDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack();

		pack.addProvider { output, registriesFuture -> LanguageProvider(output, "en_us", registriesFuture) }
		pack.addProvider { output, _ -> ModelProvider(output) }
	}
}