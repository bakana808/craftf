package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.*;

import java.util.*;

public class SimpleChatElement extends AbstractElement {

	/**
	 * An instance of CFText that is empty.
	 */
	final public static SimpleChatElement DUMMY = new SimpleChatElement("");

	/**
	 * The text.
	 */
	private String text = "";

	/**
	 * The color of the text.
	 */
	private ChatColor color = ChatColor.WHITE;

	/**
	 * The styles of this text.
	 */
	private Set<ChatStyle> styles = new HashSet<>();

	public SimpleChatElement() {}

	public SimpleChatElement(String text) {
		this.text = text;
	}

	public SimpleChatElement(String text, ChatColor color, ChatStyle... styles) {
		this(text, color, new HashSet<>(Arrays.asList(styles)));
	}

	public SimpleChatElement(String text, ChatColor color, Set<ChatStyle> styles) {
		this.text = text;
		this.color = color;
		this.styles = styles;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Element text(String text) {
		this.text = text;
		return this;
	}

	@Override
	public ChatColor getColor() {
		return color;
	}

	/**
	 * Sets the color of the next TextElement.
	 * @param color
	 * @return
	 */
	@Override
	public Element color(ChatColor color) {
		this.color = color;
		return this;
	}

	@Override
	public Set<ChatStyle> getStyles() {
		return styles;
	}

	@Override
	public Element style(ChatStyle... styles) {
		for(ChatStyle style: styles) {
			if(!this.styles.contains(style)) {
				this.styles.add(style);
			}
		}
		return this;
	}

	@Override
	public TextMode getMode() {
		return TextMode.TEXT;
	}

	@Override
	public Element style(Set<ChatStyle> styles) {
		this.styles.addAll(styles);
		return this;
	}

	@Override
	public ClickEventEntry getClickEvent() {
		return null;
	}

	@Override
	public Element click(ClickEventEntry entry) {
		throw new UnsupportedOperationException(
			"CFSimpleChatElement does not support click events."
		);
	}

	@Override
	public HoverEventEntry getHoverEvent() {
		return null;
	}

	@Override
	public Element hover(HoverEventEntry entry) {
		throw new UnsupportedOperationException(
			"CFSimpleChatElement does not support hover events."
		);
	}

	@Override
	public List<Element> getChildren() {
		return Collections.emptyList();
	}

}
