package com.elenai.elenaidodge.api;

import javax.annotation.Nullable;

import com.elenai.elenaidodge.util.ClientStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class WallJumpEvent extends Event {
	public enum Direction {
		NORTH, EAST, SOUTH, WEST;
	}

	protected final Side side;
	protected final EntityPlayer player;
	protected double force;
	protected int cooldown;
	@Nullable
	protected Direction direction;

	/**
	 * Do not use this by itself. Please use RequestWallJumpEvent for the Client, and
	 * ServerWallJumpEvent for the Server. RequestWallJumpEvent is called when the player
	 * wants to WallJump. You can push this to the Forge event bus to make the player
	 * wallJump yourself. ServerWallJumpEvent is called as the player wallJumps. You can
	 * cancel the event to prevent the player from Wall Jumping.
	 * 
	 * @param side
	 * @param direction
	 * @param force
	 * @param player
	 * @author Elenai
	 */
	public WallJumpEvent(Side side, double force, EntityPlayer player, int cooldown, Direction direction) {
		this.side = side;
		this.force = force;
		this.player = player;
		this.cooldown = cooldown;
		this.direction = direction;
	}

	/**
	 * RequestWallJumpEvent is called when the player wants to WallJump. You can push this
	 * to the Forge event bus to make the player wallJump yourself.
	 * 
	 * @author Elenai
	 */
	public static class RequestWallJumpEvent extends WallJumpEvent {
		public RequestWallJumpEvent() {
			super(Side.SERVER, 0.0, Minecraft.getMinecraft().player, ClientStorage.cooldown, null);
		}
	}

	/**
	 * ServerWallJumpEvent is called as the player wallJumps. You can cancel the event to
	 * prevent the player from Wall Jumping.
	 * 
	 * @author Elenai
	 */
	@Cancelable
	public static class ServerWallJumpEvent extends WallJumpEvent {
		public ServerWallJumpEvent(double force, EntityPlayer player, int cooldown) {
			super(Side.CLIENT,  force, player, cooldown, null);
		}
		
		/**
		 * @return WallJump Force
		 */
		public double getForce() {
			return force;
		}

		/**
		 * Sets the wallJump force. it is ALWAYS recommended to do setForce(event.getForce() + X) in order to ensure compatibility.
		 * @param force
		 */
		public void setForce(double force) {
			this.force = force;
		}

		/**
		 * @return Player Wall Jumping
		 */
		public EntityPlayer getPlayer() {
			return player;
		}
		
		public int getCooldown() {
			return cooldown;
		}


		public void setCooldown(int cooldown) {
			this.cooldown = cooldown;
		}
		
		/**
		 * @return The Face of the block the player has just collided with.
		 */
		public Direction getDirection() {
			return direction;
		}

		public void setDirection(Direction direction) {
			this.direction = direction;
		}
	}

	/**
	 * @return Client or Server
	 */
	public Side getSide() {
		return side;
	}
}
