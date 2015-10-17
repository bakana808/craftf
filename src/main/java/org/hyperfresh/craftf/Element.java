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
	 *     <li>{@code getColor()} returns {@code TextColor.WHITE}</li>
	 *     <li>{@code getStyles()} returns an empty set</li>
	 *     <li>{@code getClickEvent()} returns {@code null}</li>
	 *     <li>{@code getHoverEvent()} returns {@code null}</li>
	 * </ul>
	 * This means that this TextElement represents text that is not formatted at all.
	 * @return
	 */
	boolean isPlain();

	/**
	 * Returns true if:
	 * <ul>
	 *     <li>{@code getText()} returns {@code ""}</li>
	 *     <li>{@code isPlain()} returns {@code true}</li>
	 * </ul>
	 * This means the root ChatElement represents nothing.
	 * @return
	 */
	boolean isEmpty();

	/**
	 * Returns true if:
	 * <ul>
	 *     <li>{@code getText()} returns {@code ""}</li>
	 *     <li>{@code isPlain()} returns {@code false}</li>
	 * </ul>
	 * This means the root ChatElement represents an empty string
	 * with formats placed on it.
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
