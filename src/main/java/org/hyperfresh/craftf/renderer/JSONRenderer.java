package org.hyperfresh.craftf.renderer;

import org.hyperfresh.craftf.CraftF;
import org.hyperfresh.craftf.Element;
import org.hyperfresh.craftf.enums.ChatColor;
import org.hyperfresh.craftf.enums.TextMode;
import org.hyperfresh.craftf.enums.ChatStyle;

import java.util.List;

public class JSONRenderer implements Renderer<String> {

	@Override
	public String render(Element el) {

		StringBuilder sb = new StringBuilder();

		String text = CraftF.fixText(el.getText());

		//if(el.getText().equals("") && el.getChildren().size() == 1) {
		//	// empty parent Text object, so just render the one child
		//	return render(el.getChildren().get(0));
		//}

		if(el.isPlain()) {
			return sb.append('"').append(text).append('"').toString();
		}

		sb.append("{");

		if(el.getMode() == TextMode.TEXT) {
			sb.append("\"text\":\"").append(text).append("\"");
		}

		if(el.getColor() != ChatColor.WHITE) {
			sb.append(",\"color\":\"").append(el.getColor().name().toLowerCase()).append("\"");
		}

		for(ChatStyle style: el.getStyles()) {
			sb.append(",\"").append(style.name().toLowerCase()).append("\": true");
		}

		if(el.getChildren().size() > 0) {
			sb.append(",\"extras\":[");
			List<Element> children = el.getChildren();
			sb.append(render(children.get(0)));
			for(int i = 1; i < children.size(); i++) {
				sb.append(',').append(render(children.get(i)));
			}
			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}
}
