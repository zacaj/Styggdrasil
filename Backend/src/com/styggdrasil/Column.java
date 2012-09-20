package com.styggdrasil;

import java.util.Calendar;
import java.util.Collections;
import java.util.SortedSet;
import java.util.Set;
import java.util.Vector;


public abstract class Column
{
	Vector<Item>	contents;// Needs to be a Vector for random access.  Use addItem to insert so it stays sorted

	public Column()
	{
		contents = new Vector<Item>();
	}

	/**
	 * TwitterHandler will call this whenever a new Item is created
	 * 
	 * It is up to the implementation to decide whether it wants to add the item
	 * to contents
	 * 
	 * @param item
	 * @return Whether the item was added
	 */
	abstract boolean newItem(Item item);
	
	/**
	 * Inserts an item into contents ordered based on item.time
	 * @param item
	 */
	public void addItem(Item item)
	{
		int index=Collections.binarySearch(contents,item);
		if(index<0) index=~index;
		contents.add(index,item);
	}
}
