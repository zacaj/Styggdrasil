package com.styggdrasil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class LogCatTwitterActivity extends Activity
{
	private LinearLayout view;
	private FrameLayout tweetView;
	public TwitterHandler handler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(view=new LinearLayout(this));
        view.setOrientation(LinearLayout.VERTICAL);
        view.addView(tweetView=new FrameLayout(this));
        /*LinearLayout buttons=new LinearLayout(this);
        view.addView(buttons);
        for(int i=0;i<4;i++)
        {
        	Button button=new Button(this);
        	button.setText("Home");
        	button.setWidth(buttons.getWidth()/4);
        	button.setHeight(50);
        	buttons.addView(button);
        }*/

	    TwitterHandler handler=new TwitterHandler();
	    handler.columns.add(new AndroidUIColumn(new EveryColumn(),this));
	    handler.columns.add(new AndroidUIColumn(new MentionColumn("zacaj"),this));
	    
        tweetView.addView(((AndroidUIColumn)handler.columns.get(0)).view);        
        
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
		    in.close();
		    
			handler.accessToken=accessToken;
			handler.accessTokenSecret=accessTokenSecret;
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