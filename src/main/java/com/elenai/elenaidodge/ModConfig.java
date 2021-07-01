package com.elenai.elenaidodge;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = ElenaiDodge.MODID, name = "Elenai Dodge", type = Type.INSTANCE)
public class ModConfig {

	public static Client client = new Client();

	public static class Client {

		public Controls controls = new Controls();

		public class Controls {

			@Name("Enable Double Tap")
			@Comment("Enable to make dodging left, right or backwards require a double tap,"
					+ " and forwards require a tap of a movement key.")
			public boolean doubleTap = false;

			@Name("Double Tap Ticks")
			@Comment("How many system ticks you have between double tapping (these are faster than Minecraft ticks).")
			@RangeInt(min = 1, max = Integer.MAX_VALUE)
			public int doubleTapTicks = 200;
			
			@Name("Forwards Double Tap")
			@Comment("Enable to make dodging forwards require a double tap of a movement key. 'Enable Double Tap' must be enabled.")
			public boolean doubleTapForwards = false;
		}

		public Hud hud = new Hud();

		public class Hud {
			@Name("Show HUD")
			@Comment("Whether to show the feathers in the UI.")
			public boolean hud = true;

			@Name("Show Tutorial")
			@Comment("Whether to show the tutorial on joining a new world.")
			public boolean tutorial = true;

			@Name("Show Tooltips")
			@Comment("Whether to show armor weight tooltips. If Quark is installed, please restart the game after disabling or enabling 'Quark Settings -> Client -> Visual Stat Display'.")
			public boolean tooltips = true;
		}
	}

	public static Common common = new Common();

	public static class Common {

		public Balance balance = new Balance();

		public class Balance {
			
			@Name("Cooldown Speed")
			@Comment("The amount of ticks required for the Dodge cooldown to fully decrease when Armor Weights are disabled.")
			@RangeInt(min = 0, max = Integer.MAX_VALUE)
			public int regenSpeed = 20;

			@Name("Force")
			@Comment("The force of the player's dodge before any multipliers have been applied and when Armor Weights are disabled. This value is very sensitive.")
			@RangeDouble(min = 0.0, max = Double.MAX_VALUE)
			public double force = 0.6;

			@Name("Verticality")
			@Comment("How high the player is pushed from the ground when dodging. This value is proportional distance dodged due to friction.")
			@RangeDouble(min = 0.0, max = Double.MAX_VALUE)
			public double verticality = 0.25;

			@Name("Exhaustion")
			@Comment("How much exhaustion is added when dodging. For reference, sprinting adds 0.01 exhaustion per meter, and the Hunger effect adds 0.1 per second.")
			@RangeDouble(min = 0.0, max = 40.0)
			public double exhaustion = 0.4;

			@Name("Hunger Requirement")
			@Comment("How many half drumsticks the player needs to dodge. The default six is the same as sprinting.")
			@RangeInt(min = -1, max = 20)
			public int hunger = 6;

			@Name("Enable whilst Sneaking")
			@Comment("Whether the player can dodge whilst sneaking.")
			public boolean enableWhilstSneaking = false;

			@Name("Enable whilst Blocking")
			@Comment("Whether the player can dodge whilst blocking.")
			public boolean enableWhilstBlocking = false;

			@Name("Enable whilst Airborne")
			@Comment("Whether the player can dodge whilst airborne.")
			public boolean enableWhilstAirborne = false;

			@Name("Invincibility Ticks")
			@Comment("How many in-game ticks of invincibility the player has after dodging. 20 ticks is 1 second.")
			@RangeInt(min = 0, max = 100)
			public int invincibilityTicks = 10;

			@Name("Potion Blacklist")
			@Comment("Potions that prevent the player from dodging. Insert values in the format modid:potion. Idea Credit: SandwichHorror")
			public String[] potions = {};
			
			@Name("Requires Dodge Potion")
			@Comment("Whether the player requires the potion effect 'can_dodge' to dodge.")
			public boolean requiresPotion = false;
		}

		public Weights weights = new Weights();

		public class Weights {

