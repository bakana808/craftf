package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.*;

/**
 * A text element that supports text modes, colors, formats, and click/hover events.
 *
 * @author octopod
 */
public class CFTextFancy extends CFText
{
	/**
	 * The text mode which tells you how the text will be used.
	 */
	final CFTextMode mode;

	/**
	 * The click event of this text.
	 */
	final CFClickEvent click;

	/**
	 * The hover event of this text.
	 */
	final CFHoverEvent hover;

	/**
	 * The amount of space, in pixels, that this text will take.
	 * If set to -1, there will be no margins.
	 */
	final int margins;

	public CFTextFancy(String text, CFColor color, CFFormat[] formats,
					   CFClickEvent click, CFText clickValue,
					   CFHoverEvent hover, CFText hoverValue)
	{
		super(text, color, formats);
		this.mode = CFTextMode.TEXT;
		this.click = click;
		this.hover = hover;
		this.margins = -1;
	}
}
