package com.styggdrasil;

import twitter4j.Status;

public class Retweet extends Tweet
{
	public long nRetweet;
	public final static int type=2;
	public User retweetedBy;
	
	protected Retweet(Status status, AccountHandler _handler)
	{
		super(status, _handler);
		Status rt=status.getRetweetedStatus();
		//originalTweet=handler.getTweet(rt.getId());//TODO really need to figure out how to handle duplicate tweets...  currently just ignoring them
		retweetedBy=user;//Tweet() sets this to the retweeter, we change it below
		nRetweet=status.getRetweetCount();
		text=rt.getText();
		user=handler.getUser(rt.getUser());
		replyId=rt.getInReplyToStatusId();
		if(replyId==-1)
			inReplyTo=null;
		else
			inReplyTo=handler.getLoadedTweet(replyId);
	}
	public int getType()
	{
		return type;
	}
}
