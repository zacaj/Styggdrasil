package com.styggdrasil;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

public class ColumnButtonListener implements View.OnClickListener
{
	UIColumn column;
	FrameLayout tweetView;
	UITwitterActivity activity;
	public ColumnButtonListener(UIColumn _column,FrameLayout _view,UITwitterActivity _activity)
	{
		activity=_activity;
		column=_column;
		tweetView=_view;
	}
	public void onClick(View view)
	{
		tweetView.removeAllViews();
		activity.columnStack.lastElement().switchedFrom();
		tweetView.addView(column.view);
		if(activity.columnStack.contains(column))
				activity.columnStack.remove(column);
		activity.columnStack.add(column);
		column.switchedTo();
	}
}
