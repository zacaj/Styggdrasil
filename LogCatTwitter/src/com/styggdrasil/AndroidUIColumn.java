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
import android.widget.SeekBar;
import android.widget.TextView;

public class AndroidUIColumn extends Column
{
	public Column parent;
	public ListView view;
	public Activity activity;
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
	}
	public boolean newItem(Item item)
	{
		if (!(item instanceof Tweet))//TODO separate Item type handling...  somehow
			return false;
		final Tweet tweet = (Tweet) item;
		activity.runOnUiThread(new Runnable()//TODO should this really be on a separate thread?  Why IS it on a separate thread?  I have no idea.  Might have been by mistake?  Who knows?
		{
			@Override
			public void run()
			{
				boolean ret=parent.newItem(tweet);
				for(DataSetObserver observer:observers)
				{
					observer.onChanged();
				}
			}
		});
		return true;
	}
}
