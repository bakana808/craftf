package org.hyperfresh.mc.liquidf;

/**
 * Various information about a Minecraft font.
 *
 * @author octopod
 */
public interface LqFontInfo
{
	/**
	 * Gets the width, in pixels, of a character using this font.
	 *
	 * @param c the character
	 * @return the pixel width of this character
	 */
	public int getWidth(char c);
}
