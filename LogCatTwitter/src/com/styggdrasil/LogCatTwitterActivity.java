package com.styggdrasil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
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
				Column column=new EveryColumn();
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