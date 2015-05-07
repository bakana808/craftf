package org.hyperfresh.mc.liquidf;

import org.hyperfresh.mc.liquidf.enums.LqAlignment;
import org.hyperfresh.mc.liquidf.enums.LqColor;
import org.hyperfresh.mc.liquidf.enums.LqFormat;

import org.json.simple.JSONValue;

import java.util.*;

/**
 * LiquidF static utilities
 *
 * @author octopod
 */
public class LiquidF
{
	/**
	 * Map of all specific character widths other than 6, which most of the characters have.
	 */
    final private static Map<Character, Integer> widths = new HashMap<>();

    static
	{
        widths.put('*', 5);
        widths.put('>', 5);
        widths.put('<', 5);
        widths.put(',', 2);
        widths.put('!', 2);
        widths.put('{', 5);
        widths.put('}', 5);
        widths.put(')', 5);
        widths.put('(', 5);
        widths.put('\u00a7', 0); //section sign; Minecraft's color code symbol.
        widths.put('[', 4);
        widths.put(']', 4);
        widths.put(':', 2);
        widths.put('\'', 3);
        widths.put('|', 2);
        widths.put('.', 2);
        widths.put('\u2019', 2); //filler character; Reverse quotation mark.
        widths.put('`', 3); //old filler character; Width change since 1.7
        widths.put(' ', 4);
        widths.put('f', 5);
        widths.put('k', 5);
        widths.put('I', 4);
        widths.put('t', 4);
        widths.put('l', 3);
        widths.put('i', 2);
    }

	/**
	 * Returns the width of the inserted character, according to Minecraft's default chat font (in pixels)
	 * Most characters are 6 pixels wide.
	 *
	 * @param character The setText to use for calculation.
	 * @return The width of the setText inserted. (in pixels)
	 */
	static public int width(char character)
	{
		if(widths.containsKey(character))
			return widths.get(character);
		return 6;
	}

	/**
	 * Returns the width of the setText inserted into the function.
	 * Note that bolded characters are 1 pixel wider than normal.
	 * @param text The setText to use for calculation.
	 * @return The width of the setText inserted. (in pixels)
	 */
	static public int width(String text)
	{
		int width = 0;
		boolean isCode = false;
		boolean bolded = false;

		for(char character:text.toCharArray())
		{
			if(character == '\u00a7')
			{
				isCode = true;
			}
			else
			{
				if(isCode)
				{
					if(bolded && LqColor.fromChar(character) != null)
					{
						bolded = false;
					}
					else if(!bolded)
					{
						bolded = (character == 'l' || character == 'L');
					}
					isCode = false;
				}
				else
				{
					width += width(character);
					if(bolded) width += 1;
				}
			}
		}

		return width;
	}

	public static void send(ChatReciever target, String json)
	{
		target.sendJSONMessage(json);
    }

	public static String colorize(String message)
	{
		return colorize(message, '&');
	}

	public static String colorize(String message, char replace)
	{
		return message.replace(replace, '\u00A7');
	}

	/**
	 * Converts a ChatBuilder object to Minecraft legacy chat string.
	 * Obviously, hover and click events won't carry over.
	 * @param elements The ChatBuilder object to convert
	 * @return The legacy chat string.
	 */
    public static String toLegacyString(ChatElement... elements)
	{
		StringBuilder sb = new StringBuilder();

		for(ChatElement e: elements)
		{
			sb.append(toLegacyString(e));
		}

		return sb.toString();
    }

	public static String toLegacyString(ChatElement element)
	{
		StringBuilder sb = new StringBuilder();

		if(element.getColor() != null)
		{
			sb.append(element.getColor());
		}
		for(LqFormat format: element.getFormats())
		{
			sb.append(format);
		}
		if(!element.getText().equals(""))
		{
			sb.append(element.getText());
		}

		for(ChatElement extra: element.getExtraElements())
		{
			if(extra.getColor() != null)
			{
				sb.append(extra.getColor());
			}
			for(LqFormat format: extra.getFormats())
			{
				sb.append(format);
			}
			if(!extra.getText().equals(""))
			{
				sb.append(extra.getText());
			}
		}

		return sb.toString();
	}

