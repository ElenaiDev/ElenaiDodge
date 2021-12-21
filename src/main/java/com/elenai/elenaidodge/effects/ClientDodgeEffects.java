package com.elenai.elenaidodge.effects;

import com.elenai.elenaidodge.gui.DodgeStep;
import com.elenai.elenaidodge.util.ClientStorage;

import net.minecraft.client.Minecraft;

public class ClientDodgeEffects {
	
	/**
	 * Runs the Client Dodge Effects
	 * @param dodges
	 * @param dodgeCost
	 * @side Client
	 */
	public static void run() {
		if (!Minecraft.getMinecraft().player.isCreative() && !Minecraft.getMinecraft().player.isSpectator()) {
			ClientStorage.dodgesDouble = 27;
	}
		if(ClientStorage.tutorialDodges < 1) {
		ClientStorage.tutorialDodges+=0.25;
		DodgeStep.moveToast.setProgress((float)ClientStorage.tutorialDodges);
		}
		
	}
	
}
