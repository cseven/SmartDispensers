package com.c777.smartdispensers.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BreedAnimalsDispenserBehavior implements IDispenseItemBehavior {

	public static final Set<Item> BREEDING_ITEMS = new HashSet<>(Arrays.asList(
			Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_CARROT, Items.WHEAT, Items.CARROT,
			Items.POTATO, Items.BEETROOT, Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS, Items.BEETROOT_SEEDS,
			Items.BEEF, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.RABBIT, Items.ROTTEN_FLESH,
			Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT, Items.COOKED_MUTTON,
			Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.HAY_BLOCK, Items.DANDELION));

	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		World world = dispenser.getWorld();

		// Figure out what direction we're facing
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);

		List<Entity> targetEntities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(forwardPos));

		for(Entity entity : targetEntities) {
			if(entity instanceof AnimalEntity) {
				if(((AnimalEntity) entity).canBreed() && ((AnimalEntity) entity).isBreedingItem(itemStack)) {
					itemStack.shrink(1);
					((AnimalEntity) entity).setInLove(null);
				}
			}
		}

		return itemStack;
	}
}
