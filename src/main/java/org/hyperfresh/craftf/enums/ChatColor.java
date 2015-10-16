package org.hyperfresh.craftf.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author octopod
 */
public enum ChatColor
{
	BLACK('0'),
	DARK_BLUE('1'),
	DARK_GREEN('2'),
	DARK_AQUA('3'),
	DARK_RED('4'),
	DARK_PURPLE('5'),
	GOLD('6'),
	GRAY('7'),
	DARK_GRAY('8'),
	BLUE('9'),
	GREEN('a'),
	AQUA('b'),
	RED('c'),
	LIGHT_PURPLE('d'),
	YELLOW('e'),
	WHITE('f');

	char character;

	private static Map<Character, ChatColor> map = new HashMap<>();

	static
	{
		for (ChatColor c : values()) map.put(c.character, c);
	}

	ChatColor(char c)
	{
		character = c;
	}

	public char getChar()
	{
		return character;
	}

	static public ChatColor fromChar(char c)
	{
		return map.get(c);
	}

	@Override
	public String toString()
	{
		return '\u00A7' + "" + character;
	}
}
