package com.styggdrasil;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class AndroidUIColumnObserver implements ColumnObserver
{
	public Column column;
	public ListView view;
	public Activity activity;
	private Vector<DataSetObserver> observers;
	
	public AndroidUIColumnObserver(Column _column,Activity _activity)
	{
		column=_column;
		column.addObserver(this);
		activity=_activity;
		view = new ListView(activity);
		observers=new Vector<DataSetObserver>();
		ListAdapter adapter=new ListAdapter() {			
			@Override public int getCount()
			{
				return column.contents.size();
			}

			@Override public Object getItem(int arg0)
			{
				return column.contents.get(arg0);
			}

			@Override public long getItemId(int arg0)
			{
				return column.contents.get(arg0).time;
			}

			@Override public int getItemViewType(int arg0)
			{			
				return column.contents.get(arg0).getType();
			}

			@Override public View getView(int index, View view, ViewGroup arg2)
			{
				if(view==null || !(view instanceof RelativeLayout))
				{
					view=new RelativeLayout(activity);
				}
				RelativeLayout layout=(RelativeLayout)view;
				Item item=column.contents.get(index);
				int type=item.getType();
				switch(type)
				{
				case Retweet.type:
				{
					Retweet retweet=(Retweet)item;
					{
						{//rt
							TextView text=(TextView)layout.findViewById(5);
							if(text==null)
							{
								text=new TextView(activity);
								text.setBackgroundColor(Color.LTGRAY);
								text.setTextSize(10);
								text.setTextColor(Color.DKGRAY);
								text.setId(5);
								RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
								lp.addRule(RelativeLayout.RIGHT_OF,2);
					        	lp.addRule(RelativeLayout.LEFT_OF,3);
					        	lp.addRule(RelativeLayout.ABOVE,1);
					        	text.setGravity(Gravity.BOTTOM);
					        	layout.addView(text,lp);
							}				
							String str="RT by "+retweet.retweetedBy.getName();
							if(retweet.nRetweet>1)
								str+=" (x"+new Long(retweet.nRetweet).toString()+")";
							text.setText(str);
						}
					}
				}
				case Tweet.type:
				{
					Tweet tweet=(Tweet)item;
					{
			        	ImageView image=(ImageView)layout.findViewById(4);
			        	if(image==null)
			        	{
			        		image=new ImageView(activity);
							image.setId(4);
							RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				        	lp.addRule(RelativeLayout.BELOW,2);
				        	lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				        	layout.addView(image,lp);
			        	}
			        	image.setImageBitmap(tweet.user.avatar);
					}
					{//username
						TextView text=(TextView)layout.findViewById(2);
						if(text==null)
						{
							text=new TextView(activity);
							text.setBackgroundColor(Color.LTGRAY);
							text.setTextSize(12);
							text.setTextColor(Color.DKGRAY);
							text.setId(2);
							RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				        	lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				        	lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				        	layout.addView(text,lp);
						}				
						text.setText(tweet.user.getName());
					}
					{//time
						TextView text=(TextView)layout.findViewById(3);
						if(text==null)
						{
							text=new TextView(activity);
							text.setBackgroundColor(Color.LTGRAY);
							text.setTextSize(10);
							text.setTextColor(Color.DKGRAY);
							text.setId(3);
							RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				        	lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				        	lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				        	layout.addView(text,lp);
						}				
						text.setText(tweet.dateString);
					}
					{//text
						TextView text=(TextView)layout.findViewById(1);
						if(text==null)
						{
							text=new TextView(activity);
							text.setBackgroundColor(Color.LTGRAY);
							text.setTextSize(14);
							text.setTextColor(Color.BLACK);
							text.setId(1);
							RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);	        	
							lp.addRule(RelativeLayout.BELOW,2);
				        	lp.addRule(RelativeLayout.BELOW,3);
				        	lp.addRule(RelativeLayout.RIGHT_OF,4);
				        	layout.addView(text,lp);
						}			
						text.setText(tweet.text);
					}
					view.setOnClickListener(new TweetSelectListener(tweet));
					break;
				}
				}
				return view;
			}

			@Override public int getViewTypeCount()
			{
				return 3;
			}

			@Override public boolean hasStableIds()
			{
				return true;
			}

			@Override public boolean isEmpty()
			{
				return column.contents.isEmpty();
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
	@Override public void onItemAdded(int index, Item item)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				for(DataSetObserver observer:observers)
				{
					observer.onChanged();
				}
			}
		});
	}
	@Override public void onItemRemoved(int index, Item item)
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				for(DataSetObserver observer:observers)
				{
					observer.onChanged();
				}
			}
		});	
	}
}
