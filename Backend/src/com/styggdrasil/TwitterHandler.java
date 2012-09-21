package com.styggdrasil;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

//some redesign going to be needed to handle multiple accounts, but 
public class TwitterHandler {
	Vector<Column> columns;
	//id,item
	Map<Long,Item> items;
	
	TwitterStreamFactory streamFactory;
	AsyncTwitterFactory asyncFactory;
	TwitterFactory twitterFactory;
	
	public TwitterHandler()
	{
		columns=new Vector<Column>();
		items=new HashMap<Long,Item>();
	}
	
	//Configuration variables
	String accessToken;
	String accessTokenSecret;
	
	/**
	 * Starts the handler.  Make sure configure it first.
	 */
	public void start()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("1Ysjec2smtfSHfTaZeOAA")
		  .setOAuthConsumerSecret("fMzPJj4oFBgSlW1Ma2r79Y1kE0t7S7r1lvQXBnXSk")
		  .setOAuthAccessToken(accessToken)
		  .setOAuthAccessTokenSecret(accessTokenSecret);
		Configuration config=cb.build();
		streamFactory=new TwitterStreamFactory(config);
		asyncFactory=new AsyncTwitterFactory(config);
		twitterFactory=new TwitterFactory(config);

		updateHomeTimeline();
		
		UserStream stream=new UserStream(this);
		TwitterStream stream4j=streamFactory.getInstance();
		stream4j.addListener(stream);
		stream4j.user();
		
	}
	public void updateHomeTimeline() {
		final TwitterHandler handler=this;
		TwitterListener listener = new TwitterAdapter() {
	        @Override public void gotHomeTimeline(ResponseList<Status> statuses) {
	        	for(final Status status : statuses)
	        	{
	        		new Thread(new Runnable() {
	        			@Override 
						public void run()
						{
	        				handleItem(new Tweet(status, handler));//TODO make a factory(?) that will create Tweets/Retweets(/etc?) from Statuses
						}
	        		}).start();
	        	}
	        }
	    };
		
	    AsyncTwitter twitter=asyncFactory.getInstance();
	    twitter.addListener(listener);
	    twitter.getHomeTimeline();//TODO change to use Paging based on settings
	}
	public void handleItem(Item item)
	{
		if(items.put(item.time,item)==null)
			for(Column column : columns)
			{
				column.newItem(item);
			}
		else//   ?  Not sure exactly what should happen if we get the same tweet twice...
		{
			@SuppressWarnings("unused") int i=0;//just here to provide some code to break on
		}
	}
	/**
	 * Makes network requests, do not run on ui thread
	 * @param id
	 * @return
	 */
	public Tweet getTweet(long id)
	{
		Item item=null;
		if((item=items.get(id))!=null)
		{
			return (Tweet)item;
		}
		else
		{
			Twitter twitter=twitterFactory.getInstance();
			Tweet tweet=null;
			while(tweet==null)
			{
				try
				{
					Status status=twitter.showStatus(id);
					tweet=new Tweet(status,this);
				}
				catch (TwitterException e)
				{
					// TODO error handling?
					e.printStackTrace();
				}
			}
			return tweet;
		}
	}
}
