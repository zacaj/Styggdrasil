package com.styggdrasil;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
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
	RelativeLayout bottomBar;
	AccountHandler handler;
	UITwitterActivity activity;
	TextView count;
	public long	inReplyTo;
	
	public UITweetBox(final UITwitterActivity _activity,RelativeLayout _bottomBar,AccountHandler _handler)
	{
		super(_activity);
		inReplyTo=-1;
		bottomBar=_bottomBar;
		handler=_handler;
		activity=_activity;
		{
			layout=new RelativeLayout(activity);
			
			view.addView(layout);
		}
		{
			textField=new EditText(activity){
                @Override
                public boolean onKeyPreIme(int keyCode, KeyEvent event)
                {
                    /*if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    {
                    	bottomBar.setVisibility(View.VISIBLE);
                    }*/
                    return super.onKeyPreIme(keyCode, event);
                }
            };;
			textField.setId(1);
			textField.setLines(5);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        	lp.addRule(RelativeLayout.ABOVE,2);
        	//lp.addRule(RelativeLayout.ABOVE,3);
			//lp.addRule(RelativeLayout.ABOVE,4);
        	layout.addView(textField,lp);
			textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        	    @Override
        	    public void onFocusChange(View v, boolean hasFocus) {
        	    	//Log.i("Twitter test","focus changed "+new Boolean(hasFocus).toString());
        	    	//bottomBar.setVisibility(hasFocus==true?View.GONE:View.VISIBLE);
        	    	//if(!hasFocus)
        	    	//	bottomBar.setVisibility(View.VISIBLE);
        	    }
        	});
			textField.addTextChangedListener(new TextWatcher(){

				@Override public void afterTextChanged(Editable arg0)
				{
					String str=arg0.toString();
					if(str.length()<=140)
						count.setText(new Long(str.length()).toString());
					else 
						count.setText(new Long(str.length()).toString()+" (x"+new Long(str.length()/140+1).toString()+")");
				}

				@Override public void beforeTextChanged(CharSequence arg0,
						int arg1, int arg2, int arg3)
				{
					// TODO Auto-generated method stub
					
				}

				@Override public void onTextChanged(CharSequence arg0,
						int arg1, int arg2, int arg3)
				{
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		{
			Button button=new Button(activity);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.BELOW,1);
			//lp.addRule(RelativeLayout.RIGHT_OF,4);
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        	button.setId(2);
        	//button.setGravity(button.getGravity()|Gravity.RIGHT);
        	button.setText("Tweet");
        	button.setOnClickListener(new OnClickListener(){
				@Override public void onClick(View v)
				{

					InputMethodManager imm = (InputMethodManager)activity.getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(textField.getWindowToken(), 0);
					activity.popColumnStack();
					if(inReplyTo!=0)
						handler.sendTweet(textField.getText().toString(),inReplyTo);
					else
						handler.sendTweet(textField.getText().toString());
					textField.setText("");
					//textField.clearFocus();
					inReplyTo=-1;
					/*textField.postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							InputMethodManager imm = (InputMethodManager)activity.getSystemService(
								      Context.INPUT_METHOD_SERVICE);
							//	imm.hideSoftInputFromWindow(textField.getWindowToken(), 0);
		            	    //bottomBar.setVisibility(View.VISIBLE);
						}
					}, 100);*/
				}
        	});
        	layout.addView(button,lp);
		}
		{
			Button button=new Button(activity);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.BELOW,1);
			//lp.addRule(RelativeLayout.LEFT_OF,4);
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        	//button.setGravity(Gravity.LEFT);
        	button.setId(3);
        	button.setText("Clear");
        	button.setOnClickListener(new OnClickListener(){
				@Override public void onClick(View v)
				{
					textField.setText("");
				}
        	});
        	layout.addView(button,lp);
		}
		{
			count=new TextView(activity);
			RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.RIGHT_OF,3);
			lp.addRule(RelativeLayout.LEFT_OF,2);
			//lp.addRule(RelativeLayout.BELOW,1);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	count.setId(4);
        	count.setText("0");
        	count.setTextSize(25);
        	count.setGravity(count.getGravity()|Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        	layout.addView(count,lp);
			
		}
	}
	@Override public void switchedTo()
	{
		//activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		//((InputMethodManager)(activity.getSystemService(Context.INPUT_METHOD_SERVICE))).showSoftInput(textField, InputMethodManager.SHOW_IMPLICIT);
		textField.requestFocus();
		
		textField.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(textField, InputMethodManager.SHOW_IMPLICIT);
				//bottomBar.setVisibility(View.GONE);	
				textField.requestFocus();
			}
		}, 100);
	}
	@Override public void switchedFrom()
	{
		textField.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				InputMethodManager imm = (InputMethodManager)activity.getSystemService(
					      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(textField.getWindowToken(), 0);
        	   // bottomBar.setVisibility(View.VISIBLE);
			}
		}, 100);	
	}

}