	public static ChatElement fromLegacy(String message) {return fromLegacy(message, '\u00A7');}

	/**
	 * Converts Minecraft legacy chat to a ChatBuilder object.
	 * @param message The legacy chat string to convert
	 * @return A new ChatBuilder object.
	 */

	public static ChatElement fromLegacy(String message, char colorCode) {

		ChatElement cb = new ChatElement();

		StringBuilder text = new StringBuilder();
		boolean nextIsColorCode = false;
		LqColor lastColor = LqColor.WHITE;
		List<LqFormat> formats = new ArrayList<>();

		for(char c: message.toCharArray()) {

			if(c == colorCode) {
				nextIsColorCode = true;
				continue;
			}

			if(nextIsColorCode) {
				nextIsColorCode = false;
				LqColor color = LqColor.fromChar(c);
				LqFormat format = LqFormat.fromChar(c);
				if(color != null && format == null) { //This is a color
					//Push new element
					if(!text.toString().equals("")) {
						cb.append(text.toString()).color(lastColor).format(formats.toArray(new LqFormat[formats.size()]));
					}
					//Reset variables
					text = new StringBuilder();
					lastColor = color;
					formats = new ArrayList<>();
				} else if (color == null && format != null) { //This is a format
					formats.add(format);
				}
				continue;
			}

			text.append(c);

		}

		cb.append(text.toString()).color(lastColor).format(formats.toArray(new LqFormat[formats.size()]));

		return cb;
	}

	public static ChatElement join(ChatElement builder, ChatElement glue) {return join(builder, glue, glue);}
	public static ChatElement join(ChatElement builder, ChatElement glue, ChatElement lastGlue)
	{
		ChatElement newBuilder = new ChatElement();
		List<ChatElement> elements = builder.getExtraElements();
		if(elements.size() > 0) {
			newBuilder.append(elements.get(0));
			for(int i = 1; i < elements.size(); i++) {
				if(i == (elements.size() - 1)) {
					newBuilder.append(lastGlue);
				} else {
					newBuilder.append(glue);
				}
				newBuilder.append(elements.get(i));
			}
		}

		return newBuilder;
	}

	/*
	@SuppressWarnings("deprecation")
	public static String itemtoJSON(ItemStack item) {

		Map<String, Object> toJSONString = new HashMap<String, Object>();
		Map<String, Object> meta = new HashMap<String, Object>();
		Map<String, Object> display = new HashMap<String, Object>();

		toJSONString.put("id", item.getTypeId());
		toJSONString.put("Damage", (int)item.getData().getData());
		toJSONString.put("Count", item.getAmount());

		try{
			display.put("Name", item.getItemMeta().getDisplayName());
			meta.put("display", display);
		} catch (NullPointerException e) {}

		toJSONString.put("tag", meta);

		return JSONValue.toJSONString(toJSONString);

	}
	*/

	private static interface BlockRenderer <T>
	{
		public T render(String left, String text, String right);
	}

	private static BlockRenderer<String> BLOCK_RENDERER_STRING = new BlockRenderer<String>()
	{
		@Override
		public String render(String left, String text, String right)
		{
			return left + text + right;
		}
	};

	private static BlockRenderer<ChatElement> BLOCK_RENDERER_CHAT = new BlockRenderer<ChatElement>()
	{
		@Override
		public ChatElement render(String left, String text, String right)
		{
			return new ChatElement().
				appendif(!left.equals(""), left).
				append(text).
				appendif(!right.equals(""), right);
		}
	};

	private static interface FillerRenderer <T>
	{
		public T render(String filler);
	}

	private static FillerRenderer<String> FILLER_RENDERER_STRING = new FillerRenderer<String>()
	{
		@Override
		public String render(String filler)
		{
			return filler;
		}
	};

	private static FillerRenderer<ChatElement> FILLER_RENDERER_CHAT = new FillerRenderer<ChatElement>()
	{
		@Override
		public ChatElement render(String filler)
		{
			return new ChatElement(filler);
		}
	};

