package com.styggdrasil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Stack;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;


public class UITwitterActivity extends Activity
{
	private RelativeLayout view;
	public FrameLayout tweetView;
	public AccountHandler handler;
	public Vector<UIColumn> columnStack;
	public UITweetBox tweetBox;
	
	public void popColumnStack()
	{
		if(columnStack.size()<=1)
			return;
		columnStack.remove(columnStack.size()-1);
		tweetView.removeAllViews();
		tweetView.addView(columnStack.lastElement().view);
	}
	@Override public void onBackPressed()
	{
		if(columnStack.size()<=1)
			finish();
		else
			popColumnStack();
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        columnStack=new Vector<UIColumn>();
        
        setContentView(view=new RelativeLayout(this));
        LinearLayout buttons=new LinearLayout(this);
        {
        	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        	lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        	view.addView(buttons,lp);
        	buttons.setId(746);
        }
        {
        	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        	lp.addRule(RelativeLayout.ABOVE,buttons.getId());
        	lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            view.addView(tweetView=new FrameLayout(this),lp);
        }
        
	    AccountHandler handler=new AccountHandler();
	    Column column;
	    handler.columns.add(column=new EveryColumn());
        {
        	UIColumnObserver observer=new UIColumnObserver(column,this);
        	Button button=new Button(this);
        	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        	button.setText("Home");
        	button.setOnClickListener(new ColumnButtonListener(observer,tweetView,this));
        	buttons.addView(button,lp);
        	
        	tweetView.addView(observer.view);  
        	columnStack.add(observer);
        }
	    handler.columns.add(column=(new MentionColumn("zacaj")));
        {
        	UIColumnObserver observer=new UIColumnObserver(column,this);
        	Button button=new Button(this);
        	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        	button.setText("Mentions");
        	button.setOnClickListener(new ColumnButtonListener(observer,tweetView,this));
        	buttons.addView(button,lp);
        }
        {
        	Button button=new Button(this);
        	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        	button.setText("New");
        	tweetBox=new UITweetBox(this,buttons,handler);
        	button.setOnClickListener(new ColumnButtonListener(tweetBox,tweetView,this));
        	//button.setGravity(Gravity.RIGHT);
        	buttons.addView(button,lp);
        }
        log("Initialising...");
		String path=Environment.getExternalStorageDirectory().toString()+"/Android/data/com.zision.styggdrasil/";
		log("Data folder: "+path);
	    File directory = new File(path);
	    directory.mkdirs();
	    try
		{
			BufferedReader in=new BufferedReader(new FileReader(path+"user.txt"));
		    String accessToken=in.readLine();
		    String accessTokenSecret=in.readLine();
		    String restUrl=in.readLine();
		    in.close();
		    
			handler.accessToken=accessToken;
			handler.accessTokenSecret=accessTokenSecret;
			handler.username="zacaj_";
			if(restUrl!=null)
				handler.restUrl=restUrl;
			handler.start();
		}
		catch (Exception ex)
		{
			log("Exception: "+ex.getLocalizedMessage());
			for (StackTraceElement ste : ex.getStackTrace())
				log(ste.toString());
		}
		
    }
	
	private void log(final String status)
	{
		Log.i("Twitter test", status);
	}
    
}