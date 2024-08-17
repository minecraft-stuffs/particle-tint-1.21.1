package com.rhseung.wiw.init

import com.rhseung.wiw.WhatIWant
import com.rhseung.wiw.item.ModularToolItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.Items
import net.minecraft.item.ToolItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.Identifier

object ModItems {
    fun load() {}

    fun <T: Item> register(id: Identifier, entry: T): T {
        val ret = Registry.register(Registries.ITEM, id, entry);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
            .register { group -> group.add(ret) }

        return ret;
    }

    fun registerModular(id: String, handle: Item, head: Item, binding: Item): ModularToolItem {
        return register(Identifier.of(WhatIWant.MOD_ID, id), ModularToolItem(BlockTags.PICKAXE_MINEABLE,
            handle as ToolItem, head as ToolItem, binding as ToolItem));
    }

    val MODULAR_PICKAXE_1 = registerModular("modular_pickaxe_1",
        Items.WOODEN_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE);

    val MODULAR_PICKAXE_2 = registerModular("modular_pickaxe_2",
        Items.WOODEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_PICKAXE);

    val MODULAR_PICKAXE_3 = registerModular("modular_pickaxe_3",
        Items.WOODEN_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE);

    val MODULAR_PICKAXES = arrayOf(
        MODULAR_PICKAXE_1, MODULAR_PICKAXE_2, MODULAR_PICKAXE_3
    );
}