package org.hyperfresh.mc.liquidf;

import org.hyperfresh.mc.liquidf.enums.LqClickEvent;
import org.hyperfresh.mc.liquidf.enums.LqColor;
import org.hyperfresh.mc.liquidf.enums.LqFormat;
import org.hyperfresh.mc.liquidf.enums.LqHoverEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds up a chat message to be output to JSON or legacy.
 *
 * @author octopod
 */
public class LqMessage
{
	private final List<LqText> textList = new ArrayList<>();

	LqColor 		color = LqColor.WHITE;

	LqFormat[] 		formats = LqFormat.NO_FORMATS;

	LqClickEvent 	click = null;

	LqText			clickValue = LqText.BLANK;

	LqHoverEvent	hover = null;

	LqText			hoverValue = LqText.BLANK;

	/**
	 * Primes a color to be used in the next added text.
	 *
	 * @param color
	 * @return
	 */
	public LqMessage color(LqColor color)
	{
		this.color = color;
		return this;
	}

	/**
	 * Primes formats to be used in the next added text.
	 *
	 * @param formats
	 * @return
	 */
	public LqMessage format(LqFormat... formats)
	{
		this.formats = formats;
		return this;
	}

	public LqMessage text(String text)
	{
		if(click == null && hover == null)
		{
			//use LqText instead
			textList.add(new LqText(text, color, formats));
		}
		else
		{
			//use LqTextFancy
			textList.add(new LqTextFancy(text, color, formats,
					click, clickValue, hover, hoverValue));
		}
		return this;
	}

	/**
	 * Returns the legacy version of this message.
	 *
	 * @return the legacy string
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		textList.stream().forEach(sb::append);

		return sb.toString();
	}

	/**
	 * Returns the JSON version of this message.
	 *
	 * @return the JSON string
	 */
	public String toJSONString()
	{

	}
}
