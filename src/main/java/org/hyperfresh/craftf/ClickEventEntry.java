package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.ClickEvent;

public class ClickEventEntry {

	final ClickEvent event;
	final Element text;

	public ClickEventEntry(ClickEvent event, Element text)
	{
		this.event = event;
		this.text = text;
	}

	public ClickEvent getEvent() {
		return event;
	}

	public Element getValue() {
		return text;
	}
}
