package com.elenai.elenaidodge.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.config.ConfigHandler;
import com.elenai.elenaidodge.util.ClientStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * This single class is based off the class FoodTooltip used by Vazkii in Quark:
 * https://github.com/Vazkii/Quark/blob/1.12/src/main/java/vazkii/quark/client/feature/FoodTooltip.java
 * Both Quark and Elenai Dodge are issued under an altered version of the Creative Commons license.
 * @author Vazkii
 */	
public class TooltipEventListener {
	public static final ResourceLocation ICONS_RESOURCE = new ResourceLocation(ElenaiDodge.MODID, "textures/gui/icons.png");

	public static int divisor = 2;

	static List<String> weights = new ArrayList<String>();

	private static int getWeight(Item item) {
		if(ClientStorage.weightValues == null) { return 1;}
		Collections.addAll(weights, ClientStorage.weightValues.split(","));
		for (String weight : weights) {
			String[] itemValue = weight.split("="); // itemValue[0] is id, itemValue[1] is weight
			if (item.getRegistryName().equals(new ResourceLocation(itemValue[0]))) {
				weights.clear();
				return (int) Math.floor(Double.valueOf(itemValue[1]));
			}
		}
		weights.clear();
		return 0;
	}
	
	@SubscribeEvent
	public void makeTooltip(ItemTooltipEvent event) {
		if(!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ArmorItem && ClientStorage.weightValues != null) {
			if(ConfigHandler.tooltips) {
			int weight =  getWeight(event.getItemStack().getItem());
			if(weight > 0) {
			int len = (int) Math.ceil((double) weight / divisor);
			
			StringBuilder s = new StringBuilder("'");
			for(int i = 0; i < len; i++)
				s.append("  ");
			
			List<ITextComponent> tooltip = event.getToolTip();
			if(tooltip.isEmpty())
				tooltip.add(new StringTextComponent(s.toString()));
			else tooltip.add(1, (new StringTextComponent(s.toString())));
			}
			}
		}
	}
	
	@SubscribeEvent
	public void renderTooltip(RenderTooltipEvent.PostText event) {
		if (!event.getStack().isEmpty() && event.getStack().getItem() instanceof ArmorItem && ConfigHandler.tooltips) {

			Minecraft mc = Minecraft.getInstance();
			mc.getTextureManager().bindTexture(ICONS_RESOURCE);
			int weight = getWeight(event.getStack().getItem());
			if (weight < 28) {

				int count = (int) Math.ceil((double) weight / divisor);
				int y = shiftTextByLines(event.getLines(), event.getY() + 10);
				for (int i = 0; i < count; i++) {
					int x = event.getX() + i * 9; //- 2;
					
					int v = 9;
					int u = 25;
					
					if (weight % 2 != 0 && i == 0)
						u += 9;

					Screen.blit(event.getMatrixStack(), x, y, u, v, 9, 9, 256, 256);
				}
			}
		}
	}

	   public static int shiftTextByLines(List<? extends ITextProperties> lines, int y) {
	        for(int i = 1; i < lines.size(); i++) {
	            String s = lines.get(i).getString();
	            s = TextFormatting.getTextWithoutFormattingCodes(s);
	            if(s != null && !s.isEmpty()) {
	                y += 10 * (i - 1) + 1;
	                break;
	            }
	        }
	        return y;
	    }
}
