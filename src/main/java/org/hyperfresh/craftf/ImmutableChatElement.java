package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.*;

import java.util.*;

/**
 * An immutable text element.
 *
 * @author octopod
 */
public class ImmutableChatElement extends AbstractImmutableElement {

	/**
	 * The text.
	 */
	private final String text;

	/**
	 * The color of the text.
	 */
	private final ChatColor color;

	/**
	 * The styles of this text.
	 */
	private final Set<ChatStyle> styles;

	/**
	 * The text mode which tells you how the text will be used.
	 */
	private final TextMode mode;

	/**
	 * The click event of this text.
	 */
	private final ClickEventEntry click;

	/**
	 * The hover event of this text.
	 */
	private final HoverEventEntry hover;

	/**
	 * Child CFText instances.
	 */
	private final List<Element> children;

	/**
	 * The amount of space, in pixels, that this text will take.
	 * If set to -1, there will be no margins.
	 */
	private final int margins;

	/**
	 * Empty ChatElement constructor.
	 * Use {@code CFChatElement.EMPTY} instead.
	 */
	private ImmutableChatElement(Element el) {
		this.text = el.getText();
		this.color = el.getColor();
		this.styles = Collections.unmodifiableSet(el.getStyles());
		this.mode = el.getMode();
		this.click = el.getClickEvent();
		this.hover = el.getHoverEvent();
		this.children = Collections.unmodifiableList(el.getChildren());
		this.margins = -1;
	}

	public static Element copyOf(Element el) {
		if(el instanceof ImmutableChatElement) {
			return el;
		} else {
			return new ImmutableChatElement(el);
		}
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public ChatColor getColor() {
		return color;
	}

	@Override
	public Set<ChatStyle> getStyles() {
		return styles;
	}

	@Override
	public TextMode getMode() {
		return mode;
	}

	@Override
	public ClickEventEntry getClickEvent() {
		return click;
	}

	@Override
	public HoverEventEntry getHoverEvent() {
		return hover;
	}

	@Override
	public List<Element> getChildren() {
		return children;
	}
}
