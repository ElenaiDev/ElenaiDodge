package com.elenai.elenaidodge.effects;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.capability.invincibility.IInvincibility;
import com.elenai.elenaidodge.capability.invincibility.InvincibilityProvider;
import com.elenai.elenaidodge.capability.particles.IParticles;
import com.elenai.elenaidodge.capability.particles.ParticlesProvider;
import com.elenai.elenaidodge.network.PacketHandler;
import com.elenai.elenaidodge.network.message.CParticleMessage;
import com.elenai.elenaidodge.util.PatronRewardHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

public class ServerDodgeEffects {

	/**
	 * Runs the Server Dodge Effects
	 * 
	 * @side Server
	 */
	public static void run(EntityPlayerMP player) {

		player.getEntityWorld().playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
				SoundCategory.PLAYERS, 0.8f, 4f);

		IInvincibility i = player.getCapability(InvincibilityProvider.INVINCIBILITY_CAP, null);
		i.set(ModConfig.common.balance.invincibilityTicks);
		
		IParticles p = player.getCapability(ParticlesProvider.PARTICLES_CAP, null);
		p.set(10);
		
		if (!player.isCreative() && !player.isSpectator()) {
			player.getFoodStats().addExhaustion((float) ModConfig.common.balance.exhaustion);
		}
		
		if (ModConfig.common.misc.particles && PatronRewardHandler.getTier(player) <= 0) {
		PacketHandler.instance.sendTo(new CParticleMessage(PatronRewardHandler.getTier(player),
				player.posX, player.posY, player.posZ), (EntityPlayerMP) player);
		PacketHandler.instance.sendToAllTracking(new CParticleMessage(PatronRewardHandler.getTier(player),
				player.posX, player.posY, player.posZ), (EntityPlayerMP) player);
		}
	}
}
