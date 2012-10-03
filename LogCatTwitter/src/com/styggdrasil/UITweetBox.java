package com.styggdrasil;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class UITweetBox extends UIColumn
{
	RelativeLayout layout;
	EditText textField;
	LinearLayout buttons;
	AccountHandler handler;
	public UITweetBox(final UITwitterActivity activity,LinearLayout _buttons,AccountHandler _handler)
	{
		super(activity);
		buttons=_buttons;
		handler=_handler;
		{
			layout=new RelativeLayout(activity);
			view.addView(layout);
		}
		{
			textField=new EditText(activity){
                @Override
                public boolean onKeyPreIme(int keyCode, KeyEvent event)
                {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    {
            	    	buttons.setVisibility(View.VISIBLE);
                    }
                    return super.onKeyPreIme(keyCode, event);
                }
            };;
			textField.setId(1);
			textField.setLines(5);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        	lp.addRule(RelativeLayout.ABOVE,2);
			lp.addRule(RelativeLayout.ABOVE,3);
        	layout.addView(textField,lp);
			textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        	    @Override
        	    public void onFocusChange(View v, boolean hasFocus) {
        	    	buttons.setVisibility(hasFocus==true?View.GONE:View.VISIBLE);
        	    	if(!hasFocus)
    					activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        	    }
        	});
		}
		{
			Button button=new Button(activity);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.BELOW,1);
			lp.addRule(RelativeLayout.RIGHT_OF,3);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	button.setId(2);
        	button.setGravity(button.getGravity()|Gravity.RIGHT);
        	button.setText("Tweet");
        	button.setOnClickListener(new OnClickListener(){
				@Override public void onClick(View v)
				{
					//handler.sendTweet(textField.getText().toString());
					textField.setText("");
					textField.clearFocus();
					activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
					activity.popColumnStack();
				}
        	});
        	layout.addView(button,lp);
		}
		{
			Button button=new Button(activity);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.BELOW,1);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	//button.setGravity(Gravity.LEFT);
        	button.setId(3);
        	button.setText("Clear");
        	layout.addView(button,lp);
		}
	}

}
