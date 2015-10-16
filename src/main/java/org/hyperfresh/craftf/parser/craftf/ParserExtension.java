package org.hyperfresh.craftf.parser.craftf;

import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.parser.MarkupParser;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

public interface ParserExtension {

	String getRegex();

	void parse(MarkupParser parser, Element root, final MatchResult match);
}
