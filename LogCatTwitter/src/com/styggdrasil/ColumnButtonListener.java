package com.styggdrasil;

import android.view.View;
import android.widget.FrameLayout;

public class ColumnButtonListener implements View.OnClickListener
{
	AndroidUIColumn column;
	FrameLayout tweetView;
	public ColumnButtonListener(AndroidUIColumn _column,FrameLayout _view)
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
