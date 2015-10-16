package org.hyperfresh.craftf.parser.craftf;

import org.hyperfresh.craftf.ChatElement;
import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.enums.ChatStyle;
import org.hyperfresh.craftf.parser.MarkupParser;

import java.util.regex.MatchResult;

public class StyledText implements ParserExtension {

	@Override
	public String getRegex() {
		return "(?i)(?<!/)\\&([k-o])(.*?)(?:/\\&\\1|$|&r)";
	}

	@Override
	public void parse(MarkupParser parser, Element root, final MatchResult match) {

		String before = match.group(1);
		char code = match.group(2).charAt(0);
		String after = match.group(3);

		root.attach(new ChatElement(before));

		if(!after.equals("")) {
			Element child = new ChatElement().style(ChatStyle.fromChar(code));
			parser.parse(child, after);
			root.attach(child);
		}
	}
}
