package com.styggdrasil;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class AndroidUIColumn extends Column
{
	public Column parent;
	public ListView view;
	public Activity activity;
	public LinearLayout tweetList;
	private Vector<DataSetObserver> observers;
	
	public AndroidUIColumn(Column _parent,Activity _activity)
	{
		parent=_parent;
		activity=_activity;
		view = new ListView(activity);
		observers=new Vector<DataSetObserver>();
		ListAdapter adapter=new ListAdapter() {
				
			
			@Override public int getCount()
			{
				return parent.contents.size();
			}

			@Override public Object getItem(int arg0)
			{
				return parent.contents.get(arg0);
			}

			@Override public long getItemId(int arg0)
			{
				return parent.contents.get(arg0).time;
			}

			@Override public int getItemViewType(int arg0)
			{
				// TODO handle multiple Item types
				return 0;
			}

			@Override public View getView(int arg0, View arg1, ViewGroup arg2)
			{
				Tweet tweet=(Tweet)parent.contents.get(arg0);
				if(arg1==null || !(arg1 instanceof TextView))
				{
					arg1=new TextView(activity);
					
					//((TextView)arg1).setText(tweet.user.getName() + ": " + tweet.text);
				}
				((TextView)arg1).setText(tweet.user.getName() + ":: " + tweet.text);
				return arg1;
			}

			@Override public int getViewTypeCount()
			{
				return 1;
			}

			@Override public boolean hasStableIds()
			{
				return true;
			}

			@Override public boolean isEmpty()
			{
				return parent.contents.isEmpty();
			}
			
			@Override public void registerDataSetObserver(
					DataSetObserver observer)
			{
				observers.add(observer);
			}

			@Override public void unregisterDataSetObserver(
					DataSetObserver observer)
			{
				observers.remove(observer);
			}

			@Override public boolean areAllItemsEnabled()
			{
				// TODO What does this MEAN
				return false;
			}

			@Override public boolean isEnabled(int position)
			{
				// TODO What does this MEAN
				return false;
			}
			
		};
		view.setAdapter(adapter);
		//view.setFillViewport(true);
		//view.addView(tweetList=new LinearLayout(activity));
        //tweetList.setOrientation(LinearLayout.VERTICAL);
	}
	public boolean newItem(Item item)
	{
		if (!(item instanceof Tweet))
			return false;
		final Tweet tweet = (Tweet) item;
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				//TextView text=new TextView(activity);
				//text.setText(tweet.user.getName() + ": " + tweet.text);
				//tweetList.addView(text,0);//TODO not necessarally at the top, need to find correct spot
				boolean ret=parent.newItem(tweet);
				Log.i("Twitter test", "New tweet");
				for(DataSetObserver observer:observers)
				{
					Log.i("Twitter test", "notified");
					observer.onChanged();
				}
			}
		});
		/*if(ret)
			if (item instanceof Tweet)
			{
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
			}*/
		return true;
	}
}
