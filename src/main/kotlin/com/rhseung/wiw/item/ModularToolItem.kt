package com.rhseung.wiw.item

import com.rhseung.wiw.util.Color
import com.rhseung.wiw.util.Color.Companion.gradient
import net.minecraft.block.Block
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ToolComponent
import net.minecraft.component.type.ToolComponent.Rule
import net.minecraft.item.*
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.registry.tag.TagKey
import net.minecraft.text.Text
import kotlin.math.roundToInt

class ModularToolItem(
    val effectiveBlocks: TagKey<Block>,
    vararg val tools: ToolItem
) : Item(Settings()
    .component(DataComponentTypes.TOOL, createComponent(effectiveBlocks, tools))
    .maxDamage(tools.sumOf { it.material.durability } / 10)
) {

    companion object {
        fun getLevel(tools: Array<out ToolItem>): Int {
            return tools.maxOf { when (it.material) {
                ToolMaterials.WOOD -> 0
                ToolMaterials.GOLD -> 0
                ToolMaterials.STONE -> 1
                ToolMaterials.IRON -> 2
                ToolMaterials.DIAMOND -> 3
                ToolMaterials.NETHERITE -> 4
                else -> throw IllegalArgumentException("Unknown material")
            } };
        }

        fun getMaterial(tools: Array<out ToolItem>): ToolMaterial {
            return when (getLevel(tools)) {
                0 -> ToolMaterials.WOOD
                1 -> ToolMaterials.STONE
                2 -> ToolMaterials.IRON
                3 -> ToolMaterials.DIAMOND
                4 -> ToolMaterials.NETHERITE
                else -> throw IllegalArgumentException("Unknown material")
            }
        }

        fun getMiningSpeed(tools: Array<out ToolItem>): Float {
            var sum = 0.0F;
            tools.forEach {
                sum += it.material.miningSpeedMultiplier;
            }
            return sum;
        }

        fun createComponent(effectiveBlocks: TagKey<Block>, tools: Array<out ToolItem>): ToolComponent {
            return ToolComponent(listOf(
                Rule.ofNeverDropping(getMaterial(tools).inverseTag),
                Rule.ofAlwaysDropping(effectiveBlocks, getMiningSpeed(tools))),
                1.0F, 1);
        }
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean {
        return tools.any { it.canRepair(stack, ingredient) }
    }

    override fun getEnchantability(): Int {
        return tools.map { it.enchantability }.average().roundToInt()
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        val maxDamage = stack.maxDamage;
        val damage = stack.damage;
        val remain = (maxDamage - damage).toFloat() / maxDamage;

        return (Color.LAPIS to Color.REDSTONE).gradient(remain).toInt();
    }

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        super.appendTooltip(stack, context, tooltip, type)

        tooltip.add(Text.of("Level: ${getLevel(tools)}"))
        tooltip.add(Text.of("Mining Speed: ${getMiningSpeed(tools)}"))
        tooltip.add(Text.of("Enchantability: ${enchantability}"))

        val maxDamage = stack.maxDamage;
        val damage = stack.damage;
        val remain = (maxDamage - damage).toFloat() / maxDamage;
        tooltip.add(Text.literal("Durability: ")
            .append(Text.literal("${maxDamage - damage}").withColor((Color.LAPIS to Color.REDSTONE).gradient(remain).toInt()))
            .append(Text.literal(" / $maxDamage")))

        fun materialName(material: ToolMaterial): String {
            return when (material) {
                ToolMaterials.WOOD -> "Wood"
                ToolMaterials.GOLD -> "Gold"
                ToolMaterials.STONE -> "Stone"
                ToolMaterials.IRON -> "Iron"
                ToolMaterials.DIAMOND -> "Diamond"
                ToolMaterials.NETHERITE -> "Netherite"
                else -> throw IllegalArgumentException("Unknown material")
            }
        }

        tooltip.add(Text.of("Structure:"))
        tools.forEach {
            tooltip.add(Text.literal("  ${materialName(it.material)}")
                    .withColor(Color.materialToColor(it.material).toInt()))
        }
    }
}
