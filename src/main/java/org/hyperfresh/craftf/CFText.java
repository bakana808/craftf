package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.CFColor;
import org.hyperfresh.craftf.enums.CFFormat;

/**
 * A text element that supports only colors and formats.
 *
 * @author octopod
 */
public class CFText
{
	final public static CFText BLANK = new CFText("", CFColor.WHITE, CFFormat.NO_FORMATS);

	/**
	 * The text.
	 */
	final String text;

	/**
	 * The color of the text.
	 */
	final CFColor color;

	/**
	 * The formats of this text.
	 */
	final CFFormat[] formats;

	/**
	 * The full constructor for CFLegacyText
	 */
	public CFText(String text, CFColor color, CFFormat... formats)
	{
		this.text = text;
		this.color = color;
		this.formats = formats;
	}

	public String getText()
	{
		return text;
	}

	public CFColor getColor()
	{
		return color;
	}

	public CFFormat[] getFormats()
	{
		return formats;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(color);

		for (CFFormat format : formats)
		{
			sb.append(format);
		}

		sb.append(text);

		//to make sure formats are reset after this text
		sb.append(CFFormat.RESET);

		return sb.toString();
	}
}
