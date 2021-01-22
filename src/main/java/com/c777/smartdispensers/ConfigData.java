package com.c777.smartdispensers;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigData {

	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	static {
		final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_SPEC = specPair.getRight();
		SERVER = specPair.getLeft();
	}

	public static class ServerConfig {
		public final ForgeConfigSpec.BooleanValue dispensersTillDirt;
		public final ForgeConfigSpec.BooleanValue dispensersPlantSeeds;
		public final ForgeConfigSpec.BooleanValue dispensersSwingSwords;
		public final ForgeConfigSpec.BooleanValue dispensersPlayRecords;
		public final ForgeConfigSpec.BooleanValue dispensersApplyNametags;
		public final ForgeConfigSpec.BooleanValue dispensersConsumeNametags;
		public final ForgeConfigSpec.BooleanValue dispensersEquipSaddles;
		public final ForgeConfigSpec.BooleanValue dispensersPlantSaplings;
		public final ForgeConfigSpec.BooleanValue dispensersDyeSheep;
		public final ForgeConfigSpec.BooleanValue dispensersBreedAnimals;

		public ServerConfig(ForgeConfigSpec.Builder builder) {
			builder.push("DispenserBehaviors");
			dispensersTillDirt = builder
					.comment("Can dispensers use hoes to till dirt?")
					.define("dispensersTillDirt", true);
			dispensersPlantSeeds = builder
					.comment("Can dispensers plant seeds on tilled dirt?")
					.define("dispensersPlantSeeds", true);
			dispensersSwingSwords = builder
					.comment("Will dispensers hit mobs in front of them with swords?")
					.define("dispensersSwingSwords", true);
			dispensersPlayRecords = builder
					.comment("Should dispensers play records when facing a jukebox?")
					.define("dispensersPlayRecords", true);
			dispensersApplyNametags = builder
					.comment("Can dispensers apply nametags to mobs?")
					.define("dispensersApplyNametags", true);
			dispensersConsumeNametags = builder
					.comment("Do dispensers consume nametags they apply?")
					.define("dispensersConsumeNametags", true);
			dispensersEquipSaddles = builder
					.comment("Can dispensers put saddles onto pigs and tamed horses?")
					.define("dispensersEquipSaddles", true);
			dispensersPlantSaplings = builder
					.comment("Can dispensers plant saplings in front of them?")
					.define("dispensersPlantSaplings", true);
			dispensersDyeSheep = builder
					.comment("Can dispensers dye sheep in front of them?")
					.define("dispensersDyeSheep", true);
			dispensersBreedAnimals = builder
					.comment("Can dispensers breed animals in front of them?")
					.define("dispensersBreedAnimals", true);
			builder.pop();
		}
	}

}
