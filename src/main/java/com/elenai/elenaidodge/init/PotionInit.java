package com.elenai.elenaidodge.init;

import com.elenai.elenaidodge.potions.BasePotion;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionInit {
	public static final Potion FORCEFUL_EFFECT = new BasePotion("forceful", false, 5883132, 0, 0);
	public static final Potion FEEBLE_EFFECT = new BasePotion("feeble", true, 8714579, 0, 1);
	
	public static final Potion CAN_DODGE_EFFECT = new BasePotion("can_dodge", false, 197223223, 1, 0);
	public static final Potion SLUGGISH_EFFECT = new BasePotion("sluggish", true, 1222513, 1, 1);
	public static final Potion SKYSTRIDE_EFFECT = new BasePotion("skystride", false, 25324595, 2, 0);

	public static final PotionType FORCEFUL = new PotionType("forceful", new PotionEffect[] {new PotionEffect(FORCEFUL_EFFECT, 3600)}).setRegistryName("forceful");
	public static final PotionType LONG_FORCEFUL = new PotionType("forceful", new PotionEffect[] {new PotionEffect(FORCEFUL_EFFECT, 9600)}).setRegistryName("long_forceful");
	public static final PotionType STRONG_FORCEFUL = new PotionType("forceful", new PotionEffect[] {new PotionEffect(FORCEFUL_EFFECT, 1800, 1)}).setRegistryName("strong_forceful");
	
	public static final PotionType FEEBLE = new PotionType("feeble", new PotionEffect[] {new PotionEffect(FEEBLE_EFFECT, 3600)}).setRegistryName("feeble");
	public static final PotionType LONG_FEEBLE = new PotionType("feeble", new PotionEffect[] {new PotionEffect(FEEBLE_EFFECT, 9600)}).setRegistryName("long_feeble");
	public static final PotionType STRONG_FEEBLE = new PotionType("feeble", new PotionEffect[] {new PotionEffect(FEEBLE_EFFECT, 1800, 1)}).setRegistryName("strong_feeble");
	
	public static final PotionType SLUGGISH = new PotionType("sluggish", new PotionEffect[] {new PotionEffect(SLUGGISH_EFFECT, 3600)}).setRegistryName("sluggish");
	public static final PotionType LONG_SLUGGISH = new PotionType("sluggish", new PotionEffect[] {new PotionEffect(SLUGGISH_EFFECT, 9600)}).setRegistryName("long_sluggish");
	
	public static final PotionType SKYSTRIDE = new PotionType("skystride", new PotionEffect[] {new PotionEffect(SKYSTRIDE_EFFECT, 3600)}).setRegistryName("skystride");
	public static final PotionType LONG_SKYSTRIDE = new PotionType("skystride", new PotionEffect[] {new PotionEffect(SKYSTRIDE_EFFECT, 9600)}).setRegistryName("long_skystride");
	
	public static void registerPotions() {
		registerPotion(FORCEFUL, LONG_FORCEFUL, STRONG_FORCEFUL, FORCEFUL_EFFECT);
		registerPotion(FEEBLE, LONG_FEEBLE, STRONG_FEEBLE, FEEBLE_EFFECT);
		registerEffectOnly(CAN_DODGE_EFFECT);
		registerPotion(SLUGGISH, LONG_SLUGGISH, SLUGGISH_EFFECT);
		registerPotion(SKYSTRIDE, LONG_SKYSTRIDE, SKYSTRIDE_EFFECT);

		registerPotionMixes();
	}
	
	private static void registerPotion(PotionType defaultPotion, PotionType longPotion, PotionType strongPotion, Potion effect) {
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);
		ForgeRegistries.POTION_TYPES.register(strongPotion);
	}
	
	private static void registerEffectOnly(Potion effect) {
		ForgeRegistries.POTIONS.register(effect);
	}
	
	private static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion effect) {
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);
	}
	
	private static void registerPotionMixes() {
		
		PotionHelper.addMix(PotionTypes.SWIFTNESS, Items.FEATHER, FORCEFUL);
		PotionHelper.addMix(PotionTypes.LONG_SWIFTNESS, ItemInit.IRON_FEATHER, LONG_FORCEFUL);
		PotionHelper.addMix(PotionTypes.STRONG_SWIFTNESS, ItemInit.IRON_FEATHER, STRONG_FORCEFUL);
		PotionHelper.addMix(FORCEFUL, Items.REDSTONE, LONG_FORCEFUL);
		PotionHelper.addMix(FORCEFUL, Items.GLOWSTONE_DUST, STRONG_FORCEFUL);
		
		PotionHelper.addMix(PotionTypes.SLOWNESS, ItemInit.IRON_FEATHER, FEEBLE);
		PotionHelper.addMix(PotionTypes.LONG_SLOWNESS, ItemInit.IRON_FEATHER, LONG_FEEBLE);
		PotionHelper.addMix(FEEBLE, Items.REDSTONE, LONG_FEEBLE);
		PotionHelper.addMix(FEEBLE, Items.GLOWSTONE_DUST, STRONG_FEEBLE);
	}
	
}
