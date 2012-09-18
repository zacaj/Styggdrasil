package com.styggdrasil;

import twitter4j.Status;


public class Tweet extends Item
{
	String	text;
	User	user;
	Status	t4j;

	public Tweet(Status status)
	{
		t4j = status;
		id = status.getId();
		text = status.getText();
		user = new User(status.getUser());
	}
}