			@Name("Weights Override")
			@Comment("The weight of each item of Armor. This overrides the default generated values and can be a decimal. Insert values as such: modid:itemname=value. Idea Credit: SandwichHorror")
			public String[] weights = { "minecraft:leather_boots=1", "minecraft:leather_leggings=2",
					"minecraft:leather_chestplate=3", "minecraft:leather_helmet=1", "minecraft:iron_boots=2",
					"minecraft:iron_leggings=3", "minecraft:iron_chestplate=5", "minecraft:iron_helmet=3",
					"minecraft:diamond_boots=3", "minecraft:diamond_leggings=4", "minecraft:diamond_chestplate=6",
					"minecraft:diamond_helmet=3", "minecraft:golden_boots=2", "minecraft:golden_leggings=2",
					"minecraft:golden_chestplate=3", "minecraft:golden_helmet=2", "minecraft:chainmail_boots=2",
					"minecraft:chainmail_leggings=2", "minecraft:chainmail_chestplate=3",
					"minecraft:chainmail_helmet=2", "minecraft:elytra=2", "twilightforest:naga_chestplate=3",
					"twilightforest:naga_leggings=2", "twilightforest:ironwood_helmet=2",
					"twilightforest:ironwood_chestplate=5", "twilightforest:ironwood_leggings=3",
					"twilightforest:ironwood_boots=2", "twilightforest:fiery_helmet=3",
					"twilightforest:fiery_chestplate=7", "twilightforest:fiery_leggings=4",
					"twilightforest:fiery_boots=3", "twilightforest:steeleaf_helmet=3",
					"twilightforest:steeleaf_chestplate=5", "twilightforest:steeleaf_leggings=4",
					"twilightforest:steeleaf_boots=3", "twilightforest:knightmetal_helmet=3",
					"twilightforest:knightmetal_chestplate=7", "twilightforest:knightmetal_leggings=5",
					"twilightforest:knightmetal_boots=2", "twilightforest:yeti_helmet=2",
					"twilightforest:yeti_chestplate=5", "twilightforest:yeti_leggings=3", "twilightforest:yeti_boots=2",
					"twilightforest:arctic_helmet=1", "twilightforest:arctic_chestplate=3",
					"twilightforest:arctic_leggings=2", "twilightforest:arctic_boots=1",
					"betterwithmods:steel_helmet=3", "betterwithmods:steel_chest=7", "betterwithmods:steel_pants=4",
					"betterwithmods:steel_boots=2", "atop:amethyst_helmet=3", "atop:amethyst_chestplate=6",
					"atop:amethyst_leggings=5", "atop:amethyst_boots=3", "atop:mud_helmet=1", "atop:mud_chestplate=3",
					"atop:mud_leggings=2", "atop:mud_boots=1", "atop:peridot_helmet=3", "atop:peridot_chestplate=6",
					"atop:peridot_leggings=4", "atop:peridot_boots=3", "atop:ruby_helmet=3", "atop:ruby_chestplate=6",
					"atop:ruby_leggings=4", "atop:ruby_boots=3", "atop:sapphire_helmet=3", "atop:sapphire_chestplate=6",
					"atop:sapphire_leggings=4", "atop:sapphire_boots=3  ", "quark:archaeologist_hat=0.25",
					"quark:pirate_hat=0.25", "quark:witch_hat=0.25", "dungeontactics:steel_helmet=3",
					"dungeontactics:steel_chestplate=6", "dungeontactics:steel_leggings=5",
					"dungeontactics:steel_boots=2", "endreborn:armour_helmet_helmet=5",
					"endreborn:armour_helmet_obsidian=5", "endreborn:armour_chestplate_obsidian=10",
					"endreborn:armour_leggings_obsidian=7", "endreborn:armour_boots_obsidian=4",
					"mod_lavacow:felarmor_helmet=1", "mod_lavacow:felarmor_chestplate=3",
					"mod_lavacow:felarmor_leggings=2", "mod_lavacow:felarmor_boots=1", "mod_lavacow:swinemask=0.25",
					"mod_lavacow:swinearmor_chestplate=3", "mod_lavacow:swinearmor_leggings=2",
					"mod_lavacow:swinearmor_boots=1", "mod_lavacow:faminearmor_helmet=1",
					"mod_lavacow:faminearmor_chestplate=2", "mod_lavacow:faminearmor_leggings=1",
					"mod_lavacow:faminearmor_boots=0.5", "iceandfire:armor_silver_metal_helmet=1",
					"iceandfire:armor_silver_metal_chestplate=3", "iceandfire:armor_silver_metal_leggings=2",
					"iceandfire:armor_silver_metal_boots=1", "iceandfire:deathworm_yellow_helmet=1",
					"iceandfire:deathworm_yellow_chestplate=3", "iceandfire:deathworm_yellow_leggings=2",
					"iceandfire:deathworm_yellow_boots=1", "iceandfire:deathworm_white_helmet=1",
					"iceandfire:deathworm_white_chestplate=3", "iceandfire:deathworm_white_leggings=2",
					"iceandfire:deathworm_white_boots=1", "iceandfire:deathworm_red_helmet=1",
					"iceandfire:deathworm_red_chestplate=3", "iceandfire:deathworm_red_leggings=2",
					"iceandfire:deathworm_red_boots=1", "iceandfire:myrmex_desert_helmet=1",
					"iceandfire:myrmex_desert_chestplate=2", "iceandfire:myrmex_desert_leggings=1.5",
					"iceandfire:myrmex_desert_boots=0.5", "iceandfire:myrmex_jungle_helmet=1",
					"iceandfire:myrmex_jungle_chestplate=2", "iceandfire:myrmex_jungle_leggings=1.5",
					"iceandfire:myrmex_jungle_boots=0.5", "iceandfire:dragonsteel_fire_helmet=3",
					"iceandfire:dragonsteel_fire_chestplate=7", "iceandfire:dragonsteel_fire_leggings=5",
					"iceandfire:dragonsteel_fire_boots=3", "iceandfire:dragonsteel_ice_helmet=3",
					"iceandfire:dragonsteel_ice_chestplate=7", "iceandfire:dragonsteel_ice_leggings=5",
					"iceandfire:dragonsteel_ice_boots=3", "iceandfire:armor_red_helmet=2",
					"iceandfire:armor_red_chestplate=3", "iceandfire:armor_red_leggings=2",
					"iceandfire:armor_red_boots=1", "iceandfire:armor_bronze_helmet=2",
					"iceandfire:armor_bronze_chestplate=3", "iceandfire:armor_bronze_leggings=2",
					"iceandfire:armor_bronze_boots=1", "iceandfire:armor_green_helmet=2",
					"iceandfire:armor_green_chestplate=3", "iceandfire:armor_green_leggings=2",
					"iceandfire:armor_green_boots=1", "iceandfire:armor_gray_helmet=2",
					"iceandfire:armor_gray_chestplate=3", "iceandfire:armor_gray_leggings=2",
					"iceandfire:armor_gray_boots=1", "iceandfire:armor_blue_helmet=2",
					"iceandfire:armor_blue_chestplate=3", "iceandfire:armor_blue_leggings=2",
					"iceandfire:armor_blue_boots=1", "iceandfire:armor_white_helmet=2",
					"iceandfire:armor_white_chestplate=3", "iceandfire:armor_white_leggings=2",
					"iceandfire:armor_white_boots=1", "iceandfire:armor_sapphire_helmet=2",
					"iceandfire:armor_sapphire_chestplate=3", "iceandfire:armor_sapphire_leggings=2",
					"iceandfire:armor_sapphire_boots=1", "iceandfire:armor_silver_helmet=2",
					"iceandfire:armor_silver_chestplate=3", "iceandfire:armor_silver_leggings=2",
					"iceandfire:armor_silver_boots=1", "iceandfire:tide_blue_helmet=0.5",
					"iceandfire:tide_blue_chestplate=2", "iceandfire:tide_blue_leggings=1",
					"iceandfire:tide_blue_boots=0.5", "iceandfire:tide_bronze_helmet=0.5",
					"iceandfire:tide_bronze_chestplate=2", "iceandfire:tide_bronze_leggings=1",
					"iceandfire:tide_bronze_boots=0.5", "iceandfire:tide_deepblue_helmet=0.5",
					"iceandfire:tide_deepblue_chestplate=2", "iceandfire:tide_deepblue_leggings=1",
					"iceandfire:tide_deepblue_boots=0.5", "iceandfire:tide_green_helmet=0.5",
					"iceandfire:tide_green_chestplate=2", "iceandfire:tide_green_leggings=1",
					"iceandfire:tide_green_boots=0.5", "iceandfire:tide_purple_helmet=0.5",
					"iceandfire:tide_purple_chestplate=2", "iceandfire:tide_purple_leggings=1",
					"iceandfire:tide_purple_boots=0.5", "iceandfire:tide_red_helmet=0.5",
					"iceandfire:tide_red_chestplate=2", "iceandfire:tide_red_leggings=1",
					"iceandfire:tide_red_boots=0.5", "iceandfire:tide_teal_helmet=0.5",
					"iceandfire:tide_teal_chestplate=2", "iceandfire:tide_teal_leggings=1",
					"iceandfire:tide_teal_boots=0.5", "iceandfire:forest_troll_leather_helmet=1",
					"iceandfire:forest_troll_leather_chestplate=3", "iceandfire:forest_troll_leather_leggings=2",
					"iceandfire:forest_troll_leather_boots=1", "iceandfire:frost_troll_leather_helmet=1",
					"iceandfire:frost_troll_leather_chestplate=3", "iceandfire:frost_troll_leather_leggings=2",
					"iceandfire:frost_troll_leather_boots=1", "iceandfire:mountain_troll_leather_helmet=1",
					"iceandfire:mountain_troll_leather_chestplate=3", "iceandfire:mountain_troll_leather_leggings=2",
					"iceandfire:mountain_troll_leather_boots=1", "iceandfire:sheep_helmet=0.25",
					"iceandfire:sheep_chestplate=1", "iceandfire:sheep_leggings=0.5", "iceandfire:sheep_boots=0.25",
					"mowziesmobs:wrought_helmet=2", "mowziesmobs:barako_mask=1", "mowziesmobs:barakoa_mask_fury=0.25",
					"mowziesmobs:barakoa_mask_fear=0.25", "mowziesmobs:barakoa_mask_rage=0.25",
					"mowziesmobs:barakoa_mask_misery=0.25", "netherex:wither_bone_helmet=0.25",
					"netherex:wither_bone_chestplate=2", "netherex:wither_bone_leggings=1",
					"netherex:wither_bone_boots=0.25", "primitivemobs:camouflage_helmet=0.15",
					"primitivemobs:camouflage_chestplate=1", "primitivemobs:camouflage_boots=0.15",
					"primitivemobs:camouflage_leggings=0.5", "rats:farmer_hat=0.1", "rats:fisherman_hat=0.1",
					"rats:top_hat=0.1", "rats:santa_hat=0.1", "rats:plague_doctor_mask=0.15",
					"rats:black_death_mask=0.15", "simpleores:mythril_helmet=1", "simpleores:mythril_chestplate=2",
					"simpleores:mythril_leggings=1", "simpleores:mythril_boots=0.5", "simpleores:adamantium_helmet=1",
					"simpleores:adamantium_chestplate=3", "simpleores:adamantium_leggings=2",
					"simpleores:adamantium_boots=1", "simpleores:onyx_helmet=5", "simpleores:onyx_chestplate=7",
					"simpleores:onyx_leggings=6", "simpleores:onyx_boots=5", "toroquest:toro_armor_boots=0.1",
					"toroquest:toro_armor_leggings=0.5", "toroquest:toro_armor_helmet=0.1",
					"toroquest:toro_armor_chestplate=1", "toroquest:royal_boots=0.25", "toroquest:royal_leggings=1",
					"toroquest:royal_helmet=0.25", "toroquest:royal_chestplate=2",
					"toroquest:legendary_bandit_helmet=0.1", "toroquest:samurai_boots=0.15",
					"toroquest:samurai_leggings=0.45", "toroquest:samurai_helmet=0.15",
					"toroquest:samurai_chestplate=1", "betteranimalsplus:hirschgeistskullwearable=0.5",
					"aether_legacy:zanite_helmet=3", "aether_legacy:zanite_chestplate=6",
					"aether_legacy:zanite_leggings=4", "aether_legacy:zanite_boots=2",
					"aether_legacy:gravitite_helmet=3", "aether_legacy:gravitite_chestplate=5",
					"aether_legacy:gravitite_leggings=2", "aether_legacy:gravitite_boots=0.5",
					"aether_legacy:neptune_helmet=1", "aether_legacy:neptune_chestplate=4",
					"aether_legacy:neptune_leggings=2", "aether_legacy:neptune_boots=1",
					"aether_legacy:phoenix_helmet=0.25", "aether_legacy:phoenix_chestplate=1",
					"aether_legacy:phoenix_leggings=0.5", "aether_legacy:phoenix_boots=0.25",
					"aether_legacy:valkyrie_helmet=2", "aether_legacy:valkyrie_chestplate=4",
					"aether_legacy:valkyrie_leggings=3", "aether_legacy:valkyrie_boots=2",
					"aether_legacy:sentry_boots=2", "defiledlands:scale_boots=2", "defiledlands:scale_leggings=3",
					"defiledlands:scale_chestplate=5", "defiledlands:scale_helmet=3",
					"defiledlands:scale_golden_boots=2", "defiledlands:scale_golden_leggings=3",
					"defiledlands:scale_golden_chestplate=5", "defiledlands:scale_golden_helmet=3",
					"nyx:meteor_boots=3", "nyx:meteor_pants=4", "nyx:meteor_chest=6", "nyx:meteor_helm=3",
					"ceramics:clay_boots=1", "ceramics:clay_leggings=2", "ceramics:clay_chestplate=3",
					"ceramics:clay_helmet=1", "dungeontactics:bounce_boots=2" };
			
