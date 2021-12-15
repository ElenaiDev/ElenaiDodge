package com.elenai.elenaidodge.event;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.api.LedgeGrabEvent;
import com.elenai.elenaidodge.api.LedgeGrabEvent.ServerLedgeGrabEvent;
import com.elenai.elenaidodge.api.WallJumpEvent.Direction;
import com.elenai.elenaidodge.api.WallJumpEvent.ServerWallJumpEvent;
import com.elenai.elenaidodge.capability.walljumpcooldown.IWallJumpCooldown;
import com.elenai.elenaidodge.capability.walljumpcooldown.WallJumpCooldownProvider;
import com.elenai.elenaidodge.capability.walljumps.IWallJumps;
import com.elenai.elenaidodge.capability.walljumps.WallJumpsProvider;
import com.elenai.elenaidodge.init.PotionInit;
import com.elenai.elenaidodge.util.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerWallJumpEventListener {

	@SubscribeEvent
	public void onServerWallJump(ServerWallJumpEvent event) {

		EntityPlayer player = event.getPlayer();

		// RAY END POINT - TO WHERE IT WILL TRAVEL TO
		Double rayLength = new Double(0.4 + ModConfig.common.wallJump.distance);
		Vec3d playerRotation = Utils.getVectorForHorizontalRotation(event.getPlayer().rotationYawHead);
		Vec3d inverseRotation = playerRotation.scale(-1);
		Vec3d rayPath = inverseRotation.scale(rayLength);

		// RAY START AND END POINTS
		Vec3d from = player.getPositionVector().addVector(0, 0.2, 0);
		Vec3d to = from.add(rayPath);

		// LEFT
		Vec3d playerRotationLeft = Utils.getVectorForHorizontalRotation(event.getPlayer().rotationYawHead + (float) ModConfig.common.wallJump.advanced.angle);
		Vec3d inverseRotationLeft = playerRotationLeft.scale(-1);
		Vec3d rayPathLeft = inverseRotationLeft.scale(rayLength*ModConfig.common.wallJump.advanced.angleLength);
		Vec3d fromLeft = player.getPositionVector().addVector(0, 0.2, 0);
		Vec3d toLeft = fromLeft.add(rayPathLeft);

		// RIGHT
		Vec3d playerRotationRight = Utils.getVectorForHorizontalRotation(event.getPlayer().rotationYawHead - (float) ModConfig.common.wallJump.advanced.angle);
		Vec3d inverseRotationRight = playerRotationRight.scale(-1);
		Vec3d rayPathRight = inverseRotationRight.scale(rayLength*ModConfig.common.wallJump.advanced.angleLength);
		Vec3d fromRight = player.getPositionVector().addVector(0, 0.2, 0);
		Vec3d toRight = fromLeft.add(rayPathRight);
		
		// CREATE AND CAST THE RAYS
		RayTraceResult ctx = player.world.rayTraceBlocks(from, to);
		RayTraceResult ctxLeft = player.world.rayTraceBlocks(fromLeft, toLeft);
		RayTraceResult ctxRight = player.world.rayTraceBlocks(fromRight, toRight);
		
		// CHECK IF ANY RAY FINDS SOLID BLOCK
		if (ctx != null && player.world.getBlockState(ctx.getBlockPos()).getMaterial().isSolid()) {
			event.setDirection(Direction.valueOf(ctx.sideHit.getName().toUpperCase()));
		} else if (ctxLeft != null && player.world.getBlockState(ctxLeft.getBlockPos()).getMaterial().isSolid()) {
			event.setDirection(Direction.valueOf(ctxLeft.sideHit.getName().toUpperCase()));
		} else if (ctxRight != null && player.world.getBlockState(ctxRight.getBlockPos()).getMaterial().isSolid()) {
			event.setDirection(Direction.valueOf(ctxRight.sideHit.getName().toUpperCase()));
		}

		// IF NOT, ATTEMPT TO LEDGE GRAB
		else {
			LedgeGrabEvent.ServerLedgeGrabEvent lgEvent = new ServerLedgeGrabEvent(
					ModConfig.common.ledgeGrab.forwardsForce, player, 0,
					com.elenai.elenaidodge.api.LedgeGrabEvent.Direction
							.valueOf(player.getHorizontalFacing().getName().toUpperCase()));
			if (!MinecraftForge.EVENT_BUS.post(lgEvent)) {
				Utils.handleLedgeGrab(lgEvent, (EntityPlayerMP) player);
			}
			event.setCanceled(true);
		}

		if ((!ModConfig.common.wallJump.enable && !Loader.isModLoaded("reskillable"))
				|| !Utils.wallJumpTraitUnlocked(event.getPlayer())) {
			event.setCanceled(!player.isPotionActive(PotionInit.NIMBLE_EFFECT));
		}

		// Modifiers
		if (event.getDirection() != null) {

			switch (event.getDirection()) {
			case NORTH:
				if (playerRotation.z > 0 - ModConfig.common.wallJump.angle) {
					event.setCanceled(true);
				}
				break;
			case EAST:
				if (playerRotation.x < 0 + ModConfig.common.wallJump.angle) {
					event.setCanceled(true);
				}
				break;
			case SOUTH:
				if (playerRotation.z < 0 + ModConfig.common.wallJump.angle) {
					event.setCanceled(true);
				}
				break;
			case WEST:
				if (playerRotation.x > 0 - ModConfig.common.wallJump.angle) {
					event.setCanceled(true);
				}
				break;
			default:
				break;
			}

			IWallJumps w = player.getCapability(WallJumpsProvider.WALLJUMPS_CAP, null);
			if (w.getWallJumps() + 1 > ModConfig.common.wallJump.maximum && ModConfig.common.wallJump.maximum != 0) {
				event.setCanceled(true);
			}

			if (player.getFoodStats().getFoodLevel() <= ModConfig.common.wallJump.hunger) {
				event.setCanceled(true);
			}

			if (!ModConfig.common.wallJump.falling && player.chasingPosY > player.posY) {
				event.setCanceled(true);
			}

			if (player.isCreative() || player.isSpectator()) {
				event.setCanceled(true);
			}

			if (player.fallDistance > ModConfig.common.wallJump.fallDistance
					&& ModConfig.common.wallJump.fallDistance != 0) {
				event.setCanceled(true);
			}

			if (player.isRiding()) {
				event.setCanceled(true);
			}

			IWallJumpCooldown wjc = player.getCapability(WallJumpCooldownProvider.WALLJUMPCOOLDOWN_CAP, null);
			if (wjc.getWallJumps() > 0) {
				event.setCanceled(true);
			}

			if (!ModConfig.common.wallJump.oneBlock && event.getPlayer().world
					.getBlockState(event.getPlayer().getPosition()
							.offset(EnumFacing.byName(event.getDirection().name().toUpperCase())))
					.getMaterial().blocksMovement()) {
				event.setCanceled(true);
			}
		} else {
			event.setCanceled(true);
		}
	}
}
