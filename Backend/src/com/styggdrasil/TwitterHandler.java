package com.styggdrasil;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

//some redesign going to be needed to handle multiple accounts, but 
public class TwitterHandler {
	Vector<Column> columns;
	Map<Long,Item> items;
	
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
		
		UserStream stream=new UserStream(this);
		TwitterStream stream4j=new TwitterStreamFactory(cb.build()).getInstance();
		stream4j.addListener(stream);
		stream4j.user();
	}
	public void handleItem(Item item)
	{
		if(items.put(item.id,item)==null)
			for(Column column : columns)
			{
				column.newItem(item);
			}
		else//   ?  Not sure exactly what should happen if we get the same tweet twice...
		{
			int i=0;//just here to provide some code to break on
		}
	}
}
