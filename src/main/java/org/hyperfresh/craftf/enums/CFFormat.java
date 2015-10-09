package org.hyperfresh.craftf.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author octopod
 */
public enum CFFormat
{
	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINED('n'),
	ITALIC('o'),
	RESET('r');

	public static CFFormat[] NO_FORMATS = new CFFormat[0];

	Character character = null;

	private static Map<Character, CFFormat> map = new HashMap<>();

	static
	{
		for (CFFormat f : values()) map.put(f.character, f);
	}

	private CFFormat(char c)
	{
		character = c;
	}

	public char getChar()
	{
		return character;
	}

	static public CFFormat fromChar(char c)
	{
		return map.get(c);
	}

	public String toString()
	{
		return '\u00A7' + "" + character;
	}
}