	/**
	 * Creates a block of setText with a variable width. Useful for aligning setText into columns on multiple lines.
	 * @param text The string to insert
	 * @param toWidth The width to fit the setText to in pixels. (Will cut the setText if toWidth is shorter than it)
	 * @param alignment Which way to align the setText. (0: left, 1: right, 2: center)
	 * @param fillerChar The primary character to use for filling. Usually a space.
	 * @param precise Whether or not to use filler characters to perfectly match the width (this will create artifacts in the filler)
	 * @param renderer The interface that this method will use to build the return object.
	 * @return The setText fitted to toWidth.
	 */
    static private <T> T block(String text, int toWidth, LqAlignment alignment, char fillerChar, boolean precise, BlockRenderer<T> renderer)
	{
        String cutText = cut(text, toWidth)[0] + LqFormat.RESET;

        //The total width (in pixels) needed to fill
        final int totalFillerWidth = toWidth - width(cutText);

        int lFillerWidth, rFillerWidth;

        switch(alignment) {
        	case LEFT:
        	default:
				lFillerWidth = 0;
        		rFillerWidth = totalFillerWidth;
        		break;
        	case RIGHT:
        		lFillerWidth = totalFillerWidth;
				rFillerWidth = 0;
        		break;
        	case CENTER: //Cuts the total width to fill in half
        		lFillerWidth = (int)Math.floor(totalFillerWidth / 2.0);
        		rFillerWidth = (int)Math.ceil(totalFillerWidth / 2.0);
				break;
			case CENTER_CEIL:
				lFillerWidth = (int)Math.ceil(totalFillerWidth / 2.0);
				rFillerWidth = (int)Math.floor(totalFillerWidth / 2.0);
                break;
        }

       return renderer.render(filler(lFillerWidth, precise, fillerChar, FILLER_RENDERER_STRING), cutText, filler(rFillerWidth, precise, fillerChar, FILLER_RENDERER_STRING));
    }

    static public String blockString(String text, int toWidth, LqAlignment alignment)
	{
    	return blockString(text, toWidth, alignment, ' ', true);
    }

    static public String blockString(String text, int toWidth, LqAlignment alignment, char fillerChar, boolean precise)
	{
        return block(text, toWidth, alignment, fillerChar, precise, BLOCK_RENDERER_STRING);
    }

	static public ChatElement block(String text, int toWidth, LqAlignment alignment)
	{
		return block(text, toWidth, alignment, ' ', true);
	}

	static public ChatElement block(String text, int toWidth, LqAlignment alignment, char fillerChar, boolean precise)
	{
		return block(text, toWidth, alignment, fillerChar, precise, BLOCK_RENDERER_CHAT);
	}

    static public ChatElement block(ChatElement element, int toWidth, LqAlignment alignment)
	{
    	return block(element, toWidth, alignment, ' ', true);
    }

    static public ChatElement block(ChatElement element, int toWidth, LqAlignment alignment, char fillerChar, boolean precise)
	{
        return block(toLegacyString(element), toWidth, alignment, fillerChar, precise, BLOCK_RENDERER_CHAT);
    }

    final static LqColor FILLER_COLOR = LqColor.DARK_GRAY;

    public final static String FILLER_2PX_RAW = FILLER_COLOR + "\u2019";
	public final static ChatElement FILLER_2PX = new ChatElement(FILLER_2PX_RAW);

