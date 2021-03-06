package com.styggdrasil;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;


public class TweetSelectListener implements OnClickListener
{
	static LinearLayout menu=null;
	static RelativeLayout currentTweet=null;
	private UITweetBox tweetBox;
	private UITwitterActivity activity;
	
	public Tweet tweet;
	public TweetSelectListener(Tweet _tweet, UITwitterActivity _activity)
	{
		tweet=_tweet;
		activity=_activity;
		tweetBox=activity.tweetBox;
	}

	@Override public void onClick(View v)
	{
		{
        	//HorizontalScrollView scroll=new HorizontalScrollView(this);
			if(menu==null)
				menu=new LinearLayout(v.getContext());
			if(currentTweet!=null)
				currentTweet.removeView(currentTweet.findViewById(6));
			menu.removeAllViews();
			if(currentTweet==v)
			{
				currentTweet=null;
				return;
			}
			menu.setOrientation(LinearLayout.HORIZONTAL);
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Reply");
            	button.setOnClickListener(new OnClickListener(){
					@Override public void onClick(View v)
					{
						String str="@"+tweet.user.username+" ";
						tweetBox.textField.setText(str);
						tweetBox.textField.setSelection(str.length());
						tweetBox.inReplyTo=tweet.id;
						activity.tweetView.removeAllViews();
						activity.tweetView.addView(tweetBox.view);
						if(activity.columnStack.contains(tweetBox))
								activity.columnStack.remove(tweetBox);
						activity.columnStack.add(tweetBox);
						tweetBox.switchedTo();
					}
            	});
            	menu.addView(button,lp);
        	}
        	if(!tweet.user.username.equalsIgnoreCase(tweet.handler.username))
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Retweet");
            	button.setOnClickListener(new OnClickListener(){
					@Override public void onClick(View v)
					{
						tweet.retweet();
					}
            	});
            	menu.addView(button,lp);
        	}
        	else
        	{
	        	{
	        		Button button=new Button(v.getContext());
	            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	            	button.setText("Delete");
	            	button.setOnClickListener(new OnClickListener(){
						@Override public void onClick(View v)
						{
							tweet.delete();
						}
	            	});
	            	menu.addView(button,lp);
	        	}
	        	{
	        		Button button=new Button(v.getContext());
	            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	            	button.setText("Undo");
	            	button.setOnClickListener(new OnClickListener(){
						@Override public void onClick(View v)
						{
							tweetBox.textField.setText(tweet.text);
							tweetBox.inReplyTo=tweet.replyId;
							tweet.delete();
							activity.tweetView.removeAllViews();
							activity.tweetView.addView(tweetBox.view);
							if(activity.columnStack.contains(tweetBox))
									activity.columnStack.remove(tweetBox);
							activity.columnStack.add(tweetBox);
							tweetBox.switchedTo();
						}
	            	});
	            	menu.addView(button,lp);
	        	}
        	}
        	
        	if(tweet.isFavorited)
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Unfavorite");
            	button.setOnClickListener(new OnClickListener(){
					@Override public void onClick(View v)
					{
						tweet.unfavorite();
					}
            	});
            	menu.addView(button,lp);
        	}
        	else
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Favorite");
            	button.setOnClickListener(new OnClickListener(){
					@Override public void onClick(View v)
					{
						tweet.favorite();
					}
            	});
            	menu.addView(button,lp);
        	}
        	menu.setOrientation(LinearLayout.HORIZONTAL);
        	
        	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        	lp.addRule(RelativeLayout.BELOW,1);;
        	menu.setId(6);
        	((RelativeLayout) v).addView(menu,lp);
        	currentTweet=(RelativeLayout) v;
        }
	}
}
