package com.styggdrasil;

public interface ColumnObserver
{
	/**
	 * Indicates to the observer that an item has been added to the
	 * column at the specified location.
	 * @param index
	 * @param item
	 */
	void onItemAdded(int index, Item item);
	
	/**
	 * Indicates to the observer that an item has been removed from the
	 * column at the specified location.
	 * @param index
	 * @param item
	 */
	void onItemRemoved(int index, Item item);
}
