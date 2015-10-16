package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.HoverEvent;

public class HoverEventEntry {

	final HoverEvent event;
	final Element text;

	public HoverEventEntry(HoverEvent event, Element text)
	{
		this.event = event;
		this.text = text;
	}

	public HoverEvent getEvent() {
		return event;
	}

	public Element getValue() {
		return text;
	}
}
