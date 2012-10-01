package com.styggdrasil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;


public class AndroidUITwitterActivity extends Activity
{
	private RelativeLayout view;
	private FrameLayout tweetView;
	public AccountHandler handler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
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
	    handler.columns.add(new AndroidUIColumn(new EveryColumn(),this));
        {
        	Button button=new Button(this);
        	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        	button.setText("Home");
        	button.setOnClickListener(new ColumnButtonListener((AndroidUIColumn)handler.columns.lastElement(),tweetView));
        	buttons.addView(button,lp);
        }
	    handler.columns.add(new AndroidUIColumn(new MentionColumn("zacaj"),this));
        {
        	Button button=new Button(this);
        	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        	button.setText("Mentions");
        	button.setOnClickListener(new ColumnButtonListener((AndroidUIColumn)handler.columns.lastElement(),tweetView));
        	buttons.addView(button,lp);
        }
        {
        	tweetView.addView(((AndroidUIColumn)handler.columns.get(0)).view);        
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