package com.c777.smartdispensers.behaviors;

import net.minecraft.block.*;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;

public class PlayRecordDispenserBehavior implements IDispenseItemBehavior {
	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		World world = dispenser.getWorld();

		// Create fake player entity to perform block placement
		PlayerEntity fakePlayer = FakePlayerFactory.getMinecraft((ServerWorld)world);

		// Figure out exactly where we're trying to till
		Direction direction = dispenser.getBlockState().get(DispenserBlock.FACING);
		BlockPos forwardPos = dispenser.getBlockPos().offset(direction);

		if(!fakePlayer.canPlayerEdit(forwardPos, Direction.DOWN, itemStack)) {
			return itemStack;
		}

		BlockState jukeboxState = world.getBlockState(forwardPos);
		Block block = jukeboxState.getBlock();

		if(block != Blocks.JUKEBOX) {
			return itemStack;
		}

		JukeboxBlock jukebox = (JukeboxBlock)block;
		jukebox.onBlockActivated(jukeboxState, world, forwardPos, fakePlayer, null, null);
		jukebox.insertRecord(world, forwardPos, jukeboxState, itemStack);
		world.playEvent(null, 1010, forwardPos, Item.getIdFromItem(itemStack.getItem()));
		itemStack.shrink(1);

		return itemStack;
	}
}