	/**
	 * Creates a filler for use in Minecraft's chat. It's a more raw function used to align setText.
	 * @param width The width of the filler (in pixels)
	 * @param precise Whether or not to use filler characters to perfectly match the width (this will create artifacts in the filler)
	 * @param customFiller The character to use primarily during the filler (should be a space most of the time)
	 * @return The filler as a string.
	 */
    static public <T> T filler(int width, boolean precise, char customFiller, FillerRenderer<T> renderer)
	{
		if(width < 0) throw new IllegalArgumentException("Filler width cannot be less than 0!");
		if(width == 0) return renderer.render("");
		if(width == 1) throw new IllegalArgumentException("A filler cannot be a pixel wide");
		if(width == 2) return renderer.render(FILLER_2PX_RAW);

    	final int customFillerWidth = width(customFiller);
        StringBuilder filler = new StringBuilder();

        while(width > customFillerWidth + 1){
            filler.append(customFiller);
            width -= customFillerWidth;
        }

        switch(width){
            case 6:
                if(customFillerWidth == 6) {filler.append(customFiller); break;}
            case 5:
                if(customFillerWidth == 5) {filler.append(customFiller); break;}
				// Use a bolded space (4px + 1px)
                filler.append(LqFormat.BOLD).append(' ').append(LqFormat.RESET);
                break;
            case 4:
                if(customFillerWidth == 4) {filler.append(customFiller); break;}
				// Use a space (4px)
                filler.append(" ");
                break;
            case 3:
                if(customFillerWidth == 3) {filler.append(customFiller); break;}
                if(!precise) break;
				// Use the bolded 2px filler (2px + 1px)
                filler.append(FILLER_COLOR).append(LqFormat.BOLD).append(FILLER_2PX_RAW).append(LqFormat.RESET);
                break;
            case 2:
                if(customFillerWidth == 2) {filler.append(customFiller); break;}
                if(!precise) break;
				// Use the 2px filler
                filler.append(FILLER_COLOR).append(FILLER_2PX_RAW).append(LqFormat.RESET);
                break;
        }

        return renderer.render(filler.toString());
    }

    static public ChatElement filler(int width)
	{
    	return filler(width, true, ' ');
    }

    static public ChatElement filler(int width, boolean precise, char emptyFiller)
	{
    	return filler(width, precise, emptyFiller, FILLER_RENDERER_CHAT);
    }

    static public String[] cut(String text, int width)
	{
    	return cut(text, width, 0);
    }

	/**
	 * Returns the truncated version of setText to be of toWidth or less.
	 * The setText will be returned unmodified if toWidth is wider than the width of the setText.
	 * TODO: Make this function return a list of strings instead of just the first one
	 * @param text The setText to use for calculation.
	 * @return The width of the setText inserted. (in pixels)
	 */
	static public String[] cut(String text, int width, int wrap)
	{
    	int start = 0;
    	int end = text.length();

    	while(width(text.substring(start, end)) > width)
		{
    		end--;
			if(wrap > 0 && width(text.substring(start, end)) <= width)
			{
				int lookbehind = 0; //Amount of characters looked at behind the end index
				int temp_end = end; //Temporary end marker
				while(lookbehind < wrap && text.charAt(temp_end - 1) != ' ')
				{
					temp_end--;

					if(temp_end <= 0) break;

					lookbehind++;

					if(text.charAt(temp_end - 1) == ' ')
					{
						end = temp_end;
						break;
					}
				}
			}
    	}
    	return new String[]{text.substring(start, end), text.substring(end)};
    }

	public static String toJSONString(ChatElement... elements)
	{
		Map<Object, Object> json = new HashMap<>();
		json.put("text", "");
		json.put("extra", Arrays.asList(elements));
		return JSONValue.toJSONString(json);
	}

	public static String toJSONString(List<ChatElement> elements)
	{
		Map<Object, Object> json = new HashMap<>();
		json.put("text", "");
		json.put("extra", elements);
		return JSONValue.toJSONString(json);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJSONString(ChatElement element)
	{
		String text = element.getText();
		boolean translate = element.getTranslate();
		List<String> with = element.getTranslateWith();

		Map<String, Object> json = new HashMap();

		if(translate) {
			json.put("translate", text);
			if(with.size() > 0)
				json.put("with", with);
		} else {
			json.put("text", text);
		}

		if(element.isPlain())
		{
			return JSONValue.toJSONString(json);
		}

		if(element.getClick() != null)
		{
			Map click = new HashMap();
			click.put("action", element.getClick().name().toLowerCase());
			click.put("value", element.getClickValue());
			json.put("clickEvent", click);
		}

		if(element.getHover() != null)
		{
			Map hover = new HashMap();
			hover.put("action", element.getHover().name().toLowerCase());
			hover.put("value", element.getHoverValue());
			json.put("hoverEvent", hover);
		}

		for(LqFormat format: element.getFormats())
		{
			json.put(format.name().toLowerCase(), true);
		}

		if(element.getColor() != null)
		{
			json.put("color", element.getColor().name().toLowerCase());
		}

		if(element.getExtraElements().size() > 0)
		{
			json.put("extra", element.getSimpleExtraElements());
		}

		return JSONValue.toJSONString(json);
	}
}
