package com.styggdrasil;

public class MentionColumn extends Column
{
	String username;
	
	public MentionColumn(String name)
	{
		super();
		username=name;
	}
	
	@Override boolean newItem(Item item)
	{
		if(item instanceof Tweet)
		{
			Tweet tweet=(Tweet)item;
			if(tweet.text.contains(username))
			{
				contents.add(item);
				return true;
			}
		}
		return false;
	}

}