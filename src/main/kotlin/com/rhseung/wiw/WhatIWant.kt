package com.rhseung.wiw

import com.rhseung.wiw.init.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object WhatIWant : ModInitializer {
	const val MOD_ID = "wiw";
	private val LOGGER = LoggerFactory.getLogger(MOD_ID);

	override fun onInitialize() {
		ModItems.load()
	}
}