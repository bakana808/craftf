package org.hyperfresh.mc.liquidf;

import org.hyperfresh.mc.liquidf.enums.*;

/**
 * A text element that supports text modes, colors, formats, and click/hover events.
 *
 * @author octopod
 */
public class LqTextFancy extends LqText
{
	/**
	 * The text mode which tells you how the text will be used.
	 */
	final LqTextMode mode;

	/**
	 * The click event of this text.
	 */
	final LqClickEvent click;

	/**
	 * The hover event of this text.
	 */
	final LqHoverEvent hover;

	/**
	 * The amount of space, in pixels, that this text will take.
	 * If set to -1, there will be no margins.
	 */
	final int margins;

	public LqTextFancy(String text, LqColor color, LqFormat[] formats,
					   LqClickEvent click, LqText clickValue,
					   LqHoverEvent hover, LqText hoverValue)
	{
		super(text, color, formats);
		this.mode = LqTextMode.TEXT;
		this.click = click;
		this.hover = hover;
		this.margins = -1;
	}
}
