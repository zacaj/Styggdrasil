package com.styggdrasil;

public class User {
	long id;
	String username,fullname;
	public User(twitter4j.User user) {
		id=user.getId();
		username=user.getScreenName();
		fullname=user.getName();
		t4j=user;
	}
	String getName()
	{
		return username;//later will change based on preferences
	}
	twitter4j.User t4j;
}
