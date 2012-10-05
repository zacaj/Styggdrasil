package com.styggdrasil;

import android.app.Activity;
import android.util.Log;


public class EveryColumn extends Column
{

	public EveryColumn(Activity _activity)
	{
		super(_activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean newItem(Item item)
	{
		if (item instanceof Tweet)
		{
			Tweet tweet = (Tweet) item;
			Log.d("Twitter test", tweet.user.getName() + ": " + tweet.text);
		}
		addItem(item);

		return true;
	}
}
