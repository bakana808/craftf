package org.hyperfresh.craftf;

import org.hyperfresh.craftf.enums.ChatColor;
import org.hyperfresh.craftf.parser.MarkupParser;
import org.hyperfresh.craftf.parser.Parser;
import org.hyperfresh.craftf.renderer.JSONRenderer;
import org.hyperfresh.craftf.renderer.LegacyRenderer;
import org.hyperfresh.craftf.renderer.Renderer;

import java.util.List;

/**
 * CraftF API
 *
 * @author octopod
 */
public class CraftF {

	/**
	 * The current instance of MarkupParser.
	 */
	private static final Parser<String> markupParser = new MarkupParser();

	/**
	 * The current instance of JSONRenderer.
	 */
	private static final Renderer<String> jsonRenderer = new JSONRenderer();

	/**
	 * The current instance of LegacyRenderer.
	 */
	private static final Renderer<String> legacyRenderer = new LegacyRenderer();

	/**
	 * The current instance of VanillaTextFont.
	 */
	private static final ChatFont vanillaFont = new VanillaChatFont();

	public static Parser<String> getMarkupParser() {
		return markupParser;
	}

	public static Renderer<String> getJsonRenderer() {
		return jsonRenderer;
	}

	public static Renderer<String> getLegacyRenderer() {
		return legacyRenderer;
	}

	public static ChatFont getVanillaFont() {
		return vanillaFont;
	}

	public static <T> Element parse(T source, Parser<T> parser) {
		return parser.parse(source);
	}

	/**
	 * Shortcut method to {@code CraftF.parse(input, markupParser)}.
	 *
	 * @param input
	 * @return
	 */
	public static Element parse(String input) {
		return parse(input, markupParser);
	}

	/**
	 * Shortcut method to {@code CraftF.render(element, markupParser)}.
	 *
	 * @param element the TextElement to render
	 * @return
	 */
	public static <T> T render(Element element, Renderer<T> renderer) {
		return renderer.render(element);
	}


	public static String render(Element element) {
		return render(element, jsonRenderer);
	}

	/**
	 * Shortcut method to parsing an object, then rendering it.
	 *
	 * @param input the source object
	 * @param parser the parser
	 * @param renderer the renderer
	 * @param <I> input object type
	 * @param <O> output object type
	 * @return
	 */
	public static <I, O> O evaluate(I input, Parser<I> parser, Renderer<O> renderer) {
		return renderer.render(parser.parse(input));
	}

	public static String evaluate(String input) {
		return evaluate(input, markupParser, jsonRenderer);
	}

	// ====================================================
	// TODO: review old code below

	public static String fixText(String text) {
		text = text.replaceAll("(\\n|\\r)", "\\\\n");
		return text;
	}

	public static Element simplify(Element original) {

		Element parent = new ChatElement(original);

		List<Element> children = parent.getChildren();

		// detach all empty children
		//
		for(int i = 0; i < children.size(); i++) {
			Element child = children.get(i);
			if(child.isEmpty()) {
				parent.detach(child);
			} else {
				children.set(i, simplify(child));
			}
		}

		// if the parent has no text and has only one child,
		// copy all properties from the child onto the parent, then detach it.
		// example:
		//
		// {"text":"", "extra":[{"text":"extra", "color":"green"}]}
		//
		// would simplify into:
		//
		// {"text":"extra", "color":"green"}
		//
		if(parent.isSimplifiable()) {
			Element child = parent.getChildren().get(0);
			parent.text(child.getText());
			// if the parent's color is not white and the child's color is white, then keep
			// using the parent's color.
			if(parent.getColor() != ChatColor.WHITE && child.getColor() == ChatColor.WHITE) {
				parent.color(parent.getColor());
			} else {
				parent.color(child.getColor());
			}
			parent.style(child.getStyles());
			parent.click(child.getClickEvent());
			parent.hover(child.getHoverEvent());
			parent.detachAll();
			parent.attach(child.getChildren());
		}

		// if the parent has only formats and the first child is plain, then
		// set the parent's text to the first child's text, then detach it.
		//
		if(parent.isOnlyFormats() && parent.getChildren().size() > 0) {
			Element child = parent.getChildren().get(0);
			if(child.isPlain()) {
				parent.text(child.getText());
				parent.detach(child);
			}
		}

		return parent;
	}

