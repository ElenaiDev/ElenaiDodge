package com.elenai.elenaidodge.effects;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.capability.walljumpcooldown.IWallJumpCooldown;
import com.elenai.elenaidodge.capability.walljumpcooldown.WallJumpCooldownProvider;
import com.elenai.elenaidodge.capability.walljumps.IWallJumps;
import com.elenai.elenaidodge.capability.walljumps.WallJumpsProvider;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

public class ServerWallJumpEffects {

	/**
	 * Runs the Server Wall Jump Effects
	 * 
	 * @side Server
	 */
	public static void run(EntityPlayerMP player) {

		//TODO: Check WallJump starting point, if 0 check the players height, then everytime they walljump higher than this / height of previous wall jump set fall damage to 0
		player.fallDistance = 0;
		
		IWallJumps w = player.getCapability(WallJumpsProvider.WALLJUMPS_CAP, null);
		w.increase(1);
		
		IWallJumpCooldown wjc = player.getCapability(WallJumpCooldownProvider.WALLJUMPCOOLDOWN_CAP, null);
		wjc.set(ModConfig.common.wallJump.cooldown);
		
		player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
				SoundCategory.PLAYERS, 0.7f, 5f);
		
		if (!player.isCreative() && !player.isSpectator()) {
			player.getFoodStats().addExhaustion((float) ModConfig.common.wallJump.exhaustion);
		}
	}
}
