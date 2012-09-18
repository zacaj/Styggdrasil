package com.styggdrasil;

import java.util.HashSet;
import java.util.Set;


public abstract class Column
{
	Set<Item>	contents;	// maybe should be a Map. Thoughts?

	public Column()
	{
		contents = new HashSet<Item>();
	}

	/**
	 * TwitterHandler will call this whenever a new Item is created
	 * 
	 * It is up to the implementation to decide whether it wants to add the item
	 * to contents
	 * 
	 * @param item
	 */
	abstract void newItem(Item item);
}
