package com.elenai.elenaidodge.network.message;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.event.ArmorTickEventListener;
import com.elenai.elenaidodge.util.ClientStorage;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CUpdateConfigMessage implements IMessage {

	/*
	 * A Message to transfer server side values from the Config.
	 */

	private int regenRate;
	private String weights;
	private boolean weightsEnabled;

	private boolean messageValid;

	public CUpdateConfigMessage() {
		this.messageValid = false;
	}

	public CUpdateConfigMessage(int regenRate, String weights, boolean weightsEnabled) {
		this.regenRate = regenRate;
		this.weights = weights;
this.weightsEnabled = weightsEnabled;

		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.regenRate = buf.readInt();
			this.weights = ByteBufUtils.readUTF8String(buf);
			this.weightsEnabled = buf.readBoolean();


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
		ByteBufUtils.writeUTF8String(buf, weights);
		buf.writeBoolean(weightsEnabled);
	}

	public static class Handler implements IMessageHandler<CUpdateConfigMessage, IMessage> {

		@Override
		public IMessage onMessage(CUpdateConfigMessage message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT) {
				return null;
			}
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		void processMessage(CUpdateConfigMessage message, MessageContext ctx) {
			ClientStorage.regenSpeed = message.regenRate;
			ClientStorage.weightsEnabled = message.weightsEnabled;
			ClientStorage.weightValues = message.weights;
			
			// Forces Armor Refresh
			ArmorTickEventListener.previousArmor.clear();

		}
	}
}
