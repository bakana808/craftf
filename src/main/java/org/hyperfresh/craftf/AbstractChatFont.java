package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.ChatColor;

public abstract class AbstractChatFont implements ChatFont {

	@Override
	abstract public int getWidth(char c);

	/**
	 * Returns the width of the string inserted into the function.
	 * Note that bolded characters are 1 pixel wider than normal.
	 *
	 * @param string The string to use for calculation.
	 * @return The width of the setText inserted. (in pixels)
	 */
	@Override
	public final int getWidth(String string) {

		int width = 0;
		boolean isCode = false;
		boolean bolded = false;

		for (char character : string.toCharArray()) {
			if (character == '\u00a7') {
				isCode = true;
			} else {
				if (isCode) {
					if (bolded && ChatColor.fromChar(character) != null) {
						bolded = false;
					} else if (!bolded) {
						bolded = (character == 'l' || character == 'L');
					}
					isCode = false;
				} else {
					width += getWidth(character);
					if (bolded) { width += 1; }
				}
			}
		}
		return width;
	}

	@Override
	abstract public int getTabWidth();
}
