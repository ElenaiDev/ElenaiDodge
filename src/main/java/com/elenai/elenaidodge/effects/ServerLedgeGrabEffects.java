package com.elenai.elenaidodge.effects;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.ILedgeGrabCooldown;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.LedgeGrabCooldownProvider;
import com.elenai.elenaidodge.capability.ledgegrabs.ILedgeGrabs;
import com.elenai.elenaidodge.capability.ledgegrabs.LedgeGrabsProvider;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

public class ServerLedgeGrabEffects {

	/**
	 * Runs the Server Wall Jump Effects
	 * 
	 * @side Server
	 */
	public static void run(EntityPlayerMP player) {

		ILedgeGrabs l = player.getCapability(LedgeGrabsProvider.LEDGEGRABS_CAP, null);
		l.increase(1);
		
		ILedgeGrabCooldown lgc = player.getCapability(LedgeGrabCooldownProvider.LEDGEGRABCOOLDOWN_CAP, null);
		lgc.set(ModConfig.common.ledgeGrab.cooldown);
		
		player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
				SoundCategory.PLAYERS, 0.7f, 5f);
		
		if (!player.isCreative() && !player.isSpectator()) {
			player.getFoodStats().addExhaustion((float) ModConfig.common.ledgeGrab.exhaustion);
		}
	}
}
