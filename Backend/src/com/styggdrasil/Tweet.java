package com.styggdrasil;

import java.util.Date;

import twitter4j.Status;


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
}