			@Name("Weight Tiers")
			@Comment("The Cooldown Speed and Dodge Force given to the player depending on their weight. Each tier is determined by a minimum weight to enter it. Always start this at 0.0 and add each tier in increasing weight order. To make a player 'Overencumbered' simply set a tiers cooldown AND force to 0. Insert values as such: minimumweight:cooldown|force. Idea Credit: SandwichHorror")
			public String[] tiers = { "0.0:20|1.0", "5.0:35|0.8", "10.0:50|0.75", "15.0:65|0.6", "20.0:0|0" };
			
			@Name("Enable Weights")
			@Comment("Enables the Weight System")
			public boolean enable = true;
		}
		
		public Gamestages gamestages = new Gamestages();

		public class Gamestages {
			@Name("Dodging Requires Gamestage")
			@Comment("Whether the player requires a gamestage to dodge =! Requires GameStages to be Installed !=")
			public boolean enable = false;
			
			@Name("Dodging Gamestage Name")
			@Comment("The name of the required gamestage =! Requires GameStages to be Installed !=")
			public String name = "elenai_dodge";
			
			@Name("Airborne Gamestage Name")
			@Comment("The name of a gamestage that will allow the player to dodge in midair. This does not require 'Dodging Requires Gamestage' to be enabled to work. =! Requires GameStages to be Installed !=")
			public String nameAirborne = "elenai_dodge_airborne";
		}

		public Misc misc = new Misc();

		public class Misc {

			@Name("Nether Dodge")
			@Comment("Whether the Player dodges further in the Nether.")
			public boolean nether = true;

			@Name("End Dodge")
			@Comment("Whether the Player dodge is weaker in the End.")
			public boolean end = true;
			
			@Name("Enable Particles")
			@Comment("Whether the Player's dodge creates a puff of smoke.")
			public boolean particles = true;
		}
	}

}
