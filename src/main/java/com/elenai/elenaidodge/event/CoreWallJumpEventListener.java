package com.elenai.elenaidodge.event;

import com.elenai.elenaidodge.api.WallJumpEvent.RequestWallJumpEvent;
import com.elenai.elenaidodge.network.PacketHandler;
import com.elenai.elenaidodge.network.message.SWallJumpMessage;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CoreWallJumpEventListener {

	@SubscribeEvent
	public void onClientWallJump(RequestWallJumpEvent event) {

		if (!Minecraft.getMinecraft().player.capabilities.isFlying && !Minecraft.getMinecraft().player.isInLava()
				&& !Minecraft.getMinecraft().player.isInWater()) {
			PacketHandler.instance.sendToServer(new SWallJumpMessage());
		}

	}
}
