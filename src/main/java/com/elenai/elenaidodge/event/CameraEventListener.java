package com.elenai.elenaidodge.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CameraEventListener {

	/*
	 * Shhh... ;)
	 */
	
	@SubscribeEvent
	public void camera(EntityViewRenderEvent.CameraSetup event) {
		if (event.getEntity() instanceof EntityPlayer) {
			
//			switch (ClientStorage.wallRunning) {
//			case 0:
//				event.setRoll(0);
//				break;
//			case 1:
//				event.setRoll(10);
//
//				break;
//			case 2:
//				event.setRoll(-10);
//				break;
//			default:
//				break;
//			}
		}
	}

}
