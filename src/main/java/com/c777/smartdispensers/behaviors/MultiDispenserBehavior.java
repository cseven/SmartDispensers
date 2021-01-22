package com.c777.smartdispensers.behaviors;

import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.ItemStack;

public class MultiDispenserBehavior implements IDispenseItemBehavior {

	private IDispenseItemBehavior[] childBehaviors;

	public MultiDispenserBehavior(IDispenseItemBehavior[] behaviors) {
		childBehaviors = behaviors;
	}

	@Override
	public ItemStack dispense(IBlockSource dispenser, ItemStack itemStack) {
		ItemStack startStack = itemStack.copy();
		for(IDispenseItemBehavior behavior : childBehaviors) {
			ItemStack endStack = behavior.dispense(dispenser, itemStack);
			if(!ItemStack.areItemStacksEqual(startStack, endStack)) {
				return endStack;
			}
		}
		return itemStack;
	}
}
