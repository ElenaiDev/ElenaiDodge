package com.elenai.elenaidodge.gui;

import org.lwjgl.opengl.GL11;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.util.ClientStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DodgeGui {

	public static ResourceLocation DODGE_ICONS = new ResourceLocation(ElenaiDodge.MODID, "textures/gui/icons.png");
	public static float alpha = 1.0f;

	@SubscribeEvent
	public void onRenderDodgeGUIEvent(RenderGameOverlayEvent.Post event) {
		if (ModConfig.client.hud.hud && !Minecraft.getMinecraft().player.isCreative()
				&& !Minecraft.getMinecraft().player.isSpectator() && !Minecraft.getMinecraft().isGamePaused()) {

			if ((event.getType() == ElementType.ALL)) {
				Minecraft.getMinecraft().getTextureManager().bindTexture(DODGE_ICONS);
				GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
						GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
				enableAlpha(alpha);

				int i = Minecraft.getMinecraft().gameSettings.thirdPersonView > 0
						? event.getResolution().getScaledHeight() - 64 + 3
						: (event.getResolution().getScaledHeight() / 2 - 7 + 16);
				int j = event.getResolution().getScaledWidth() / 2 - 8 - 5;
				if (alpha > 0 && ClientStorage.dodges > 0) {
					
					if (ModConfig.client.hud.legacyHud) {
						
						// Legacy Hud
						int scaledHeight = event.getResolution().getScaledHeight() - 32 + 3; //Get the Y dimension to render GUI
						int scaledWidth = event.getResolution().getScaledWidth() / 2 - 91; // Get the X dimension to render GUI
						enableAlpha(.75f);
						gui.drawTexturedModalRect(scaledWidth, scaledHeight, 0, 16, (int) Math.ceil(ClientStorage.dodgesDouble*(182/27)), 5);
						disableAlpha(.75f);

					} else {
						// Modern Hud
						gui.drawTexturedModalRect(j, i, 16, 0, 27, 16);
						gui.drawTexturedModalRect(j, i, 16 + 27, 0, ClientStorage.dodges, 16);
					}
				}

				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
						GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
						GlStateManager.DestFactor.ZERO);
				disableAlpha(alpha);
				Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
			}
		}
	}

	public static void enableAlpha(float alpha) {
		GlStateManager.enableBlend();

		if (alpha == 1f)
			return;

		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void disableAlpha(float alpha) {
		GlStateManager.disableBlend();

		if (alpha == 1f)
			return;

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
