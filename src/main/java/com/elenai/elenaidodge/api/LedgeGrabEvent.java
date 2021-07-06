package com.elenai.elenaidodge.api;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;

public class LedgeGrabEvent extends Event {
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
	 * Do not use this by itself. Please use ServerLedgeGrabEvent for the Server.
	 * RequestLedgeGrabEvent is called when the player wants to LedgeGrab. You can
	 * push this to the Forge event bus to make the player ledgeGrab yourself.
	 * ServerLedgeGrabEvent is called as the player ledgeGrabs. You can cancel the
	 * event to prevent the player from Wall Jumping.
	 * 
	 * @param side
	 * @param direction
	 * @param force
	 * @param player
	 * @author Elenai
	 */
	public LedgeGrabEvent(Side side, double force, EntityPlayer player, int cooldown, Direction direction) {
		this.side = side;
		this.force = force;
		this.player = player;
		this.cooldown = cooldown;
		this.direction = direction;
	}

	/**
	 * ServerLedgeGrabEvent is called as the player ledge grabs. You can cancel the
	 * event to prevent the player from Ledge Grabbing.
	 * 
	 * @author Elenai
	 */
	@Cancelable
	public static class ServerLedgeGrabEvent extends LedgeGrabEvent {
		public ServerLedgeGrabEvent(double force, EntityPlayer player, int cooldown, Direction direction) {
			super(Side.CLIENT, force, player, cooldown, direction);
		}

		/**
		 * @return LedgeGrab Force
		 */
		public double getForce() {
			return force;
		}

		/**
		 * Sets the ledgeGrab force. it is ALWAYS recommended to do
		 * setForce(event.getForce() + X) in order to ensure compatibility.
		 * 
		 * @param force
		 */
		public void setForce(double force) {
			this.force = force;
		}

		/**
		 * @return Player Ledge Grabbing
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
