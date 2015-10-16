package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.ChatColor;
import org.hyperfresh.craftf.enums.ChatStyle;

/**
 * @author octopod
 *
 * A utility class to generate filler space at a specified width in pixels.
 *
 * Since this is a hack/workaround to Minecraft's lack of tab support, and the fact
 * that this class depends on certain factors such as certain unicode characters not
 * being defined in Minecraft's font, this class could very well be obsoleted easily
 * at any point.
 */
public class FillerUtils {

	private interface FillerRenderer<T>
	{
		public T render(String filler);
	}

	private static final FillerRenderer<String> STRING_RENDERER = new FillerRenderer<String>()
	{
		@Override
		public String render(String filler)
		{
			return filler;
		}
	};

	private static final FillerRenderer<Element> ELEMENT_RENDERER = new FillerRenderer<Element>()
	{
		@Override
		public Element render(String filler)
		{
			return new SimpleChatElement(filler, FILLER_COLOR);
		}
	};

	private final static ChatColor FILLER_COLOR = ChatColor.DARK_GRAY;

	private final static String FILLER_2PX_RAW = FILLER_COLOR + "\u2019";
	private final static Element FILLER_2PX = new SimpleChatElement(FILLER_2PX_RAW);

	/**
	 * Creates a filler for use in Minecraft's chat. It's a more raw function used to align setText.
	 *
	 * @param width        the width of the filler (in pixels)
	 * @param lazy         whether or not to skip using filler characters to perfectly match the width (this will create artifacts in the filler)
	 * @param emptyChar    the character to use primarily during the filler (should be a space most of the time)
	 * @return The filler as a string.
	 */
	static public <T> T createFiller(int width, boolean lazy, char emptyChar, ChatFont font, FillerRenderer<T> renderer)
	{
		if (width < 0) throw new IllegalArgumentException("Filler width cannot be less than 0!");
		if (width == 0) return renderer.render("");
		if (width == 1) throw new IllegalArgumentException("A filler cannot be a pixel wide");
		if (width == 2) return renderer.render(FILLER_2PX_RAW);

		final int emptyWidth = font.getWidth(emptyChar);
		StringBuilder filler = new StringBuilder();

		while (width > emptyWidth + 1) {
			filler.append(emptyChar);
			width -= emptyWidth;
		}

		switch (width) {
			case 6:
				if (emptyWidth == 6) {
					filler.append(emptyChar);
					break;
				}
			case 5:
				if (emptyWidth == 5) {
					filler.append(emptyChar);
					break;
				}
				// Use a bolded space (4px + 1px)
				filler.append(ChatStyle.BOLD).append(' ').append(ChatStyle.RESET);
				break;
			case 4:
				if (emptyWidth == 4) {
					filler.append(emptyChar);
					break;
				}
				// Use a space (4px)
				filler.append(" ");
				break;
			case 3:
				if (emptyWidth == 3) {
					filler.append(emptyChar);
					break;
				}
				if(lazy) break;
				// Use the bolded 2px filler (2px + 1px)
				filler.append(FILLER_COLOR).append(ChatStyle.BOLD).append(FILLER_2PX_RAW).append(ChatStyle.RESET);
				break;
			case 2:
				if (emptyWidth == 2) {
					filler.append(emptyChar);
					break;
				}
				if(lazy) break;
				// Use the 2px filler
				filler.append(FILLER_COLOR).append(FILLER_2PX_RAW).append(ChatStyle.RESET);
				break;
		}

		return renderer.render(filler.toString());
	}

	static public <T> T createFiller(int width, FillerRenderer<T> renderer) {
		return createFiller(width, false, ' ', CraftF.getVanillaFont(), renderer);
	}

	static public Element createFiller(int width) {
		return createFiller(width, ELEMENT_RENDERER);
	}
}
