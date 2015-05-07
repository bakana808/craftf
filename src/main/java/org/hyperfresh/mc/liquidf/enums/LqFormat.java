package org.hyperfresh.mc.liquidf.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author octopod
 */
public enum LqFormat
{
	OBFUSCATED('k'),
	BOLD('l'),
	STRIKETHROUGH('m'),
	UNDERLINED('n'),
	ITALIC('o'),
	RESET('r');

	public static LqFormat[] NO_FORMATS = new LqFormat[0];

	Character character = null;

	private static Map<Character, LqFormat> map = new HashMap<>();

	static
	{
		for(LqFormat f: values()) map.put(f.character, f);
	}

	private LqFormat(char c)
	{
		character = c;
	}

	public char getChar()
	{
		return character;
	}

	static public LqFormat fromChar(char c)
	{
		return map.get(c);
	}

	public String toString()
	{
		return '\u00A7' + "" + character;
	}
}
