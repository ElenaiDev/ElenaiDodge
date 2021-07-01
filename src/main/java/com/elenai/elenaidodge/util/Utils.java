package com.elenai.elenaidodge.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.elenai.elenaidodge.ElenaiDodge;
import com.elenai.elenaidodge.ModConfig;
import com.elenai.elenaidodge.api.DodgeEvent;
import com.elenai.elenaidodge.api.DodgeEvent.Direction;
import com.elenai.elenaidodge.capability.weight.IWeight;
import com.elenai.elenaidodge.capability.weight.WeightProvider;
import com.elenai.elenaidodge.effects.ServerDodgeEffects;
import com.elenai.elenaidodge.network.PacketHandler;
import com.elenai.elenaidodge.network.message.CDodgeEffectsMessage;
import com.elenai.elenaidodge.network.message.CInitPlayerMessage;
import com.elenai.elenaidodge.network.message.CUpdateConfigMessage;
import com.elenai.elenaidodge.network.message.CVelocityMessage;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;

public class Utils {

	/**
	 * Allows player.setVelocity to be run from the server.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @author Elenai
	 * @side Server
	 */
	public static void setPlayerVelocity(double x, double y, double z, EntityPlayer player) {
		PacketHandler.instance.sendTo(new CVelocityMessage(x, y, z), (EntityPlayerMP) player);
	}

