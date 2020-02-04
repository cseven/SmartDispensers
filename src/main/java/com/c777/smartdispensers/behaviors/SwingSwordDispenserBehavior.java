package com.c777.smartdispensers.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.List;

public class SwingSwordDispenserBehavior implements IDispenseItemBehavior {

	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {

		float damage = ((SwordItem)itemStack.getItem()).getAttackDamage();

		World world = dispenser.getWorld();

		// Create fake player entity to perform sword swing
		PlayerEntity fakePlayer = FakePlayerFactory.getMinecraft((ServerWorld) world);

		// Figure out what direction we're facing
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);

		List<Entity> targetEntities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(forwardPos));

		if(targetEntities.size() > 0) {
			targetEntities.get(0).attackEntityFrom(DamageSource.GENERIC, damage);
			itemStack.damageItem(1, fakePlayer, playerEntity -> {});
		}

		return itemStack;
	}

}
