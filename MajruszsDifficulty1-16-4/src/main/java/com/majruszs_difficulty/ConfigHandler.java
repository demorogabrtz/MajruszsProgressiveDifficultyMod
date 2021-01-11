package com.majruszs_difficulty;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec CONFIG_SPEC;
	public static final String FILENAME = "majruszs-difficulty.toml";

	public static class Config {
		public static class Features {
			public static ForgeConfigSpec.BooleanValue FALL_DAMAGE_EFFECTS, CREEPER_CHARGED, CREEPER_EFFECTS, SKELETON_GROUPS, ZOMBIE_GROUPS, ILLUSIONER_SPAWNING, GIANT_SPAWNING, PILLAGER_WOLF_SPAWNING, PIGLIN_GROUPS, PILLAGER_GROUPS, SPIDER_POISON, EVOKER_TOTEM, WITHER_SKELETON_SWORD, SKY_KEEPER_SPAWNING, SKY_KEEPER_LEVITATION, DROWNED_LIGHTNING, DROWNING_EFFECTS, CACTUS_BLEEDING, TOOL_BLEEDING;
		}

		public static class Chances {
			public static ForgeConfigSpec.DoubleValue CREEPER_CHARGED, CREEPER_EFFECTS, ENEMY_GROUPS, SPIDER_POISON, ELITE_TIPPED_ARROW, SKY_KEEPER_LEVITATION, DROWNED_LIGHTNING, DROWNING_EFFECTS, CACTUS_BLEEDING, TOOL_BLEEDING;
		}

		public static class Durations {
			public static ForgeConfigSpec.DoubleValue WITHER_SWORD_EFFECT, DROWNING_EFFECTS, CACTUS_BLEEDING, TOOL_BLEEDING;
		}

		public static class Values {
			public static ForgeConfigSpec.IntValue UNDEAD_ARMY_KILL_REQUIREMENT, FISHED_ITEMS_BAG_REQUIREMENT_NORMAL, FISHED_ITEMS_BAG_REQUIREMENT_EXPERT, FISHED_ITEMS_BAG_REQUIREMENT_MASTER, BLEEDING_AMPLIFIER_NORMAL, BLEEDING_AMPLIFIER_EXPERT, BLEEDING_AMPLIFIER_MASTER;
			public static ForgeConfigSpec.DoubleValue UNDEAD_ARMY_SCALE_WITH_PLAYERS;
		}

		public static boolean isDisabled( ForgeConfigSpec.BooleanValue config ) {
			return !config.get();
		}

		public static double getChance( ForgeConfigSpec.DoubleValue chance ) {
			return chance.get();
		}

		public static int getDurationInSeconds( ForgeConfigSpec.DoubleValue duration ) {
			return MajruszsHelper.secondsToTicks( duration.get() );
		}

		public static int getInteger( ForgeConfigSpec.IntValue value ) {
			return value.get();
		}

		public static double getDouble( ForgeConfigSpec.DoubleValue value ) {
			return value.get();
		}
	}

	public static void register( final ModLoadingContext context ) {
		ConfigHandler.load();

		context.registerConfig( ModConfig.Type.COMMON, CONFIG_SPEC, FILENAME );
	}

	private static void load() {
		BUILDER.comment( "Remember to restart your client/server after changing config!" );

		BUILDER.push( "Features" );
		Config.Features.FALL_DAMAGE_EFFECTS = createConfigSpecForBoolean( "fall_damage_effects", "", true );
		Config.Features.CREEPER_CHARGED = createConfigSpecForBoolean( "creeper_charged", "", true );
		Config.Features.CREEPER_EFFECTS = createConfigSpecForBoolean( "creeper_effects", "", true );
		Config.Features.SKELETON_GROUPS = createConfigSpecForBoolean( "skeleton_groups", "", true );
		Config.Features.ZOMBIE_GROUPS = createConfigSpecForBoolean( "zombie_groups", "", true );
		Config.Features.ILLUSIONER_SPAWNING = createConfigSpecForBoolean( "illusioner_spawning", "", true );
		Config.Features.GIANT_SPAWNING = createConfigSpecForBoolean( "giant_spawning", "", true );
		Config.Features.PILLAGER_WOLF_SPAWNING = createConfigSpecForBoolean( "pillager_wolf_spawning", "", true );
		Config.Features.PIGLIN_GROUPS = createConfigSpecForBoolean( "piglin_groups", "", true );
		Config.Features.PILLAGER_GROUPS = createConfigSpecForBoolean( "pillager_groups", "", true );
		Config.Features.SPIDER_POISON = createConfigSpecForBoolean( "spider_poison", "", true );
		Config.Features.EVOKER_TOTEM = createConfigSpecForBoolean( "evoker_totem", "", true );
		Config.Features.WITHER_SKELETON_SWORD = createConfigSpecForBoolean( "wither_skeleton_sword", "", true );
		Config.Features.SKY_KEEPER_SPAWNING = createConfigSpecForBoolean( "sky_keeper_spawning", "", true );
		Config.Features.SKY_KEEPER_LEVITATION = createConfigSpecForBoolean( "sky_keeper_levitation_attack", "", true );
		Config.Features.DROWNED_LIGHTNING = createConfigSpecForBoolean( "drowned_trident_lightning_attack", "", true );
		Config.Features.DROWNING_EFFECTS = createConfigSpecForBoolean( "drowning_negative_effects", "", true );
		Config.Features.CACTUS_BLEEDING = createConfigSpecForBoolean( "cactus_bleeding", "", true );
		Config.Features.TOOL_BLEEDING = createConfigSpecForBoolean( "tool_bleeding", "", true );
		BUILDER.pop();

		BUILDER.comment( "Remember these chances below are scaled by Clamped Regional Difficulty! (0.0 ~ 1.0)" );

		BUILDER.push( "Chances" );
		Config.Chances.CREEPER_CHARGED = createConfigSpecForDouble( "creeper_charged_chance", "", 0.125, 0.0, 1.0 );
		Config.Chances.CREEPER_EFFECTS = createConfigSpecForDouble( "creeper_effects_chance", "", 0.375, 0.0, 1.0 );
		Config.Chances.ENEMY_GROUPS = createConfigSpecForDouble( "enemy_groups_chance", "", 0.25, 0.0, 1.0 );
		Config.Chances.SPIDER_POISON = createConfigSpecForDouble( "spider_poison_chance", "", 0.25, 0.0, 1.0 );
		Config.Chances.ELITE_TIPPED_ARROW = createConfigSpecForDouble( "elite_skeleton_tipped_arrow_chance", "", 0.75, 0.0, 1.0 );
		Config.Chances.SKY_KEEPER_LEVITATION = createConfigSpecForDouble( "sky_keeper_levitation_chance", "", 0.375, 0.0, 1.0 );
		Config.Chances.DROWNED_LIGHTNING = createConfigSpecForDouble( "drowned_trident_lightning_attack_chance", "", 0.25, 0.0, 1.0 );
		Config.Chances.DROWNING_EFFECTS = createConfigSpecForDouble( "drowning_negative_effects_chance", "", 1.0, 0.0, 1.0 );
		Config.Chances.CACTUS_BLEEDING = createConfigSpecForDouble( "cactus_bleeding_chance", "", 0.5, 0.0, 1.0 );
		Config.Chances.TOOL_BLEEDING = createConfigSpecForDouble( "tool_bleeding_chance", "", 0.25, 0.0, 1.0 );
		BUILDER.pop();

		BUILDER.comment( "All durations below are given in seconds." );

		BUILDER.push( "Durations" );
		Config.Durations.WITHER_SWORD_EFFECT = createConfigSpecForDouble( "wither_sword_effect_duration", "", 6.0, 2.0, 30.0 );
		Config.Durations.DROWNING_EFFECTS = createConfigSpecForDouble( "drowning_negative_effects_duration", "", 5.0, 3.0, 10.0 );
		Config.Durations.CACTUS_BLEEDING = createConfigSpecForDouble( "cactus_bleeding_duration", "", 6.0, 1.0, 60.0 );
		Config.Durations.TOOL_BLEEDING = createConfigSpecForDouble( "tool_bleeding_duration", "", 6.0, 1.0, 60.0 );
		BUILDER.pop();

		BUILDER.push( "Values" );
		Config.Values.UNDEAD_ARMY_KILL_REQUIREMENT = createConfigSpecForInteger( "undead_army_kill_requirement", "", 50, 20, 500 );
		Config.Values.FISHED_ITEMS_BAG_REQUIREMENT_NORMAL = createConfigSpecForInteger( "fished_items_bag_requirement_normal", "", 30, 5, 100 );
		Config.Values.FISHED_ITEMS_BAG_REQUIREMENT_EXPERT = createConfigSpecForInteger( "fished_items_bag_requirement_expert", "", 20, 5, 100 );
		Config.Values.FISHED_ITEMS_BAG_REQUIREMENT_MASTER = createConfigSpecForInteger( "fished_items_bag_requirement_master", "", 10, 5, 100 );
		Config.Values.BLEEDING_AMPLIFIER_NORMAL = createConfigSpecForInteger( "bleeding_amplifier_normal", "", 0, 0, 9 );
		Config.Values.BLEEDING_AMPLIFIER_EXPERT = createConfigSpecForInteger( "bleeding_amplifier_expert", "", 0, 0, 9 );
		Config.Values.BLEEDING_AMPLIFIER_MASTER = createConfigSpecForInteger( "bleeding_amplifier_master", "", 1, 0, 9 );
		Config.Values.UNDEAD_ARMY_SCALE_WITH_PLAYERS = createConfigSpecForDouble( "undead_army_scale_with_players", "", 0.5, 0.1, 1.0 );
		BUILDER.pop();

		CONFIG_SPEC = BUILDER.build();
	}

	private static ForgeConfigSpec.BooleanValue createConfigSpecForBoolean( String name, String comment, boolean defaultValue ) {
		return BUILDER.worldRestart()
			.define( name, defaultValue );
	}

	private static ForgeConfigSpec.DoubleValue createConfigSpecForDouble( String name, String comment, double defaultValue, double min, double max ) {
		return BUILDER.worldRestart()
			.defineInRange( name, defaultValue, min, max );
	}

	private static ForgeConfigSpec.IntValue createConfigSpecForInteger( String name, String comment, int defaultValue, int min, int max ) {
		return BUILDER.worldRestart()
			.defineInRange( name, defaultValue, min, max );
	}
}
