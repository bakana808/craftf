package org.hyperfresh.craftf.parser;

import org.hyperfresh.craftf.CraftF;
import org.hyperfresh.craftf.ChatElement;
import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.parser.craftf.ColoredText;
import org.hyperfresh.craftf.parser.craftf.ParserExtension;
import org.hyperfresh.craftf.parser.craftf.PlainText;
import org.hyperfresh.craftf.parser.craftf.StyledText;

import java.util.Scanner;
import java.util.regex.MatchResult;


/**
 * CraftF's markup, which is designed to be an extension of Minecraft's "markup"
 * to include additional features including the chat's new JSON features and
 * other features such as pseudo-tabs.
 */
public class MarkupParser implements Parser<String> {

	private static final char colorCode = '&';

	private final ParserExtension[] extensions = new ParserExtension[] {
		new PlainText(), new ColoredText(), new StyledText()
	};

	/**
	 * Try to use {@code Scanner.findWithinHorizon()} and return {@code Scanner.match()},
	 * but return {@code null} if the pattern failed.
	 * @param scanner
	 * @param pattern
	 * @return
	 */
	private MatchResult nextMatch(Scanner scanner, String pattern) {
		try {
			scanner.findWithinHorizon(pattern, 0);
			return scanner.match();
		} catch (IllegalStateException e) {
			return null;
		}
	}
	public void parse(Element parent, String input) {

		Scanner src = new Scanner(input);

		loop: while(src.hasNext()) {
			for(ParserExtension ext: extensions) {
				MatchResult match = nextMatch(src, ext.getRegex());
				if(match != null) {
					ext.parse(this, parent, match);
					continue loop;
				}
			}
			System.out.println("No parsers handled the Scanner.");
			break;
		}
	}

	@Override
	public Element parse(String input) {

		Element root = new ChatElement();

		parse(root, input);

		root = CraftF.simplify(root);

		System.out.println("ELEMENT: \n" + root);

		return root;

		//TextElement tb = new TextElement();
		//StringBuilder sb = new StringBuilder();
		//boolean nextIsColorCode = false;
		//TextColor lastColor = TextColor.WHITE;
		//List<TextStyle> styles = new ArrayList<>();
		//
		//for (char c : input.toCharArray()) {
		//	if (c == colorCode) {
		//		nextIsColorCode = true;
		//		continue;
		//	}
		//	if (nextIsColorCode) {
		//		nextIsColorCode = false;
		//		TextColor color = TextColor.fromChar(c);
		//		TextStyle style = TextStyle.fromChar(c);
		//		if (color != null && style == null) {//This is a color
		//			//Push new element
		//			if (!sb.toString().equals("")) {
		//				tb.attach(new CFSimpleText(sb.toString(), lastColor, styles.toArray(new TextStyle[styles.size()])));
		//			}
		//			//Reset variables
		//			sb = new StringBuilder();
		//			styles = new ArrayList<>();
		//			//Set new color
		//			lastColor = color;
		//		} else if (color == null && style != null) { //This is a format
		//			styles.add(style);
		//		}
		//		continue;
		//	}
		//	sb.append(c);
		//}
		//
		//tb.attach(new CFSimpleText(sb.toString(), lastColor, styles.toArray(new TextStyle[styles.size()])));
		//
		//return tb.create();
	}
}
