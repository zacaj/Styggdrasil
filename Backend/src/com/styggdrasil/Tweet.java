package com.styggdrasil;

import java.util.Date;

import twitter4j.Status;


public class Tweet extends Item
{
	long	id;
	String	text;
	Tweet inReplyTo;
	User	user;
	Status	t4j;
	Date date;
	TwitterHandler handler;

	/**
	 * SHOULD NEVER BE CALLED ON UI THREAD, MAY NEED TO ACCESS INTERNET
	 * @param status
	 * @param _handler
	 */
	public Tweet(Status status,TwitterHandler _handler)
	{
		handler=_handler;
		t4j = status;
		id = status.getId();
		text = status.getText();
		user = new User(status.getUser());
		date=status.getCreatedAt();
		time=date.getTime();
		long replyId=status.getInReplyToStatusId();
		if(replyId==-1)
			inReplyTo=null;
		else
			inReplyTo=handler.getTweet(replyId);
	}
}
