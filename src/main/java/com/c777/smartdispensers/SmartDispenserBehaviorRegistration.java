package com.c777.smartdispensers;

import com.c777.smartdispensers.behaviors.*;
import net.minecraft.block.*;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.*;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

public class SmartDispenserBehaviorRegistration {

	private static HashMap<IItemProvider, ArrayList<IDispenseItemBehavior>> cachedBehaviors = new HashMap<>();

	private static void register(IItemProvider provider, IDispenseItemBehavior behavior) {
		DispenserBlock.registerDispenseBehavior(provider, behavior);
	}

	private static void registerCachedBehaviors() {
		for(IItemProvider provider : cachedBehaviors.keySet()) {
			ArrayList<IDispenseItemBehavior> behaviors = cachedBehaviors.get(provider);
			if(behaviors.size() == 1) {
				register(provider, behaviors.get(0));
			} else {
				register(provider, new MultiDispenserBehavior(behaviors.toArray(new IDispenseItemBehavior[]{})));
			}
		}
	}

	private static void cacheBehavior(IItemProvider provider, IDispenseItemBehavior behavior) {
		if(!cachedBehaviors.containsKey(provider)) {
			cachedBehaviors.put(provider, new ArrayList<>());
		}
		cachedBehaviors.get(provider).add(behavior);
	}

	public static void registerSmartDispenserBehaviors() {
		if (ConfigData.SERVER.dispensersTillDirt.get()) registerUseHoeBehaviors();
		if (ConfigData.SERVER.dispensersPlantSeeds.get()) registerPlantSeedBehaviors();
		if (ConfigData.SERVER.dispensersSwingSwords.get()) registerSwingSwordBehaviors();
		if (ConfigData.SERVER.dispensersPlayRecords.get()) registerPlayRecordBehaviors();
		if (ConfigData.SERVER.dispensersApplyNametags.get()) registerApplyNametagBehavior();
		if (ConfigData.SERVER.dispensersEquipSaddles.get()) registerEquipSaddleBehavior();
		if (ConfigData.SERVER.dispensersPlantSaplings.get()) registerPlantSaplingBehaviors();
		if (ConfigData.SERVER.dispensersDyeSheep.get()) registerDyeSheepBehaviors();
		if (ConfigData.SERVER.dispensersBreedAnimals.get()) registerBreedAnimalsBehaviors();

		registerCachedBehaviors();
	}

	private static void registerUseHoeBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof HoeItem) {
				cacheBehavior(item, new UseHoeDispenserBehavior());
			}
		}
	}

	private static void registerPlantSeedBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				if(block instanceof CropsBlock || block instanceof StemBlock || block instanceof NetherWartBlock
				|| block instanceof CocoaBlock) {
					cacheBehavior(item, new PlantSeedsDispenserBehavior());
				}
			}
		}
	}

	private static void registerSwingSwordBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof SwordItem) {
				cacheBehavior(item, new SwingSwordDispenserBehavior());
			}
		}
	}

	private static void registerPlayRecordBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof MusicDiscItem) {
				cacheBehavior(item, new PlayRecordDispenserBehavior());
			}
		}
	}

	private static void registerApplyNametagBehavior() {
		cacheBehavior(Items.NAME_TAG, new ApplyNametagDispenserBehavior(ConfigData.SERVER.dispensersConsumeNametags.get()));
	}

	private static void registerEquipSaddleBehavior() {
		cacheBehavior(Items.SADDLE, new EquipSaddleDispenserBehavior());
	}

	private static void registerPlantSaplingBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof BlockItem) {
				Block block = ((BlockItem) item).getBlock();
				if(block instanceof SaplingBlock) {
					cacheBehavior(item, new PlantSaplingBehavior());
				}
			}
		}
	}

	private static void registerDyeSheepBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(item instanceof DyeItem) {
				cacheBehavior(item, new DyeSheepDispenserBehavior());
			}
		}
	}

	private static void registerBreedAnimalsBehaviors() {
		for(Item item : ForgeRegistries.ITEMS) {
			if(BreedAnimalsDispenserBehavior.BREEDING_ITEMS.contains(item)) {
				cacheBehavior(item, new BreedAnimalsDispenserBehavior());
			}
		}
	}

}
