package com.c777.smartdispensers.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.List;

public class ApplyNametagDispenserBehavior implements IDispenseItemBehavior {

	private final boolean consumeNametags;

	public ApplyNametagDispenserBehavior(boolean consumeNametags) {
		this.consumeNametags = consumeNametags;
	}

	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {

		World world = dispenser.getWorld();

		// Figure out what direction we're facing
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);

		List<Entity> targetEntities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(forwardPos));

		if (targetEntities.size() > 0) {
			Entity targetEntity = targetEntities.get(0);
			if (itemStack.hasDisplayName() && !(targetEntity instanceof PlayerEntity)) {
				if (targetEntity.isAlive()) {
					targetEntity.setCustomName(itemStack.getDisplayName());
					if (targetEntity instanceof MobEntity) {
						((MobEntity) targetEntity).enablePersistence();
					}

					if(consumeNametags) {
						itemStack.shrink(1);
					}
				}
			}

		}

		return itemStack;
	}

}
