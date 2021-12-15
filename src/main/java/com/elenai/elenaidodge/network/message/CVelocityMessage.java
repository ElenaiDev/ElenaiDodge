package com.elenai.elenaidodge.network.message;

import com.elenai.elenaidodge.ElenaiDodge;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CVelocityMessage implements IMessage {
	
	/*
	 * A Message to allow player.setVelocity to be run from the server
	 */

	private double motionX, motionY, motionZ;

	private boolean messageValid, addVelocity;

	public CVelocityMessage() {
		this.messageValid = false;
	}

	public CVelocityMessage(double motionX, double motionY, double motionZ, boolean addVelocity) {
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.addVelocity = addVelocity;

		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.motionX = buf.readDouble();
			this.motionY = buf.readDouble();
			this.motionZ = buf.readDouble();
			this.addVelocity = buf.readBoolean();

		} catch (IndexOutOfBoundsException ioe) {
			ElenaiDodge.LOG.error("Error occured whilst networking!", ioe);
			return;
		}
		this.messageValid = true;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid) {
			return;
		}
		buf.writeDouble(motionX);
		buf.writeDouble(motionY);
		buf.writeDouble(motionZ);
		buf.writeBoolean(addVelocity);
	}

	public static class Handler implements IMessageHandler<CVelocityMessage, IMessage> {

		@Override
		public IMessage onMessage(CVelocityMessage message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT) {
				return null;
			}
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		void processMessage(CVelocityMessage message, MessageContext ctx) {
			if(message.addVelocity) {
				Minecraft.getMinecraft().player.addVelocity(message.motionX, message.motionY, message.motionZ);
			} else {
				Minecraft.getMinecraft().player.setVelocity(message.motionX, message.motionY, message.motionZ);

			}
		}
	}
}