	/**
	 * Dodges the player in the given direction.
	 * 
	 * @param direction
	 * @param player
	 * @author Elenai
	 * @side Server
	 */
	public static void handleDodge(Direction direction, DodgeEvent.ServerDodgeEvent event, EntityPlayerMP player) {

		double f = event.getForce();
		double motionX;
		double motionZ;

		switch (direction) {
		case LEFT:
			motionX = (double) (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			motionZ = (double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			break;
		case RIGHT:
			motionX = (double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			motionZ = (double) (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			break;
		case FORWARD:
			motionX = (double) (-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			motionZ = (double) (MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			break;
		case BACK:
			motionX = (double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			motionZ = (double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
					* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
			break;
		default:
			motionX = 0;
			motionZ = 0;
			ElenaiDodge.LOG.error("DodgeEvent Posted and Received but no direction given!");
		}
		setPlayerVelocity(motionX, ModConfig.common.balance.verticality, motionZ, player);
		ServerDodgeEffects.run(player);
		PacketHandler.instance.sendTo(new CDodgeEffectsMessage(), (EntityPlayerMP) player);
	}

	/**
	 * Returns the Player's Dodge Force when all default calculations have been
	 * applied.
	 * 
	 * @param player
	 * @return The Player's total Dodge Force
	 * @author Elenai
	 * @side Server
	 */
	public static double calculateForce(EntityPlayer player) {
		return ModConfig.common.balance.force;
	}

	/**
	 * A method to be run when the player first joins the world.
	 * 
	 * @author Elenai
	 * @param player
	 */
	public static void initPlayer(EntityPlayer player) {
		PacketHandler.instance.sendTo(new CInitPlayerMessage(), (EntityPlayerMP) player);
	}

	/**
	 * Updates the Client Config for the given player.
	 * 
	 * @author Elenai
	 * @param player
	 */
	public static void updateClientConfig(EntityPlayerMP player) {
		PacketHandler.instance.sendTo(new CUpdateConfigMessage(ModConfig.common.weights.enable ? Utils.getWeightTier(player).getCooldown() : ModConfig.common.balance.regenSpeed,
				arrayToString(ModConfig.common.weights.weights), ModConfig.common.weights.enable), player);
	}

	/**
	 * Updates the Client Config for all players.
	 * 
	 * @author Elenai
	 */
	public static void updateClientConfig() {
		PacketHandler.instance.sendToAll(new CUpdateConfigMessage(ModConfig.common.balance.regenSpeed,
				arrayToString(ModConfig.common.weights.weights), ModConfig.common.weights.enable));
	}

	/**
	 * Returns the cumulative total of an equipped enchantment type.
	 * 
	 * @author Diesieben07
	 * @param enchantment
	 * @param entity
	 * @return
	 */
	public static int getTotalEnchantmentLevel(Enchantment enchantment, EntityLivingBase entity) {
		Iterable<ItemStack> iterable = enchantment.getEntityEquipment(entity);

		if (iterable == null) {
			return 0;
		} else {
			int i = 0;

			for (ItemStack itemstack : iterable) {
				int j = EnchantmentHelper.getEnchantmentLevel(enchantment, itemstack);
				i += j;
			}

			return i;
		}
	}

	/**
	 * Converts a String Array into a CSV String
	 * 
	 * @author Nico Huysamen
	 * @author Adapted by Elenai
	 * @param string
	 * @return
	 */
	public static String arrayToString(String[] string) {
		if (string.length > 0) {
			StringBuilder stringBuilder = new StringBuilder();

			for (String n : string) {
				stringBuilder.append("").append(n.replace("'", "\\'")).append(",");
			}

			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			return stringBuilder.toString();
		} else {
			return "";
		}
	}

	/**
	 * Checks if the player has the Dodge trait added in Reskillable implementation.
	 * If Reskillable is not installed, this will simply return true.
	 * 
	 * @return Dodge Trait Unlocked
	 * @author Elenai
	 */
	public static boolean dodgeTraitUnlocked(EntityPlayer player) {
		if (Loader.isModLoaded("reskillable")) {
			return (codersafterdark.reskillable.api.data.PlayerDataHandler.get(player)
					.getSkillInfo(codersafterdark.reskillable.api.ReskillableRegistries.SKILLS
							.getValue(new ResourceLocation(codersafterdark.reskillable.lib.LibMisc.MOD_ID, "agility")))
					.isUnlocked(codersafterdark.reskillable.api.ReskillableRegistries.UNLOCKABLES
							.getValue(new ResourceLocation(ElenaiDodge.MODID, "dodge"))));
		}
		return true;
	}

	/**
	 * Returns the players entry weight, dodge force and dodge cooldown. If it
	 * cannot find a minimum tier, it returns the Config Default Values.
	 * 
	 * @param player
	 * @return WeightTier object of the player
	 */
	public static WeightTier getWeightTier(EntityPlayer player) {
		List<String> tiers = new ArrayList<String>();
		Collections.addAll(tiers, ModConfig.common.weights.tiers);
		Collections.reverse(tiers);
		for (String tier : tiers) {
			String[] tierValue = tier.split("[:\\|]"); // tierValue[0] is entry weight, tierValue[1] is cooldown and
														// tierValue[2] is force
			IWeight w = player.getCapability(WeightProvider.WEIGHT_CAP, null);
			if (w.getWeight() >= Double.valueOf(tierValue[0])) {
				return new WeightTier(Double.valueOf(tierValue[0]), Integer.valueOf(tierValue[1]),
						Double.valueOf(tierValue[2]));
			}
		}
		return new WeightTier(0, ModConfig.common.balance.regenSpeed, ModConfig.common.balance.force);
	}
	
	/**
	 * Whether the player has the required dodging gamestage. If gamestages is not installed, will simply return true.
	 * @param player
	 * @return
	 */
	public static boolean hasGamestage(EntityPlayer player) {
		if (!Loader.isModLoaded("gamestages")) {
			return true;
		}
		return net.darkhax.gamestages.GameStageHelper.hasStage(player, ModConfig.common.gamestages.name);
	}
	
	/**
	 * Whether the player has the required airborne gamestage. If gamestages is not installed, will simply return false.
	 * @param player
	 * @return
	 */
	public static boolean hasAirborneGamestage(EntityPlayer player) {
		if (!Loader.isModLoaded("gamestages")) {
			return false;
		}
		return net.darkhax.gamestages.GameStageHelper.hasStage(player, ModConfig.common.gamestages.nameAirborne);
	}
}
