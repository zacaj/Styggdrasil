package com.styggdrasil;

import android.util.Log;


public class EveryColumn extends Column
{

	@Override void newItem(Item item)
	{
		contents.add(item);

		if (item instanceof Tweet)
		{
			Tweet tweet = (Tweet) item;
			Log.d("Twitter test", tweet.user.getName() + ": " + tweet.text);
		}
	}
}
