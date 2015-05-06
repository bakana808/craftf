package org.hyperfresh.mc.liquidf;

import org.hyperfresh.mc.liquidf.enums.LqColor;
import org.hyperfresh.mc.liquidf.enums.LqFormat;

/**
 * @author octopod
 */
public class LqRawText
{
	final String text;

	/**
	 * The color of the text.
	 */
	final LqColor color;

	/**
	 * The formats of this text.
	 */
	final LqFormat[] formats;

	public LqRawText(String text, LqColor color, LqFormat... formats)
	{
		this.text = text;
		this.color = color;
		this.formats = formats;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(color);

		for(LqFormat format: formats)
		{
			sb.append(format);
		}

		sb.append(text);

		//to make sure formats are reset after this text
		sb.append(LqFormat.RESET);

		return sb.toString();
	}
}
