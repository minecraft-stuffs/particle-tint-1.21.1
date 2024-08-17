package com.rhseung.wiw

import com.google.common.math.IntMath.pow
import com.rhseung.wiw.init.ModItems
import com.rhseung.wiw.item.ModularToolItem
import com.rhseung.wiw.util.Color
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.item.ItemStack

object WhatIWantClient : ClientModInitializer {
    override fun onInitializeClient() {
        ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
            Color.materialToColor((stack.item as ModularToolItem).tools[tintIndex].material).toInt() - pow(16, 6)
        }, *ModItems.MODULAR_PICKAXES);
    }
}