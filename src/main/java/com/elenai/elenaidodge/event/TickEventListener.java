package com.elenai.elenaidodge.event;

import com.elenai.elenaidodge.capability.invincibility.IInvincibility;
import com.elenai.elenaidodge.capability.invincibility.InvincibilityProvider;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.ILedgeGrabCooldown;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.LedgeGrabCooldownProvider;
import com.elenai.elenaidodge.capability.ledgegrabs.ILedgeGrabs;
import com.elenai.elenaidodge.capability.ledgegrabs.LedgeGrabsProvider;
import com.elenai.elenaidodge.capability.particles.IParticles;
import com.elenai.elenaidodge.capability.particles.ParticlesProvider;
import com.elenai.elenaidodge.capability.walljumpcooldown.IWallJumpCooldown;
import com.elenai.elenaidodge.capability.walljumpcooldown.WallJumpCooldownProvider;
import com.elenai.elenaidodge.capability.walljumps.IWallJumps;
import com.elenai.elenaidodge.capability.walljumps.WallJumpsProvider;
import com.elenai.elenaidodge.network.PacketHandler;
import com.elenai.elenaidodge.network.message.CParticleMessage;
import com.elenai.elenaidodge.util.PatronRewardHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickEventListener {

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {

		// SERVER
		if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
			IInvincibility i = event.player.getCapability(InvincibilityProvider.INVINCIBILITY_CAP, null);
			if (i.getInvincibility() > 0) {
				i.set(i.getInvincibility() - 1);
			}

			IWallJumps w = event.player.getCapability(WallJumpsProvider.WALLJUMPS_CAP, null);
			if (event.player.onGround && w.getWallJumps() > 0) {
				w.set(0);
			}

			ILedgeGrabCooldown lgc = event.player.getCapability(LedgeGrabCooldownProvider.LEDGEGRABCOOLDOWN_CAP, null);
			if (lgc.getLedgeGrabs() > 0) {
				lgc.decrease(1);
			}

			IWallJumpCooldown wjc = event.player.getCapability(WallJumpCooldownProvider.WALLJUMPCOOLDOWN_CAP, null);
			if (wjc.getWallJumps() > 0) {
				wjc.decrease(1);
			}

			ILedgeGrabs l = event.player.getCapability(LedgeGrabsProvider.LEDGEGRABS_CAP, null);
			if (event.player.onGround && l.getLedgeGrabs() > 0) {
				l.set(0);
			}

			IParticles p = event.player.getCapability(ParticlesProvider.PARTICLES_CAP, null);
			if (p.getParticles() > 0) {
				p.set(p.getParticles() - 1);
				if (PatronRewardHandler.getTier(event.player) > 0) {
					PacketHandler.instance.sendTo(new CParticleMessage(PatronRewardHandler.getTier(event.player),
							event.player.posX, event.player.posY, event.player.posZ), (EntityPlayerMP) event.player);
					PacketHandler.instance.sendToAllTracking(
							new CParticleMessage(PatronRewardHandler.getTier(event.player), event.player.posX,
									event.player.posY, event.player.posZ),
							(EntityPlayerMP) event.player);
				}
			}
		}
	}
}