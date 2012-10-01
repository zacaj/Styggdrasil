package com.styggdrasil;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.Set;
import java.util.Vector;


public abstract class Column
{
	protected Vector<Item>      contents;  // Needs to be a Vector for random access.  Use addItem to insert so it stays sorted
	private Set<ColumnObserver> observers; // Using a Set, as this describes the collection best, but feel free to change

	public Column()
	{
		contents = new Vector<Item>();
		observers = new HashSet<ColumnObserver>();
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
	public abstract boolean newItem(Item item);
	
	/**
	 * Inserts an item into contents ordered based on item.time
	 * @param item
	 */
	public synchronized void addItem(Item item)
	{
		int index=Collections.binarySearch(contents,item);
		if(index<0) index=~index;
		contents.add(index,item);
		
		for (ColumnObserver co : observers)
			co.onItemAdded(index, item);
	}
	
	/**
	 * Attaches an observer to the Column 
	 * @param observer
	 */
	public void addObserver(ColumnObserver observer)
	{
		observers.add(observer);
	}
	
	/**
	 * Detaches an observer from the Column
	 * @param observer
	 */
	public void removeObserver(ColumnObserver observer)
	{
		observers.remove(observer);
	}
}