	/**
	 * Shortcut method to {@code TextFont.getWidth(string)}.
	 *
	 * @param string
	 * @return
	 */
	public static int getWidth(String string, ChatFont font) {
		return font.getWidth(string);
	}

	public static int getWidth(String string) {
		return getWidth(string, vanillaFont);
	}

	public static String colorize(String message) {
		return colorize(message, '&');
	}

	public static String colorize(String message, char replace) {
		return message.replace(replace, '\u00A7');
	}

	public static Element join(Element[] texts, Element glue) {
		return join(texts, glue, glue);
	}

	public static Element join(Element[] texts, Element glue, Element lastGlue) {
		Element el = new ChatElement();

		if (texts.length > 0)
		{
			el.attach(texts[0]);
			for (int i = 1; i < texts.length; i++)
			{
				if (i == (texts.length - 1))
				{
					el.attach(lastGlue);
				} else
				{
					el.attach(glue);
				}
				el.attach(texts[i]);
			}
		}

		return el;
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

	private static interface BlockRenderer<T>
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

	private static BlockRenderer<Element> BLOCK_RENDERER_CHAT = new BlockRenderer<Element>()
	{
		@Override
		public Element render(String left, String text, String right)
		{
			return new SimpleChatElement(left + text + right);
		}
	};

	/**
	 * Creates a block of setText with a variable width. Useful for aligning setText into columns on multiple lines.
	 *
	 * @param text       The string to insert
	 * @param toWidth    The width to fit the setText to in pixels. (Will cut the setText if toWidth is shorter than it)
	 * @param alignment  Which way to align the setText. (0: left, 1: right, 2: center)
	 * @param fillerChar The primary character to use for filling. Usually a space.
	 * @param precise    Whether or not to use filler characters to perfectly match the width (this will create artifacts in the filler)
	 * @param renderer   The interface that this method will use to build the return object.
	 * @return The setText fitted to toWidth.
	 */
	//static private <T> T block(String text, int toWidth, TextAlignment alignment, char fillerChar, boolean precise, BlockRenderer<T> renderer)
	//{
	//	String cutText = cut(text, toWidth)[0] + TextStyle.RESET;
	//
	//	//The total width (in pixels) needed to fill
	//	final int totalFillerWidth = toWidth - width(cutText);
	//
	//	int lFillerWidth, rFillerWidth;
	//
	//	switch (alignment)
	//	{
	//		case LEFT:
	//		default:
	//			lFillerWidth = 0;
	//			rFillerWidth = totalFillerWidth;
	//			break;
	//		case RIGHT:
	//			lFillerWidth = totalFillerWidth;
	//			rFillerWidth = 0;
	//			break;
	//		case CENTER: //Cuts the total width to fill in half
	//			lFillerWidth = (int) Math.floor(totalFillerWidth / 2.0);
	//			rFillerWidth = (int) Math.ceil(totalFillerWidth / 2.0);
	//			break;
	//		case CENTER_CEIL:
	//			lFillerWidth = (int) Math.ceil(totalFillerWidth / 2.0);
	//			rFillerWidth = (int) Math.floor(totalFillerWidth / 2.0);
	//			break;
	//	}
	//
	//	return renderer.render(filler(lFillerWidth, precise, fillerChar, FILLER_RENDERER_STRING), cutText, filler(rFillerWidth, precise, fillerChar, FILLER_RENDERER_STRING));
	//}
	//
	//static public String blockString(String text, int toWidth, TextAlignment alignment)
	//{
	//	return blockString(text, toWidth, alignment, ' ', true);
	//}
	//
	//static public String blockString(String text, int toWidth, TextAlignment alignment, char fillerChar, boolean precise)
	//{
	//	return block(text, toWidth, alignment, fillerChar, precise, BLOCK_RENDERER_STRING);
	//}
	//
	//static public Text block(String text, int toWidth, TextAlignment alignment)
	//{
	//	return block(text, toWidth, alignment, ' ', true);
	//}
	//
	//static public Text block(String text, int toWidth, TextAlignment alignment, char fillerChar, boolean precise)
	//{
	//	return block(text, toWidth, alignment, fillerChar, precise, BLOCK_RENDERER_CHAT);
	//}
	//
	//static public Text block(Text element, int toWidth, TextAlignment alignment)
	//{
	//	return block(element, toWidth, alignment, ' ', true);
	//}
	//
	//static public Text block(Text element, int toWidth, TextAlignment alignment, char fillerChar, boolean precise)
	//{
	//	String rendered = CFLegacyRenderer.INSTANCE.render(element);
	//	return block(rendered, toWidth, alignment, fillerChar, precise, BLOCK_RENDERER_CHAT);
	//}

	static public String[] cut(String text, int width)
	{
		return cut(text, width, 0);
	}

	/**
	 * Returns the truncated version of setText to be of toWidth or less.
	 * The setText will be returned unmodified if toWidth is wider than the width of the setText.
	 * TODO: Make this function return a list of strings instead of just the first one
	 *
	 * @param text The setText to use for calculation.
	 * @return The width of the setText inserted. (in pixels)
	 */
	static public String[] cut(String text, int width, int wrap)
	{
		int start = 0;
		int end = text.length();

		while (getWidth(text.substring(start, end)) > width)
		{
			end--;
			if (wrap > 0 && getWidth(text.substring(start, end)) <= width)
			{
				int lookbehind = 0; //Amount of characters looked at behind the end index
				int temp_end = end; //Temporary end marker
				while (lookbehind < wrap && text.charAt(temp_end - 1) != ' ')
				{
					temp_end--;

					if (temp_end <= 0) break;

					lookbehind++;

					if (text.charAt(temp_end - 1) == ' ')
					{
						end = temp_end;
						break;
					}
				}
			}
		}
		return new String[]{text.substring(start, end), text.substring(end)};
	}

	//public static String toJSONString(Text... elements)
	//{
	//	Map<Object, Object> json = new HashMap<>();
	//	json.put("text", "");
	//	json.put("extra", Arrays.asList(elements));
	//	return JSONValue.toJSONString(json);
	//}
	//
	//public static String toJSONString(List<Text> elements)
	//{
	//	Map<Object, Object> json = new HashMap<>();
	//	json.put("text", "");
	//	json.put("extra", elements);
	//	return JSONValue.toJSONString(json);
	//}
	//
	//@SuppressWarnings({"unchecked", "rawtypes"})
	//public static String toJSONString(Text element)
	//{
	//	String text = element.getText();
	//	boolean translate = element.getTranslate();
	//	List<String> with = element.getTranslateWith();
	//
	//	Map<String, Object> json = new HashMap();
	//
	//	if (translate)
	//	{
	//		json.put("translate", text);
	//		if (with.size() > 0)
	//			json.put("with", with);
	//	} else
	//	{
	//		json.put("text", text);
	//	}
	//
	//	if (element.isPlain())
	//	{
	//		return JSONValue.toJSONString(json);
	//	}
	//
	//	if (element.getClick() != null)
	//	{
	//		Map click = new HashMap();
	//		click.put("action", element.getClick().name().toLowerCase());
	//		click.put("value", element.getClickValue());
	//		json.put("clickEvent", click);
	//	}
	//
	//	if (element.getHover() != null)
	//	{
	//		Map hover = new HashMap();
	//		hover.put("action", element.getHover().name().toLowerCase());
	//		hover.put("value", element.getHoverValue());
	//		json.put("hoverEvent", hover);
	//	}
	//
	//	for (TextStyle format : element.getFormats())
	//	{
	//		json.put(format.name().toLowerCase(), true);
	//	}
	//
	//	if (element.getColor() != null)
	//	{
	//		json.put("color", element.getColor().name().toLowerCase());
	//	}
	//
	//	if (element.getExtraElements().size() > 0)
	//	{
	//		json.put("extra", element.getSimpleExtraElements());
	//	}
	//
	//	return JSONValue.toJSONString(json);
	//}
}
