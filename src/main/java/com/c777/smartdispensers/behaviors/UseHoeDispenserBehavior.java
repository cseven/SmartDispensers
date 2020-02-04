package com.c777.smartdispensers.behaviors;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

public class UseHoeDispenserBehavior implements IDispenseItemBehavior {

	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		if(!(itemStack.getItem() instanceof HoeItem)) {
			return itemStack;
		}

		World world = dispenser.getWorld();

		// Create fake player entity to perform operation
		PlayerEntity fakePlayer = FakePlayerFactory.getMinecraft((ServerWorld)world);

		// Figure out exactly where we're trying to till
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);
		BlockPos dirtPos = forwardPos.down();

		// Ensure that the fake player can edit in this location
		if(!fakePlayer.canPlayerEdit(dirtPos, Direction.DOWN, itemStack)) {
			return itemStack;
		}

		Block targetBlock = world.getBlockState(dirtPos).getBlock();

		if(world.isAirBlock(forwardPos)) {
			if(targetBlock == Blocks.GRASS_BLOCK || targetBlock == Blocks.GRASS_PATH || targetBlock == Blocks.DIRT) {
				world.setBlockState(dirtPos, Blocks.FARMLAND.getDefaultState());
				itemStack.damageItem(1, fakePlayer, playerEntity -> {});
				return itemStack;
			} else if(targetBlock == Blocks.COARSE_DIRT) {
				// Tilling coarse dirt turns it into regular dirt
				world.setBlockState(dirtPos, Blocks.DIRT.getDefaultState());
				return itemStack;
			}
		}

		return itemStack;
	}

}
