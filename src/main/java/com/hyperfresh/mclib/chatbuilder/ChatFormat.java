package com.octopod.util.minecraft.chat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Octopod - octopodsquad@gmail.com
 */
public enum ChatFormat
{
	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINED('n'),
	ITALIC('o'),
	RESET('r');

	Character character = null;

	private static Map<Character, ChatFormat> map = new HashMap<>();

	static
	{
		for(ChatFormat f: values()) map.put(f.character, f);
	}

	private ChatFormat(char c)
	{
		character = c;
	}

	public char getChar()
	{
		return character;
	}

	static public ChatFormat fromChar(char c)
	{
		return map.get(c);
	}

	public String toString()
	{
		return '\u00A7' + "" + character;
	}
}