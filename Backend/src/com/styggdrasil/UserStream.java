package com.styggdrasil;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamAdapter;


public class UserStream extends UserStreamAdapter
{

	TwitterHandler	handler;

	public UserStream(TwitterHandler _handler)
	{
		handler = _handler;
	}

	public void onStatus(Status status)
	{
		handler.handleItem(new Tweet(status));
	}

	@Override public void onDeletionNotice(StatusDeletionNotice arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onScrubGeo(long arg0, long arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onTrackLimitationNotice(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onException(Exception arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onBlock(User arg0, User arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onDeletionNotice(long arg0, long arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onDirectMessage(DirectMessage arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onFavorite(User arg0, User arg1, Status arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onFollow(User arg0, User arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onFriendList(long[] arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onRetweet(User arg0, User arg1, Status arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUnblock(User arg0, User arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUnfavorite(User arg0, User arg1, Status arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListCreation(User arg0, UserList arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListDeletion(User arg0, UserList arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListMemberAddition(User arg0, User arg1,
			UserList arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListMemberDeletion(User arg0, User arg1,
			UserList arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListSubscription(User arg0, User arg1,
			UserList arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListUnsubscription(User arg0, User arg1,
			UserList arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserListUpdate(User arg0, UserList arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override public void onUserProfileUpdate(User arg0)
	{
		// TODO Auto-generated method stub

	}
}
