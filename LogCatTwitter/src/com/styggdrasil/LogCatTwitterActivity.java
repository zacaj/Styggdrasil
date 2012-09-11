package com.styggdrasil;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;

public class LogCatTwitterActivity extends Activity
{
	private TextView textView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(textView = new TextView(this));
        textView.append("Twitter test.\n");
        
        new Thread(grabTimelineRunnable, "Twitter Thread").start();
    }
	
	private void log(final String status)
	{
		Log.i("Twitter test", status);
		
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				textView.append(status + "\n");
			}
		});
	}
    
    private Runnable grabTimelineRunnable = new Runnable()
	{
		public void run()
		{
			try
			{
				log("Initialising...");
				
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
				  .setOAuthConsumerKey("")
				  .setOAuthConsumerSecret("")
				  .setOAuthAccessToken("")
				  .setOAuthAccessTokenSecret("");
				
				TwitterFactory factory = new TwitterFactory(cb.build());
				
				Twitter twitter = factory.getInstance();
				
				log("Making request...");
				
				ResponseList<Status> responses = twitter.getHomeTimeline();
				
				log("Received "+responses.size()+" responses!"); // Don't want to show too much amazement lol
				
				for (int i = 0; i < 100 && i < responses.size(); i++)
				{
					Status status = responses.get(i); 

					log(status.getUser().getName() + " (@" + status.getUser().getScreenName() + ")\nat " + DateFormat.format("yyyy-MM-dd h:mmaa", status.getCreatedAt()));
					log(status.getText());
				}
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