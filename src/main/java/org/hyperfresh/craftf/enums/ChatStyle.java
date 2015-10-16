package org.hyperfresh.craftf.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author octopod
 */
public enum ChatStyle
{
	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINED('n'),
	ITALIC('o'),
	RESET('r');

	public static ChatStyle[] NORMAL = new ChatStyle[0];

	Character character = null;

	private static Map<Character, ChatStyle> map = new HashMap<>();

	static
	{
		for (ChatStyle f : values()) map.put(f.character, f);
	}

	private ChatStyle(char c)
	{
		character = c;
	}

	public char getChar()
	{
		return character;
	}

	static public ChatStyle fromChar(char c)
	{
		return map.get(c);
	}

	public String toString()
	{
		return '\u00A7' + "" + character;
	}
}
