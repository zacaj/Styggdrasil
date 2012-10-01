package com.styggdrasil;

import android.util.Log;


public class EveryColumn extends Column
{

	@Override
	public boolean newItem(Item item)
	{
		addItem(item);

		if (item instanceof Tweet)
		{
			Tweet tweet = (Tweet) item;
			Log.d("Twitter test", tweet.user.getName() + ": " + tweet.text);
		}
		return true;
	}
}
