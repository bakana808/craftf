package org.hyperfresh.craftf.parser;

import org.hyperfresh.craftf.SimpleChatElement;
import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.enums.ChatColor;
import org.hyperfresh.craftf.enums.ChatStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * A TextParser that parses vanilla minecraft text.
 * That is to say, it only parses the color and style codes.
 */
public class LegacyParser implements Parser<String> {

	// the section sign
	private static final char colorCode = '\u00A7';

	@Override
	public Element parse(String input) {

		Element root = new SimpleChatElement();
		StringBuilder sb = new StringBuilder();
		boolean nextIsColorCode = false;
		ChatColor lastColor = ChatColor.WHITE;
		List<ChatStyle> styles = new ArrayList<>();

		for (char c : input.toCharArray()) {
			if (c == colorCode) {
				nextIsColorCode = true;
				continue;
			}
			if (nextIsColorCode) {
				nextIsColorCode = false;
				ChatColor color = ChatColor.fromChar(c);
				ChatStyle style = ChatStyle.fromChar(c);
				if (color != null && style == null) {//This is a color
					//Push new element
					if (!sb.toString().equals("")) {
						root.attach(new SimpleChatElement(sb.toString(), lastColor, styles.toArray(new ChatStyle[styles.size()])));
					}
					//Reset variables
					sb = new StringBuilder();
					styles = new ArrayList<>();
					//Set new color
					lastColor = color;
				} else if (color == null && style != null) { //This is a format
					styles.add(style);
				}
				continue;
			}
			sb.append(c);
		}

		root.attach(new SimpleChatElement(sb.toString(), lastColor, styles.toArray(new ChatStyle[styles.size()])));

		return root;
	}
}
