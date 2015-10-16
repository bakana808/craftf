package org.hyperfresh.craftf.parser.craftf;

import org.hyperfresh.craftf.ChatElement;
import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.enums.ChatColor;
import org.hyperfresh.craftf.parser.MarkupParser;
import java.util.regex.MatchResult;

public class ColoredText implements ParserExtension {

	@Override
	public String getRegex() {
		return "(?i)(?<!/)\\&([0-9a-f])(.*?)(?:/\\&\\1|$|&r)";
	}

	@Override
	public void parse(MarkupParser parser, Element root, final MatchResult match) {

		char code = match.group(1).charAt(0);
		String after = match.group(2);

		if(after.equals("")) return;

		ChatColor color = ChatColor.fromChar(code);

		Element child = new ChatElement().color(color);
		parser.parse(child, after);
		root.attach(child);
	}
}
