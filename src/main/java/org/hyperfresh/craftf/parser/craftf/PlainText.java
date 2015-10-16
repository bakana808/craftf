package org.hyperfresh.craftf.parser.craftf;

import org.hyperfresh.craftf.ChatElement;
import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.parser.MarkupParser;

import java.util.regex.MatchResult;

public class PlainText implements ParserExtension {

	@Override
	public String getRegex() {
		return "^([^&]+)";
	}

	@Override
	public void parse(MarkupParser parser, Element root, MatchResult match) {
		root.attach(new ChatElement(match.group()));
	}
}
