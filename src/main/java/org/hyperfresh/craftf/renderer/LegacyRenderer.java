package org.hyperfresh.craftf.renderer;

import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.enums.ChatStyle;

/**
 * @author octopod
 *
 * Renders a CFText into a legacy string to be used in chat.
 * Since normal text doesn't support new features such as click or hover events,
 * those parts of the Text won't be used.
 */
public class LegacyRenderer implements Renderer<String> {

	@Override
	public String render(Element element) {

		StringBuilder sb = new StringBuilder();

		sb.append(element.getColor());

		for (ChatStyle format : element.getStyles()) {
			sb.append(format);
		}

		sb.append(element);

		for (Element child : element.getChildren()) {
			sb.append(render(child));
		}

		//to make sure styles are reset after this text
		sb.append(ChatStyle.RESET);

		return sb.toString();
	}
}
