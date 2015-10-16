package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.*;

import java.util.*;

/**
 * An immutable text element.
 *
 * @author octopod
 */
public class ChatElement extends AbstractElement {

	/**
	 * An instance of CFText that is empty.
	 */
	final public static Element DUMMY = ImmutableChatElement.copyOf(new ChatElement(""));

	final public static Element NEWLINE = ImmutableChatElement.copyOf(new ChatElement("\n"));

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

	/**
	 * The text mode which tells you how the text will be used.
	 */
	private TextMode mode = TextMode.TEXT;

	/**
	 * The click event of this text.
	 */
	private ClickEventEntry click = null;

	/**
	 * The hover event of this text.
	 */
	private HoverEventEntry hover = null;

	/**
	 * Child CFText instances.
	 */
	private List<Element> children = new ArrayList<>();

	/**
	 * The amount of space, in pixels, that this text will take.
	 * If set to -1, there will be no margins.
	 */
	private int margins = -1;

	/**
	 * Empty ChatElement constructor.
	 * Use {@code ChatElement.EMPTY} instead.
	 */
	private ChatElement() {}

	/**
	 * Copy constructor.
	 * @param other
	 */
	public ChatElement(Element other) {
		this(other.getText(), other.getColor(), other.getStyles(), other.getMode(),
			other.getClickEvent(), other.getHoverEvent(), other.getChildren());
	}

	public ChatElement(String text, ChatColor color, ChatStyle... styles) {
		this(text, color, new HashSet<>(Arrays.asList(styles)));
	}

	public ChatElement(String text) {
		this.text = text;
	}

	public ChatElement(String text, ChatColor color, Set<ChatStyle> styles) {
		this.text = text;
		this.color = color;
		this.styles = styles;
	}

	public ChatElement(Element... children) {
		this.children = new ArrayList<>(Arrays.asList(children));
	}

	public ChatElement(String text, ChatColor color, Set<ChatStyle> styles,
					   ClickEventEntry click, HoverEventEntry hover)
	{
		this(text, color, styles);
		this.click = click;
		this.hover = hover;
	}

	/**
	 * The full constructor for CFText.
	 */
	public ChatElement(String text, ChatColor color, Set<ChatStyle> styles,
					   TextMode mode,
					   ClickEventEntry click, HoverEventEntry hover,
					   List<Element> children)
	{
		this(text, color, styles);
		this.mode = mode;
		this.click = click;
		this.hover = hover;
		this.children = children;
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
	public Element style(Set<ChatStyle> styles) {
		this.styles.addAll(styles);
		return this;
	}

	@Override
	public TextMode getMode() {
		return mode;
	}

	@Override
	public Element mode(TextMode mode) {
		this.mode = mode;
		return this;
	}

	@Override
	public ClickEventEntry getClickEvent() {
		return click;
	}

	@Override
	public Element click(ClickEvent event, Element value) {
		this.click = new ClickEventEntry(event, value);
		return this;
	}

	@Override
	public Element click(ClickEventEntry entry) {
		this.click = entry;
		return this;
	}

	@Override
	public HoverEventEntry getHoverEvent() {
		return hover;
	}

	@Override
	public Element hover(HoverEvent event, Element value) {
		this.hover = new HoverEventEntry(event, value);
		return this;
	}

	@Override
	public Element hover(HoverEventEntry entry) {
		this.hover = entry;
		return this;
	}

	@Override
	public List<Element> getChildren() {
		return children;
	}

	@Override
	public Element attachAndGet(Element element) {
		attach(element);
		return element;
	}

	/**
	 * Attach a child to the next TextElement.
	 * @param children
	 * @return
	 */
	@Override
	public Element attach(Element... children) {
		Collections.addAll(this.children, children);
		return this;
	}

	@Override
	public Element attach(List<Element> builders) {
		this.children.addAll(builders);
		return this;
	}

	@Override
	public Element detach(Element... elements) {
		for(Element el: elements) {
			this.children.remove(el);
		}
		return this;
	}

	@Override
	public Element detach(List<Element> elements) {
		this.children.removeAll(elements);
		return this;
	}

	/**
	 * Detach all children from the next TextElement.
	 * @return
	 */
	@Override
	public Element detachAll() {
		children = new ArrayList<>();
		return this;
	}
}
