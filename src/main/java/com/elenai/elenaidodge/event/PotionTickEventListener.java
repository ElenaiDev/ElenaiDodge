package com.elenai.elenaidodge.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PotionTickEventListener {

	@SubscribeEvent
	public void onPotionTickEvent(TickEvent.PlayerTickEvent event) {

		if (event.phase == TickEvent.Phase.END) {

		}
	}
}
