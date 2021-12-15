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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerLedgeGrabEventListener {

	@SubscribeEvent
	public void onServerLedgeGrab(ServerLedgeGrabEvent event) {

		EntityPlayer player = event.getPlayer();

		// CHECK FOR SOLID BLOCK AT PLAYER'S KNEES
		// RAY END POINT - TO WHERE IT WILL TRAVEL TO
		Double rayLength = new Double(0.4 + ModConfig.common.wallJump.distance);
		Vec3d playerRotation = Utils.getVectorForHorizontalRotation(event.getPlayer().rotationYawHead);
		Vec3d PlayerRotationHorizontal = new Vec3d(playerRotation.x, 0, playerRotation.z);
		Vec3d rayPath = PlayerRotationHorizontal.scale(rayLength);

		// RAY START AND END POINTS
		Vec3d from = player.getPositionVector().addVector(0, 0.2, 0);
		Vec3d to = from.add(rayPath);

		// CREATE AND CAST THE RAY
		RayTraceResult ctx = player.world.rayTraceBlocks(from, to);

		// CHECK IF RAY FINDS SOLID BLOCK
		if(ctx == null || !player.world.getBlockState(ctx.getBlockPos()).getMaterial().isSolid()) {
			event.setCanceled(true);
		} 
		
		// CHECK FOR NON-SOLID BLOCK AT PLAYER'S HEAD
		if(ctx != null && player.world.getBlockState(ctx.getBlockPos().add(0, 1, 0)).getMaterial().isSolid()) {
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
		
		switch (event.getDirection()) {
		case NORTH:
			if (playerRotation.z > 0 - ModConfig.common.ledgeGrab.angle) {
				event.setCanceled(true);
			}
			break;
		case EAST:
			if (playerRotation.x < 0 + ModConfig.common.ledgeGrab.angle) {
				event.setCanceled(true);
			}
			break;
		case SOUTH:
			if (playerRotation.z < 0 + ModConfig.common.ledgeGrab.angle) {
				event.setCanceled(true);
			}
			break;
		case WEST:
			if (playerRotation.x > 0 - ModConfig.common.ledgeGrab.angle) {
				event.setCanceled(true);
			}
			break;
		default:
			break;
		}
		
		if(player.isCreative() || player.isSpectator()) {
			event.setCanceled(true);
		}
		
		if(!ModConfig.common.ledgeGrab.falling && player.chasingPosY > player.posY) {
			event.setCanceled(true);
		}
		
		if(player.fallDistance > ModConfig.common.ledgeGrab.fallDistance && ModConfig.common.ledgeGrab.fallDistance != 0) {
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
