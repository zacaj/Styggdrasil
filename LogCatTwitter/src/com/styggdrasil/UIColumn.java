package com.styggdrasil;

import android.content.Context;
import android.widget.FrameLayout;

public abstract class UIColumn
{
	public FrameLayout view;
	
	public UIColumn(Context context)
	{
		view=new FrameLayout(context);
	}
	
	public abstract void switchedTo();
}
