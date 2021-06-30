package com.elenai.elenaidodge.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.integration.ConstructsArmory;
import com.elenai.elenaidodge.util.ClientStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This single class is based off the class FoodTooltip used by Vazkii in Quark:
 * https://github.com/Vazkii/Quark/blob/1.12/src/main/java/vazkii/quark/client/feature/FoodTooltip.java
 * Both Quark and Elenai Dodge are issued under an altered version of the
 * Creative Commons license.
 * 
 * @author Vazkii
 */
public class TooltipEventListener {

	public static final ResourceLocation ICONS_RESOURCE = new ResourceLocation(ElenaiDodge.MODID,
			"textures/gui/icons.png");

	public static int divisor = 2;

	static List<String> weights = new ArrayList<String>();

	private static double getWeight(ItemStack item) {
		if (ClientStorage.weightValues == null) {
			return 1;
		}
		Collections.addAll(weights, ClientStorage.weightValues.split(","));
		for (String weight : weights) {
			String[] itemValue = weight.split("="); // itemValue[0] is id, itemValue[1] is weight
			if (item.getItem() == Item.getByNameOrId(itemValue[0])) {
				weights.clear();
				return Double.valueOf(itemValue[1]);
			}
		}

		if (Loader.isModLoaded("conarm")) {
			if (ConstructsArmory.getWeight(item) > 0) {
				weights.clear();
				return (int) Math.floor(ConstructsArmory.getWeight(item));
			}
		}
		weights.clear();
		ItemArmor armorItem = (ItemArmor) item.getItem();
		return Double.valueOf((armorItem.damageReduceAmount / 2) * 1.8);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void makeTooltip(ItemTooltipEvent event) {
		if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ItemArmor
				&& ClientStorage.weightValues != null) {
			if (ModConfig.client.hud.tooltips && ClientStorage.weightsEnabled) {
				double weight = getWeight(event.getItemStack());
				if (weight > 0) {

					StringBuilder s = new StringBuilder("   " + String.valueOf(weight));

					List<String> tooltip = event.getToolTip();
					if (tooltip.isEmpty())
						tooltip.add(s.toString());
					else
						tooltip.add(1, s.toString());
				}
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderTooltip(RenderTooltipEvent.PostText event) {
		if (!event.getStack().isEmpty() && event.getStack().getItem() instanceof ItemArmor
				&& ModConfig.client.hud.tooltips && ClientStorage.weightsEnabled) {

			GlStateManager.pushMatrix();
			GlStateManager.color(1F, 1F, 1F);
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().bindTexture(ICONS_RESOURCE);
			int y = shiftTextByLines(event.getLines(), event.getY() + 10);
			int x = event.getX() + 0 * 9;

			int v = 0;
			int u = 71;

			Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, 9, 9, 256, 256);

			GlStateManager.popMatrix();
		}
	}

	public static int shiftTextByLines(List<String> lines, int y) {
		for (int i = 1; i < lines.size(); i++) {
			String s = lines.get(i);
			s = TextFormatting.getTextWithoutFormattingCodes(s);
			if (s != null && (s.trim().isEmpty() || s.contains("  "))) {
				y += 10 * (i - 1) + 1;
				break;
			}
		}
		return y;
	}
}
