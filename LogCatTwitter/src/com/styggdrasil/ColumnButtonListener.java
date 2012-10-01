package com.styggdrasil;

import android.view.View;
import android.widget.FrameLayout;

public class ColumnButtonListener implements View.OnClickListener
{
	AndroidUIColumnObserver column;
	FrameLayout tweetView;
	public ColumnButtonListener(AndroidUIColumnObserver _column,FrameLayout _view)
	{
		column=_column;
		tweetView=_view;
	}
	public void onClick(View view)
	{
		tweetView.removeAllViews();
		tweetView.addView(column.view);
	}
}
