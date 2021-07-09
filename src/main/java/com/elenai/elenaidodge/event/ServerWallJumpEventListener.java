package com.elenai.elenaidodge.event;

import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.api.LedgeGrabEvent;
import com.elenai.elenaidodge.api.LedgeGrabEvent.ServerLedgeGrabEvent;
import com.elenai.elenaidodge.api.WallJumpEvent.Direction;
import com.elenai.elenaidodge.api.WallJumpEvent.ServerWallJumpEvent;
import com.elenai.elenaidodge.capability.walljumps.IWallJumps;
import com.elenai.elenaidodge.capability.walljumps.WallJumpsProvider;
import com.elenai.elenaidodge.util.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ServerWallJumpEventListener {

	@SubscribeEvent
	public void onServerWallJump(ServerWallJumpEvent event) {

		EntityPlayer player = event.getPlayer();

		// Creates a check to see if the player can wall jump
		boolean willCancel = true;

		Vec3d lookVecBehind = Vec3d.fromPitchYaw(0f, player.rotationYaw).scale(-0.25f);
		AxisAlignedBB playerAABB = player.getEntityBoundingBox();

		AxisAlignedBB wallCheck = new AxisAlignedBB(player.posX + lookVecBehind.x, playerAABB.minY + 0.5,
				player.posZ + lookVecBehind.z, player.posX + lookVecBehind.x, playerAABB.minY + 0.2,
				player.posZ + lookVecBehind.z);

		double dist = (player.width / 2) + 0.1 + ModConfig.common.wallJump.distance;
		AxisAlignedBB[] axes = { wallCheck.expand(0, 0, dist), wallCheck.expand(-dist, 0, 0),
				wallCheck.expand(0, 0, -dist), wallCheck.expand(dist, 0, 0) };

		int i = 0;
		for (AxisAlignedBB axis : axes) {
			if (player.world.collidesWithAnyBlock(axis)) { // checkBlockCollision(axis)
				willCancel = false;
				switch (i) {
				case 0:
					event.setDirection(Direction.NORTH);
					break;
				case 1:
					event.setDirection(Direction.EAST);
					break;
				case 2:
					event.setDirection(Direction.SOUTH);
					break;
				case 3:
					event.setDirection(Direction.WEST);
					break;
				default:
					break;
				}
			}
			i += 1;
		}
		if (willCancel) {
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
			event.setCanceled(true);
		}

		// Modifiers
		if (event.getDirection() != null) {

			if (event.getPlayer().rotationYawHead > 0) {
				switch (event.getDirection()) {
				case NORTH:
					if (event.getPlayer().rotationYawHead < 120 - ModConfig.common.wallJump.forgiving
							|| event.getPlayer().rotationYawHead > 240 + ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				case EAST:
					if (event.getPlayer().rotationYawHead < 210 - ModConfig.common.wallJump.forgiving
							|| event.getPlayer().rotationYawHead > 330 + ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				case SOUTH:
					if (event.getPlayer().rotationYawHead > 60 + ModConfig.common.wallJump.forgiving
							&& event.getPlayer().rotationYawHead < 300 - ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				case WEST:
					if (event.getPlayer().rotationYawHead < 30 - ModConfig.common.wallJump.forgiving
							|| event.getPlayer().rotationYawHead > 150 + ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				default:
					break;
				}
			} else {
				switch (event.getDirection()) {
				case NORTH:
					if (event.getPlayer().rotationYawHead > -120 + ModConfig.common.wallJump.forgiving
							|| event.getPlayer().rotationYawHead < -240 - ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				case EAST:
					if (event.getPlayer().rotationYawHead > -30 + ModConfig.common.wallJump.forgiving
							|| event.getPlayer().rotationYawHead < -150 - ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				case SOUTH:
					if (event.getPlayer().rotationYawHead < -60 - ModConfig.common.wallJump.forgiving
							&& event.getPlayer().rotationYawHead > -300 + ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				case WEST:
					if (event.getPlayer().rotationYawHead > -210 + ModConfig.common.wallJump.forgiving
							|| event.getPlayer().rotationYawHead < -330 - ModConfig.common.wallJump.forgiving) {
						event.setCanceled(true);
					}
					break;
				default:
					break;
				}
			}

			IWallJumps w = player.getCapability(WallJumpsProvider.WALLJUMPS_CAP, null);
			if (w.getWallJumps() + 1 > ModConfig.common.wallJump.maximum && ModConfig.common.wallJump.maximum != 0) {
				event.setCanceled(true);
			}

			if (player.getFoodStats().getFoodLevel() <= ModConfig.common.wallJump.hunger) {
				event.setCanceled(true);
			}
			
			if(!ModConfig.common.wallJump.falling && player.chasingPosY > player.posY) {
				event.setCanceled(true);
			}
			
			if (player.isRiding()) {
				event.setCanceled(true);
			}

			if (!ModConfig.common.wallJump.oneBlock && event.getPlayer().world.getBlockState(event.getPlayer()
			.getPosition().offset(EnumFacing.byName(event.getDirection().name().toUpperCase()))).getMaterial().blocksMovement()) { 
				event.setCanceled(true);
			}
		} else {
			event.setCanceled(true);
		}
	}
}
