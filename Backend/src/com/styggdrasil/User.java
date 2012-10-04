package com.styggdrasil;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;

public class User
{
	public long   id;
	public String username, fullname;
	public Bitmap avatar;
	public URL avatarUrl;
	
	public User(twitter4j.User user)
	{
		id = user.getId();
		username = user.getScreenName();
		fullname = user.getName();
		t4j = user;
		avatarUrl=user.getProfileImageURL();
		AvatarCache.getAvatarForUser(this);
	}

	String getName()
	{
		return username;// later will change based on preferences
	}

	twitter4j.User	t4j;
}
