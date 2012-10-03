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
	
	public Tweet tweet;
	public TweetSelectListener(Tweet _tweet)
	{
		tweet=_tweet;
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
				return;
			menu.setOrientation(LinearLayout.HORIZONTAL);
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Reply");
            	menu.addView(button,lp);
        	}
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Retweet");
            	menu.addView(button,lp);
        	}
        	{
        		Button button=new Button(v.getContext());
            	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            	button.setText("Favorite");
            	menu.addView(button,lp);
        	}
        	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	lp.addRule(RelativeLayout.BELOW,1);
        	menu.setId(6);
        	((RelativeLayout) v).addView(menu,lp);
        	currentTweet=(RelativeLayout) v;
        }
	}
}
