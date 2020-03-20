package com.c777.smartdispensers.behaviors;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

public class PlantSaplingBehavior implements IDispenseItemBehavior {
	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		World world = dispenser.getWorld();

		// Create fake player entity to perform block placement
		PlayerEntity fakePlayer = FakePlayerFactory.getMinecraft((ServerWorld)world);

		// Figure out exactly where we're trying to plant
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);

		if(!fakePlayer.canPlayerEdit(forwardPos, Direction.DOWN, itemStack)) {
			return itemStack;
		}

		BlockItem saplingBlock = (BlockItem)itemStack.getItem();

		saplingBlock.tryPlace(new DirectionalPlaceContext(world, forwardPos, Direction.DOWN, itemStack, Direction.UP));

		return itemStack;
	}
}
