package com.styggdrasil;

import java.util.Date;

import twitter4j.AsyncTwitter;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterListener;


public class Tweet extends Item
{
	public long	  id;
	public String text;
	public Tweet  inReplyTo;
	public long replyId;// -1 if not in reply to a tweet
	public User   user;
	public Status t4j;
	public Date   date;
	public String dateString;
	public AccountHandler handler;
	public final static int type=1;
	public boolean isFavorited;

	protected Tweet(Status status,AccountHandler _handler)//TODO if have multiple accounts, should tweets keep track of handler?  Separate tweets per account even if both follow, or...?
	{
		t4j = status;
		handler=_handler;
		id = status.getId();
		text = status.getText();
		user = handler.getUser(status.getUser());
		date=status.getCreatedAt();
		dateString=new Long(date.getHours()).toString()+":"+new Long(date.getMinutes()).toString();//TODO 12 hour time+padding
		time=date.getTime();
		replyId=status.getInReplyToStatusId();
		if(replyId==-1)
			inReplyTo=null;
		else
			inReplyTo=handler.getLoadedTweet(replyId);
		isFavorited=status.isFavorited();
	}
	public int getType()
	{
		return type;
	}
	public static Tweet createTweet(Status status,AccountHandler handler)
	{
		if(status.isRetweet())
			return new Retweet(status,handler);
		else
			return new Tweet(status,handler);
	}
	public void retweet()
	{
		AsyncTwitter twitter=handler.asyncFactory.getInstance();
		twitter.retweetStatus(id);
	}
	public void delete()
	{
		handler.asyncFactory.getInstance().destroyStatus(id);
	}
	public void favorite()
	{
		AsyncTwitter twitter=handler.asyncFactory.getInstance();//TODO are listeners added per instance?  If not, make one listener with everything implemented
		TwitterListener listener = new TwitterAdapter() {
	       @Override public void createdFavorite(Status status)
	       {
	    	   if(status.getId()==id)
	    		   isFavorited=true;
	    	   else
	    		   handler.getTweet(status).isFavorited=true;
	       }
	    };
		
	    twitter.addListener(listener);
	    twitter.createFavorite(id);
	}
	public void unfavorite()
	{
		AsyncTwitter twitter=handler.asyncFactory.getInstance();//TODO are listeners added per instance?  If not, make one listener with everything implemented
		TwitterListener listener = new TwitterAdapter() {
	       @Override public void destroyedFavorite(Status status)
	       {
	    	   if(status.getId()==id)
	    		   isFavorited=false;
	    	   else
	    		   handler.getTweet(status).isFavorited=false;
	       }
	    };
		
	    twitter.addListener(listener);
	    twitter.destroyFavorite(id);
	}
}
