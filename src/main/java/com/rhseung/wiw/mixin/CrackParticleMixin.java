package com.rhseung.wiw.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(CrackParticle.class)
public abstract class CrackParticleMixin {

	@Inject(method = "<init>(Lnet/minecraft/client/world/ClientWorld;DDDLnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
	private void onCrackParticleInit(ClientWorld world, double x, double y, double z, ItemStack stack, CallbackInfo ci) {
		CrackParticle that = (CrackParticle) (Object) this;
		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

		try {
			Field colorsField = ItemRenderer.class.getDeclaredField("colors");
			colorsField.setAccessible(true);

			ItemColors colors = (ItemColors) colorsField.get(itemRenderer);
			int i = colors.getColor(stack, 1);

			that.setColor(
				0.6F * (float) (i >> 16 & 255) / 255.0F,
				0.6F * (float) (i >> 8 & 255) / 255.0F,
				0.6F * (float) (i & 255) / 255.0F
			);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}