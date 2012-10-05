package com.styggdrasil;

import android.app.Activity;

/**
 * Shows tweets containing the specified text
 * @author Zack
 *
 */
public class MentionColumn extends Column
{
	String text;
	
	public MentionColumn(String _text,Activity _activity)
	{
		super(_activity);
		text=_text;
	}
	
	@Override
	public boolean newItem(Item item)
	{
		if(item instanceof Tweet)
		{
			Tweet tweet=(Tweet)item;
			if(tweet.text.contains(text))
			{
				addItem(item);
				return true;
			}
		}
		return false;
	}

}
