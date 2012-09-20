package com.styggdrasil;

import java.util.Date;

import twitter4j.Status;


public class Tweet extends Item
{
	long	id;
	String	text;
	User	user;
	Status	t4j;
	Date date;

	public Tweet(Status status)
	{
		t4j = status;
		id = status.getId();
		text = status.getText();
		user = new User(status.getUser());
		date=status.getCreatedAt();
		time=date.getTime();
	}
}
