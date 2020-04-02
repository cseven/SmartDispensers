package com.c777.smartdispensers.behaviors;

import java.util.List;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DyeSheepDispenserBehavior implements IDispenseItemBehavior {

	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		World world = dispenser.getWorld();

		// Figure out what direction we're facing
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);
		DyeColor dyeColor = DyeColor.getColor(itemStack);

		List<Entity> targetEntities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(forwardPos));

		if(targetEntities.size() > 0) {
			Entity targetEntity = targetEntities.get(0);
			if(targetEntity instanceof SheepEntity) {
				if(targetEntity.isAlive()) {
					SheepEntity sheepEntity = (SheepEntity) targetEntity;
					sheepEntity.setFleeceColor(dyeColor);
					itemStack.shrink(1);
				}
			}

		}

		return itemStack;
	}
}