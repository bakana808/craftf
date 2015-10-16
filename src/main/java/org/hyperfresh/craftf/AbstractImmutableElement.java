package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.*;

import java.util.List;
import java.util.Set;

public abstract class AbstractImmutableElement extends AbstractElement {

	private UnsupportedOperationException unsupported() {
		return new UnsupportedOperationException(
			"This ChatElement does not support this operation."
		);
	}

	@Override
	public final Element text(String text) { throw unsupported();}

	@Override
	public final Element color(ChatColor color) { throw unsupported();}

	@Override
	public final Element style(ChatStyle... styles) { throw unsupported();}

	@Override
	public final Element style(Set<ChatStyle> styles) { throw unsupported();}

	@Override
	public final Element mode(TextMode mode) { throw unsupported();}

	@Override
	public final Element click(ClickEventEntry entry) { throw unsupported();}

	@Override
	public final Element click(ClickEvent event, Element value) { throw unsupported();}

	@Override
	public final Element hover(HoverEventEntry entry) { throw unsupported();}

	@Override
	public final Element hover(HoverEvent event, Element value) { throw unsupported();}

	@Override
	public final Element attachAndGet(Element element) { throw unsupported();}

	@Override
	public final Element attach(Element... elements) { throw unsupported();}

	@Override
	public final Element attach(List<Element> elements) { throw unsupported();}

	@Override
	public final Element detach(Element... elements) { throw unsupported();}

	@Override
	public final Element detach(List<Element> elements) { throw unsupported();}

	@Override
	public final Element detachAll() { throw unsupported();}
}
