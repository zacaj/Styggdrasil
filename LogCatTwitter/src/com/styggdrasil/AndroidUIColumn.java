package com.styggdrasil;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AndroidUIColumn extends Column
{
	public Column parent;
	public ScrollView view;
	public Activity activity;
	public LinearLayout tweetList;
	
	public AndroidUIColumn(Column _parent,Activity _activity)
	{
		parent=_parent;
		activity=_activity;
		view = new ScrollView(activity);
		view.setFillViewport(true);
		view.addView(tweetList=new LinearLayout(activity));
        tweetList.setOrientation(LinearLayout.VERTICAL);
	}
	public boolean newItem(Item item)
	{
		boolean ret=parent.newItem(item);
		if(ret)
			if (item instanceof Tweet)
			{
				final Tweet tweet = (Tweet) item;
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						TextView text=new TextView(activity);
						text.setText(tweet.user.getName() + ": " + tweet.text);
						tweetList.addView(text,0);//TODO not necessarally at the top, need to find correct spot
					}
				});
			}
		return ret;
	}
}
