package org.hyperfresh.mc.liquidf;

import java.util.HashMap;
import java.util.Map;

public class LqVanillaFontInfo implements LqFontInfo
{
	/**
	* Map of all specific character widths other than the default.
	*/
	final private static Map<Character, Integer> widths = new HashMap<>();

	/**
	 * The default width. (most characters are 6px wide)
	 */
	final private static int default_width = 6;

	/**
	 * Static map filler.
	 */
	static
	{
		widths.put('*', 5);
		widths.put('>', 5);
		widths.put('<', 5);
		widths.put(',', 2);
		widths.put('!', 2);
		widths.put('{', 5);
		widths.put('}', 5);
		widths.put(')', 5);
		widths.put('(', 5);
		widths.put('\u00a7', 0);	//section sign; Minecraft's color code symbol.
		widths.put('[', 4);
		widths.put(']', 4);
		widths.put(':', 2);
		widths.put('\'', 3);
		widths.put('|', 2);
		widths.put('.', 2);
		widths.put('\u2019', 2);	//reverse quotation mark; 2px filler character since 1.7
		widths.put('`', 3);			//backtick; old filler character
		widths.put(' ', 4);
		widths.put('f', 5);
		widths.put('k', 5);
		widths.put('I', 4);
		widths.put('t', 4);
		widths.put('l', 3);
		widths.put('i', 2);
	}

	@Override
	public int getWidth(char c)
	{
		if(widths.containsKey(c))
		{
			return widths.get(c);
		}
		else
		{
			return default_width;
		}
	}
}
