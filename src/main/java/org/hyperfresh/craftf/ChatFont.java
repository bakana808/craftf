package org.hyperfresh.craftf;

/**
 * Various information about a Minecraft font.
 *
 * @author octopod
 */
public interface ChatFont
{
	/**
	 * Gets the width, in pixels, of a character using this font.
	 *
	 * @param c the character
	 * @return the pixel width of this character
	 */
	int getWidth(char c);

	int getWidth(String string);

	/**
	 * The amount of space, in pixels, that a full tab will take.
	 * Most of the time, this should be a multiple of the width a normal space is.
	 *
	 * @return the width of a tab
	 */
	int getTabWidth();
}
