package com.elenai.elenaidodge.event;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.api.LedgeGrabEvent.ServerLedgeGrabEvent;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.ILedgeGrabCooldown;
import com.elenai.elenaidodge.capability.ledgegrabcooldown.LedgeGrabCooldownProvider;
import com.elenai.elenaidodge.capability.ledgegrabs.ILedgeGrabs;
import com.elenai.elenaidodge.capability.ledgegrabs.LedgeGrabsProvider;
import com.elenai.elenaidodge.init.PotionInit;
import com.elenai.elenaidodge.util.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerLedgeGrabEventListener {

	@SubscribeEvent
	public void onServerLedgeGrab(ServerLedgeGrabEvent event) {

		EntityPlayer player = event.getPlayer();

		// Creates a check to see if the player can ledge grab
		boolean willCancel = true;

		Vec3d lookVec = Vec3d.fromPitchYaw(0f, player.rotationYaw).scale(0.25f);
		AxisAlignedBB playerAABB = player.getEntityBoundingBox();

		AxisAlignedBB wallCheck = new AxisAlignedBB(player.posX + lookVec.x, playerAABB.minY + 0.5,
				player.posZ + lookVec.z, player.posX + lookVec.x, playerAABB.minY + 0.2, player.posZ + lookVec.z);

		AxisAlignedBB headCheck = new AxisAlignedBB(player.posX + lookVec.x, playerAABB.maxY + 0.2, player.posZ + lookVec.z,
				player.posX + lookVec.x, playerAABB.maxY - 0.2, player.posZ + lookVec.z);

		double dist = (player.width / 2) + 0.1 + ModConfig.common.ledgeGrab.distance;
		AxisAlignedBB[] axes = { wallCheck.expand(0, 0, dist), wallCheck.expand(-dist, 0, 0),
				wallCheck.expand(0, 0, -dist), wallCheck.expand(dist, 0, 0) };

		AxisAlignedBB[] headAxes = { headCheck.expand(0, 0, dist), headCheck.expand(-dist, 0, 0),
				headCheck.expand(0, 0, -dist), headCheck.expand(dist, 0, 0) };

		for (AxisAlignedBB axis : axes) {
			if (player.world.collidesWithAnyBlock(axis)) {
				willCancel = false;
			}
		}

		for (AxisAlignedBB axis : headAxes) {
			if (player.world.collidesWithAnyBlock(axis)) {
				willCancel = true;
			}
		}

		if (willCancel) {
			event.setCanceled(true);
		}

		if ((!ModConfig.common.ledgeGrab.enable && !Loader.isModLoaded("reskillable"))
				|| !Utils.ledgeGrabTraitUnlocked(event.getPlayer())) {
			event.setCanceled(!player.isPotionActive(PotionInit.CLIMBER_EFFECT));
		}

		ILedgeGrabCooldown lgc = player.getCapability(LedgeGrabCooldownProvider.LEDGEGRABCOOLDOWN_CAP, null);
		if(lgc.getLedgeGrabs() > 0) {
			event.setCanceled(true);
		}
		
		// Modifiers
		if (player.getFoodStats().getFoodLevel() <= ModConfig.common.ledgeGrab.hunger) {
			event.setCanceled(true);
		}
		
		if(player.isCreative() || player.isSpectator()) {
			event.setCanceled(true);
		}
		
		if(!ModConfig.common.ledgeGrab.falling && player.chasingPosY > player.posY) {
			event.setCanceled(true);
		}
		
		if(player.fallDistance > ModConfig.common.ledgeGrab.fallDistance) {
			event.setCanceled(true);
		}
		
		ILedgeGrabs l = player.getCapability(LedgeGrabsProvider.LEDGEGRABS_CAP, null);
		if (l.getLedgeGrabs() + 1 > ModConfig.common.ledgeGrab.maximum && ModConfig.common.ledgeGrab.maximum != 0) {
			event.setCanceled(true);
		}
		
		if (player.isRiding()) {
			event.setCanceled(true);
		}
	}
}
