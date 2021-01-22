package com.c777.smartdispensers.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.List;

public class EquipSaddleDispenserBehavior implements IDispenseItemBehavior {
	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		World world = dispenser.getWorld();

		// Create fake player entity to equip saddle
		PlayerEntity fakePlayer = FakePlayerFactory.getMinecraft((ServerWorld) world);

		// Figure out what direction we're facing
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);

		List<LivingEntity> targetEntities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(forwardPos));

		for(LivingEntity target : targetEntities) {
			if(itemStack.interactWithEntity(fakePlayer, target, fakePlayer.getActiveHand()).isSuccess()) {
				return itemStack;
			} else if(target instanceof AbstractHorseEntity) {
				AbstractHorseEntity horseTarget = (AbstractHorseEntity)target;
				if(!horseTarget.isHorseSaddled() && horseTarget.isTame()) {
					// Saddle up the horse
					horseTarget.func_230266_a_(null);
					itemStack.shrink(1);
					return itemStack;
				}
			}
		}

		return itemStack;
	}
}
