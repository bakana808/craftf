package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.CFClickEvent;
import org.hyperfresh.craftf.enums.CFColor;
import org.hyperfresh.craftf.enums.CFFormat;
import org.hyperfresh.craftf.enums.CFHoverEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds up a chat message to be output to JSON or legacy.
 *
 * @author octopod
 */
public class CFMessage
{
	private final List<CFText> textList = new ArrayList<>();

	CFColor 		color = CFColor.WHITE;

	CFFormat[] 		formats = CFFormat.NO_FORMATS;

	CFClickEvent 	click = null;

	CFText			clickValue = CFText.BLANK;

	CFHoverEvent	hover = null;

	CFText			hoverValue = CFText.BLANK;

	/**
	 * Primes a color to be used in the next added text.
	 *
	 * @param color
	 * @return
	 */
	public CFMessage color(CFColor color)
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
	public CFMessage format(CFFormat... formats)
	{
		this.formats = formats;
		return this;
	}

	public CFMessage text(String text)
	{
		if(click == null && hover == null)
		{
			//use CFText instead
			textList.add(new CFText(text, color, formats));
		}
		else
		{
			//use CFTextFancy
			textList.add(new CFTextFancy(text, color, formats,
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
