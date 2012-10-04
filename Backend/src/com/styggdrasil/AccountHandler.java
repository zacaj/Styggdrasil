package com.styggdrasil;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

//some redesign going to be needed to handle multiple accounts, but 
public class AccountHandler {
	Vector<Column> columns;
	//id,item
	Map<Long,Item> items;
	
	TwitterStreamFactory streamFactory;
	AsyncTwitterFactory asyncFactory;
	TwitterFactory twitterFactory;
	
	public AccountHandler()
	{
		columns=new Vector<Column>();
		items=new HashMap<Long,Item>();
		restUrl=null;
	}
	
	public AccountHandler(String accessToken, String accessTokenSecret)
	{
		columns=new Vector<Column>();
		items=new HashMap<Long,Item>();
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
		restUrl=null;
	}
	
	//Configuration variables
	String username;//TODO actually set this internally
	String accessToken;
	String accessTokenSecret;
	String restUrl;
	
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
		if(restUrl!=null)
			cb.setRestBaseURL(restUrl);
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
	
	public void sendTweet(String text)
	{
		asyncFactory.getInstance().updateStatus(text);
	}
	public void sendTweet(String text,long inReplyTo)
	{
		StatusUpdate update=new StatusUpdate(text);
		update.setInReplyToStatusId(inReplyTo);
		asyncFactory.getInstance().updateStatus(update);
	}
	
	public void updateHomeTimeline() {
		final AccountHandler handler=this;
		TwitterListener listener = new TwitterAdapter() {
	        @Override public void gotHomeTimeline(ResponseList<Status> statuses) {
	        	for(final Status status : statuses)
	        	{
	        		new Thread(new Runnable() {
	        			@Override 
						public void run()
						{
	        				handleItem(Tweet.createTweet(status, handler));//TODO make a factory(?) that will create Tweets/Retweets(/etc?) from Statuses
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
		Item item=getLoadedTweet(id);
		if(item!=null)
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
					tweet=Tweet.createTweet(status,this);
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
	
	public Tweet getLoadedTweet(long id)//TODO add a LoadingTweet class that will automatically load "other" tweets
	{
		Item item=null;
		if((item=items.get(id))!=null)
		{
			return (Tweet)item;
		}
		else 
			return null;
	}

	public Tweet getTweet(Status status)
	{
		Item item=getLoadedTweet(status.getId());
		if(item!=null)
		{
			return (Tweet)item;
		}
		else
			return Tweet.createTweet(status, this);
	}
	
	private Map<Long,User> users=new HashMap<Long,User>();
	public User getUser(long id)
	{
		User user=users.get(id);
		if(user==null)
		{
			try
			{
				return getUser(twitterFactory.getInstance().showUser(id));//TODO put user loading in thread, some kind of autogenned temp User returned instantly?
			}
			catch (TwitterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	public User getUser(twitter4j.User t4juser)
	{
		User user=users.get(t4juser.getId());
		if(user==null)
			return new User(t4juser);
		else
			return user;
	}
	
	/**
	 * Adds a Column to the handler
	 * @param c Column to add
	 */
	public void addColumn(Column c)
	{
		columns.add(c);
	}
	
	/**
	 * Removes a Column from the handler
	 * @param c Column to remove
	 */
	public void removeColumn(Column c)
	{
		columns.remove(c);
	}

}
