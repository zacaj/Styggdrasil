package com.styggdrasil;

import java.util.Calendar;

//could be anything that would appear in a Column (tweet, retweet, info, pic upload status, etc)
public abstract class Item implements Comparable<Item>
{
	long time;
	public Item()
	{
		time=Calendar.getInstance().getTimeInMillis();
	}
	
	public int compareTo(Item i)
	{
		return (int) (i.time-time);//newest on top
	}
}
