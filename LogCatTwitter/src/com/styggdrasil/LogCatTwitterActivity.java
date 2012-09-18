package com.styggdrasil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class LogCatTwitterActivity extends Activity
{
	private LinearLayout view;
	private FrameLayout tweetView;
	private ScrollView scrollView;
	private LinearLayout tweetList;
	private LogCatTwitterActivity lgta;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	lgta=this;
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
        
        tweetView.addView(scrollView = new ScrollView(this));
    
        scrollView.addView(tweetList=new LinearLayout(this));
        tweetList.setOrientation(LinearLayout.VERTICAL);
        
        
        new Thread(grabTimelineRunnable, "Twitter Thread").start();
    }
	
	private void log(final String status)
	{
		Log.i("Twitter test", status);
	}
    
    private Runnable grabTimelineRunnable = new Runnable()
	{
		public void run()
		{
			try
			{
				log("Initialising...");
				String path=Environment.getExternalStorageDirectory().toString()+"/Android/data/com.zision.styggdrasil/";
				log("Data folder: "+path);
			    File directory = new File(path);
			    directory.mkdirs();
			    BufferedReader in=new BufferedReader(new FileReader(path+"user.txt"));
			    String accessToken=in.readLine();
			    String accessTokenSecret=in.readLine();
			    in.close();
			    
			    TwitterHandler handler=new TwitterHandler();
				handler.accessToken=accessToken;
				handler.accessTokenSecret=accessTokenSecret;
				Column column=new EveryColumn() {
					@Override public void newItem(Item item)
					{
						super.newItem(item);
						if (item instanceof Tweet)
						{
							final Tweet tweet = (Tweet) item;
							runOnUiThread(new Runnable()
							{
								@Override
								public void run()
								{
									TextView text=new TextView(lgta);
									text.setText(tweet.user.getName() + ": " + tweet.text);
									tweetList.addView(text,0);//TODO not necessarally at the top, need to find correct spot
								}
							});
						}
					}
				};
				handler.columns.add(column);
				handler.start();
			}
			catch (Exception ex)
			{
				log("Exception: "+ex.getLocalizedMessage());
				for (StackTraceElement ste : ex.getStackTrace())
					log(ste.toString());
			}
		}
	};
}