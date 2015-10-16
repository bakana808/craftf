package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.*;

import java.util.List;
import java.util.Set;

/**
 * @author octopod
 */
public interface Element {

	/**
	 * Returns true if:
	 * <ul>
	 *     <li>{@code getText()} returns {@code ""}</li>
	 *     <li>{@code getChildren()} returns an empty list</li>
	 * </ul>
	 * This means the TextElement represents nothing.
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Returns true if:
	 * <ul>
	 *     <li>{@code getText()} returns {@code ""}</li>
	 *     <li>There is only one element in {@code getChildren()}</li>
	 * </ul>
	 * This means that this TextElement can be replaced with the one child.
	 * @return
	 */
	boolean isSimplifiable();

	/**
	 * Returns true if:
	 * <ul>
	 *     <li>{@code getColor()} returns {@code TextColor.WHITE}</li>
	 *     <li>{@code getStyles()} returns an empty set</li>
	 *     <li>{@code getClickEvent()} returns {@code null}</li>
	 *     <li>{@code getHoverEvent()} returns {@code null}</li>
	 *     <li>{@code getChildren()} returns an empty list</li>
	 * </ul>
	 * This means that this TextElement represents text that is not formatted at all.
	 * @return
	 */
	boolean isPlain();

	/**
	 * Returns true if:
	 * <ul>
	 *     <li>{@code getText()} returns an empty string</li>
	 *     <li>{@code getColor()} returns a ChatColor other than {@code ChatColor.WHITE}</li>
	 *     <p>OR</p>
	 *     <li>{@code getStyles()} is not empty</li>
	 * </ul>
	 * This means that this TextElement represents text that has
	 * no text but has formats placed on it.
	 * @return
	 */
	boolean isOnlyFormats();

	String getText();

	ChatColor getColor();

	Set<ChatStyle> getStyles();

	TextMode getMode();

	ClickEventEntry getClickEvent();

	HoverEventEntry getHoverEvent();

	List<Element> getChildren();

	String toString(int indent);

	Element text(String text);

	Element color(ChatColor color);

	Element style(ChatStyle... styles);

	Element style(Set<ChatStyle> styles);

	Element mode(TextMode mode);

	Element click(ClickEvent event, Element value);

	Element click(ClickEventEntry entry);

	Element hover(HoverEvent event, Element value);

	Element hover(HoverEventEntry entry);

	Element attachAndGet(Element element);

	Element attach(Element... elements);

	Element attach(List<Element> elements);

	Element detach(Element... elements);

	Element detach(List<Element> elements);

	Element detachAll();
}
