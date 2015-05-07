package org.hyperfresh.mc.liquidf;

import org.hyperfresh.mc.liquidf.enums.LqColor;
import org.hyperfresh.mc.liquidf.enums.LqFormat;

/**
 * A text element that supports only colors and formats.
 *
 * @author octopod
 */
public class LqText
{
	/**
	 * The text.
	 */
	final String text;

	/**
	 * The color of the text.
	 */
	final LqColor color;

	/**
	 * The formats of this text.
	 */
	final LqFormat[] formats;

	/**
	 * The full constructor for LqLegacyText
	 */
	public LqText(String text, LqColor color, LqFormat... formats)
	{
		this.text = text;
		this.color = color;
		this.formats = formats;
	}

	public String getText()
	{
		return text;
	}

	public LqColor getColor()
	{
		return color;
	}

	public LqFormat[] getFormats()
	{
		return formats;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(color);

		for (LqFormat format : formats)
		{
			sb.append(format);
		}

		sb.append(text);

		//to make sure formats are reset after this text
		sb.append(LqFormat.RESET);

		return sb.toString();
	}
}
