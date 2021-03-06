package com.elenai.elenaidodge.event;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.gui.DodgeStep;
import com.elenai.elenaidodge.util.ClientStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickEventListener {

	public static int regen = ClientStorage.regenSpeed;

	public static int pauseLen = 7;
	public static int animationLen = 4;
	public static int alphaLen = 60;

	public static int animation = animationLen;
	public static int pause = pauseLen;
	public static int flashes = 2;

	public static int failedAnimation = animationLen;
	public static int failedPause = pauseLen;
	public static int failedFlashes = 2;

	public static int alpha = alphaLen;
	
	public static boolean stepVisible = false;
	public static int toastCountdown = 0;

	@SubscribeEvent
	public void onPlayerClientTick(TickEvent.ClientTickEvent event) {

		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player != null) {
			// CLIENT
			if (event.phase == TickEvent.Phase.END && player.world.isRemote
					&& !Minecraft.getMinecraft().isGamePaused()) {
				
				// Tutorial
				if (!ClientStorage.shownTutorial && ModConfig.client.hud.tutorial) {
					DodgeStep.show();
					ClientStorage.shownTutorial = true;
					stepVisible = true;

				}

				if (ClientStorage.shownTutorial == true && stepVisible == true) {
					toastCountdown++;
				}
				
				if(toastCountdown == 300) {
					ClientStorage.tutorialDodges = 1;
				}
				
				if (ClientStorage.tutorialDodges == 1) {
					DodgeStep.hide();
					stepVisible = false;
				}
				
				// COOLDOWNS
				if (ClientStorage.cooldown > 0) {
					ClientStorage.cooldown--;
				}

				// ANIMATION
				if (flashes < 2 && pause <= 0) {
					pause = pauseLen;
					animation = animationLen;
					ClientStorage.healing = true;
					flashes++;
				}

				if (pause > 0) {
					pause--;
				}

				if (animation > 0) {
					animation--;
				}

				if (ClientStorage.healing && animation == 0) {
					ClientStorage.healing = false;
				}

				// FAILED ANIMATION
				if (failedFlashes < 2 && failedPause <= 0) {
					failedPause = pauseLen;
					failedAnimation = animationLen;
					ClientStorage.failed = true;
					failedFlashes++;
				}

				if (failedPause > 0) {
					failedPause--;
				}

				if (failedAnimation > 0) {
					failedAnimation--;
				}

				if (ClientStorage.failed && failedAnimation == 0) {
					ClientStorage.failed = false;
				}

				if (ClientStorage.dodgesDouble > 0) {
					ClientStorage.dodgesDouble -= (27.0 / ClientStorage.regenSpeed);
					ClientStorage.dodges = (int) Math.ceil(ClientStorage.dodgesDouble);
				}
			}
		}
	}

}
