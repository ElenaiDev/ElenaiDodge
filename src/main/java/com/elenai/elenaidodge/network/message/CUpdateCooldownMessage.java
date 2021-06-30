package com.elenai.elenaidodge.network.message;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.event.ArmorTickEventListener;
import com.elenai.elenaidodge.util.ClientStorage;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CUpdateCooldownMessage implements IMessage {

	/*
	 * A Message to transfer server side values from the Config.
	 */

	private int regenRate;

	private boolean messageValid;

	public CUpdateCooldownMessage() {
		this.messageValid = false;
	}

	public CUpdateCooldownMessage(int regenRate) {
		this.regenRate = regenRate;


		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.regenRate = buf.readInt();


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
		buf.writeInt(regenRate);

	}

	public static class Handler implements IMessageHandler<CUpdateCooldownMessage, IMessage> {

		@Override
		public IMessage onMessage(CUpdateCooldownMessage message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT) {
				return null;
			}
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		void processMessage(CUpdateCooldownMessage message, MessageContext ctx) {
			ClientStorage.regenSpeed = message.regenRate;

			// Forces Armor Refresh
			ArmorTickEventListener.previousArmor.clear();

		}
	}
}
