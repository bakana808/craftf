package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.ClickEvent;
import org.hyperfresh.craftf.enums.HoverEvent;
import org.hyperfresh.craftf.enums.ChatColor;
import org.hyperfresh.craftf.enums.TextMode;

import java.util.List;

public abstract class AbstractElement implements Element {

	@Override
	public boolean isEmpty() {
		return getText().equals("") && getChildren().isEmpty();
	}

	@Override
	public boolean isSimplifiable() {
		return getText().equals("") && getChildren().size() == 1;
	}

	@Override
	public boolean isPlain() {
		return getColor() == ChatColor.WHITE &&
			getStyles().isEmpty() && getChildren().isEmpty() &&
			getClickEvent() == null && getHoverEvent() == null;
	}

	@Override
	public boolean isOnlyFormats() {
		return getText().equals("") &&
			(getColor() != ChatColor.WHITE || getStyles().size() > 0);
	}

	public String toString() {
		return toString(0);
	}

	public String toString(int indent) {
		String ind = new String(new char[indent]).replace("\0", "    ");

		StringBuilder sb = new StringBuilder();
		sb.append(ind).append("text: \"").append(CraftF.fixText(getText())).append("\"\n");
		sb.append(ind).append("color: ").append(getColor().name()).append("\n");
		sb.append(ind).append("styles: " + getStyles() + "\n");
		sb.append(ind).append("children: [");
		for(Element child: getChildren()) {
			sb.append('\n').append(ind).append("    {\n");
			sb.append(child.toString(indent + 1));
			sb.append(ind).append("\n").append(ind).append("    }\n");
		}
		sb.append(']');
		return sb.toString();
	}

	@Override
	public Element mode(TextMode mode) {
		return this;
	}

	@Override
	public Element click(ClickEvent event, Element value) {
		return this;
	}

	@Override
	public Element hover(HoverEvent event, Element value) {
		return this;
	}

	@Override
	public Element attachAndGet(Element element) {
		return element;
	}

	@Override
	public Element attach(Element... elements) {
		return this;
	}

	@Override
	public Element attach(List<Element> elements) {
		return this;
	}

	@Override
	public Element detach(Element... elements) {
		return this;
	}

	@Override
	public Element detach(List<Element> elements) {
		return this;
	}

	@Override
	public Element detachAll() {
		return this;
	}
}
