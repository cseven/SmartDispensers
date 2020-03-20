package com.c777.smartdispensers;

import com.c777.smartdispensers.behaviors.*;
import net.minecraft.block.*;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.*;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class SmartDispenserBehaviorRegistration {

	private static void register(IItemProvider provider, IDispenseItemBehavior behavior) {
		DispenserBlock.registerDispenseBehavior(provider, behavior);
	}

	public static void registerSmartDispenserBehaviors() {
		if(ConfigData.SERVER.dispensersTillDirt.get()) registerUseHoeBehaviors();
		if(ConfigData.SERVER.dispensersPlantSeeds.get()) registerPlantSeedBehaviors();
		if(ConfigData.SERVER.dispensersSwingSwords.get()) registerSwingSwordBehaviors();
		if(ConfigData.SERVER.dispensersPlayRecords.get()) registerPlayRecordBehaviors();
		if(ConfigData.SERVER.dispensersApplyNametags.get()) registerApplyNametagBehavior();
		if(ConfigData.SERVER.dispensersEquipSaddles.get()) registerEquipSaddleBehavior();
		if(ConfigData.SERVER.dispensersPlantSaplings.get()) registerPlantSaplingBehaviors();
	}

	private static void registerUseHoeBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof HoeItem) {
				register(item, new UseHoeDispenserBehavior());
			}
		}
	}

	private static void registerPlantSeedBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				if(block instanceof CropsBlock || block instanceof StemBlock || block instanceof NetherWartBlock
				|| block instanceof CocoaBlock) {
					register(item, new PlantSeedsDispenserBehavior());
				}
			}
		}
	}

	private static void registerSwingSwordBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof SwordItem) {
				register(item, new SwingSwordDispenserBehavior());
			}
		}
	}

	private static void registerPlayRecordBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof MusicDiscItem) {
				register(item, new PlayRecordDispenserBehavior());
			}
		}
	}

	private static void registerApplyNametagBehavior() {
		register(Items.NAME_TAG, new ApplyNametagDispenserBehavior(ConfigData.SERVER.dispensersConsumeNametags.get()));
	}

	private static void registerEquipSaddleBehavior() {
		register(Items.SADDLE, new EquipSaddleDispenserBehavior());
	}

	private static void registerPlantSaplingBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				if(block instanceof SaplingBlock) {
					register(item, new PlantSaplingBehavior());
				}
			}
		}
	}

}
