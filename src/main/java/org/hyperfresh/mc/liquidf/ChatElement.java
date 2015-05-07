package org.hyperfresh.mc.liquidf;

import org.apache.commons.lang.StringUtils;
import org.hyperfresh.mc.liquidf.enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Last Updated: 2.15.2014
 * ChatElement is a representation of a Chat Object in Minecraft's new JSON chat.
 * Utitlizes "method chaining."
 *
 * @author octopod
 */
public class ChatElement
{
	public ChatElement()
	{

	}

	public ChatElement(LqColor color)
	{
		this.color = color;
	}

	public ChatElement(LqColor color, LqFormat... formats)
	{
		this.color = color;
		this.formats = formats;
	}

	public ChatElement(String text)
	{
		this.text = text;
	}

	public ChatElement(String text, LqColor color, LqFormat... formats)
	{
		this.text = text;
		this.color = color;
		this.formats = formats;
	}

	public ChatElement(String text, LqColor color, LqClickEvent click, LqHoverEvent hover, LqFormat... formats)
	{
		this.text = text;
		this.color = color;
		this.click = click;
		this.hover = hover;
		this.formats = formats;
	}

	private String text = "";
	private boolean translate = false;
	private List<String> with = null;

	private LqColor color = null;
	private LqFormat[] formats = {};

	private LqClickEvent click = null;
	private String click_value = null;

	private LqHoverEvent hover = null;
	private String hover_value = null;

	private List<ChatElement> extras = new ArrayList<>();
	private ChatElement selection = this;

	/**
	 * Returns if the element is plain; white text, no formatting, no events, and no extras
	 *
	 * @return if the element is plain
	 */
	public boolean isPlain()
	{
		return
			color == null &&
			formats.length == 0 &&
			click == null &&
			hover == null &&
			extras.size() == 0;
	}

	//Variable getters
	public LqClickEvent getClick() 		{return click;}
	public LqHoverEvent getHover() 		{return hover;}
	public String 			getClickValue() {return click_value == null ? "" : click_value;}
	public String 			getHoverValue() {return hover_value == null ? "" : hover_value;}
	public String 			getText() 		{return text;}
	public LqColor getColor() 		{return color;}
	public LqFormat[] 	getFormats() 	{return formats;}
	public boolean			getTranslate() {return translate;}
	public List<String> 	getTranslateWith() {return with;}

	/**
	 * Returns a list of the extra elements
	 *
	 * @return the extra elements
	 */
	public List<ChatElement> getExtraElements()
	{
		return extras;
	}

	public List<Object> getSimpleExtraElements()
	{
		List<Object> list = new ArrayList<>();
		for(ChatElement element: extras)
		{
			if(element.isPlain())
			{
				list.add(element.getText());
			} else {
				list.add(element);
			}
		}
		return list;
	}

	/**
	 * Returns a list with this element as the first index, and the extra elements following.
	 *
	 * @return all the elements
	 */
	public List<ChatElement> getAllElements()
	{
		List<ChatElement> elements = new ArrayList<>();
		elements.add(this);
		elements.addAll(extras);
		return elements;
	}

	/**
	 * Sets the text of this element.
	 *
	 * @param text the text of the element
	 */
	public ChatElement setText(String text)
	{
		this.text = text;
		this.translate = false;
		return this;
	}

	/**
	 * Sets the text of this element.
	 * Set <code>translate</code> to true if the text should be translated.
	 *
	 * @param text the text of the element
	 * @param translate whether to translate the text or not
	 */
	public ChatElement setText(String text, boolean translate)
	{
		this.text = text;
		this.translate = translate;
		return this;
	}

	/**
	 * Sets the color of this element.
	 * Not to be confused with color(), which is for the selected element.
	 *
	 * @param color the color of the element
	 */
	public ChatElement setColor(LqColor color)
	{
		this.color = color;
		return this;
	}

	/**
	 * Sets the color of the selected element.
	 *
	 * @param color the color of the element
	 */
	public ChatElement color(LqColor color)
	{
		selection.setColor(color);
		return this;
	}

	/**
	 * Sets the format(s) of this element.
	 * Not to be confused with format(), which is for the selected element.
	 *
	 * @param formats the formats of the element
	 */
	public ChatElement setFormat(LqFormat... formats)
	{
		this.formats = formats;
		return this;
	}

	/**
	 * Sets the format(s) of the selected element.
	 *
	 * @param formats the formats of the element
	 */
	public ChatElement format(LqFormat... formats)
	{
		selection.formats = formats;
		return this;
	}

	public ChatElement bold() {return format(LqFormat.BOLD);}

	public ChatElement italic() {return format(LqFormat.ITALIC);}

	public ChatElement underline() {return format(LqFormat.UNDERLINED);}

	public ChatElement strikethrough() {return format(LqFormat.STRIKETHROUGH);}

	public ChatElement obfuscate() {return format(LqFormat.OBFUSCATED);}

	/**
	 * Sets the click event of this element.
	 *
	 * @param click the click event
	 * @param value the value of the event
	 */
	public ChatElement setClick(LqClickEvent click, String value)
	{
		this.click = click;
		this.click_value = value;
		return this;
	}

	/**
	 * Sets the click event of the selected element.
	 *
	 * @param click the click event
	 * @param value the value of the event
	 */
	public ChatElement click(LqClickEvent click, String value)
	{
		selection.click = click;
		selection.click_value = value;
		return this;
	}

	public ChatElement run(String command)
	{
		return click(LqClickEvent.RUN_COMMAND, command);
	}

	public ChatElement suggest(String command)
	{
		return click(LqClickEvent.SUGGEST_COMMAND, command);
	}

	public ChatElement link(String url)
	{
		return click(LqClickEvent.OPEN_URL, url);
	}

	public ChatElement file(String path)
	{
		return click(LqClickEvent.OPEN_FILE, path);
	}

	/**
	 * Sets the hover event of this element.
	 *
	 * @param hover the hover event
	 * @param value the value of the event
	 */
	public ChatElement setHover(LqHoverEvent hover, String value)
	{
		this.hover = hover;
		this.hover_value = value;
		return this;
	}

	/**
	 * Sets the hover event of the selected element.
	 *
	 * @param hover the hover event
	 * @param value the value of the event
	 */
	public ChatElement hover(LqHoverEvent hover, String value)
	{
		selection.hover = hover;
		selection.hover_value = value;
		return this;
	}

	public ChatElement tooltip(String... lines)
	{
		return hover(LqHoverEvent.SHOW_TEXT, StringUtils.join(lines, "\n"));
	}

	public ChatElement tooltip(ChatElement element)
	{
		return hover(LqHoverEvent.SHOW_TEXT, element.toLegacyString());
	}

	public ChatElement achievement(String name)
	{
		return hover(LqHoverEvent.SHOW_ACHIEVEMENT, name);
	}

	public ChatElement item(String json)
	{
		return hover(LqHoverEvent.SHOW_ITEM, json);
	}

	/**
	 * Selects an extra element by index
	 *
	 * @param index the index of the element
	 *
	 * @throws IllegalArgumentException if the index doesn't exist
	 */
	public ChatElement selectElement(int index)
	{
		validateIndex(index);
		selection = getElementAt(index);
		return this;
	}

	/**
	 * Selects this element.
	 */
	public ChatElement selectBase()
	{
		selection = this;
		return this;
	}

	/**
	 * Selects the last extra element.
	 */
	public ChatElement selectLast()
	{
		selection = getElementLast();
		return this;
	}

	private void validateIndex(int index)
	{
		if (index < 0 || index >= extras.size())
		{
			throw new IllegalArgumentException("Following ChatElement index out of bounds: " + index);
		}
	}

	private void validateNotNull(Object object, String message)
	{
		if(object == null)
		{
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * The total amount of elements.
	 *
	 * @return size of elements.
	 */
	public int size()
	{
		return extras.size();
	}


	/**
	 * Gets the last ChatElement
	 *
	 * @return The last ChatElement.
	 */
	public ChatElement getElementLast()
	{
		return getElementAt(extras.size() - 1);
	}

	/**
	 * Gets the currently selected ChatElement.
	 *
	 * @return The currently selected ChatElement.
	 */
	public ChatElement getElementSelection()
	{
		return selection;
	}

	/**
	 * Gets the ChatElement at the specified index.
	 *
	 * @throws IllegalArgumentException if the index is out of bounds
	 * @return the selection
	 */
	public ChatElement getElementAt(int index)
	{
		validateIndex(index);
		return extras.get(index);
	}

	private void remove_last()
	{
		if(extras != null)
		{
			extras.remove(extras.size() - 1);
		}
	}

	private void push(Collection<ChatElement> list)
	{
		validateNotNull(list, "ChatElement list cannot be null");
		extras.addAll(list);
	}

	private void push(ChatElement... array)
	{
		validateNotNull(array, "ChatElement array cannot be null");
		extras.addAll(Arrays.asList(array));
	}

	private void push(int index, Collection<ChatElement> list)
	{
		validateNotNull(list, "ChatElement list cannot be null");
		validateIndex(index);
		extras.addAll(index, list);
	}

	private void push(int index, ChatElement... array)
	{
		validateNotNull(array, "ChatElement array cannot be null");
		validateIndex(index);
		for(int i = extras.size() - 1; i >= 0; i--)
		{
			extras.add(index, array[i]);
		}
	}

	public ChatElement appendif(boolean b, String text, LqColor color, LqFormat... formats)
	{
		if(b) append(text, color, formats);
		return this;
	}

	public ChatElement appendif(boolean b, String text)
	{
		if(b) append(text);
		return this;
	}

	/**
	 * Appends a space and text at the end of the element.
	 *
	 * @param text the text to append
	 */
	public ChatElement sappend(String text, LqColor color, LqFormat... formats)
	{
		push(new ChatElement(" "), new ChatElement(text, color, formats));
		return selectLast();
	}

	/**
	 * Appends a space and text at the end of the element.
	 *
	 * @param text the text to append
	 */
	public ChatElement sappend(String text)
	{
		push(new ChatElement(" "), new ChatElement(text));
		return selectLast();
	}

	public ChatElement append(Object object)
	{
		push(new ChatElement(object.toString()));
		return selectLast();
	}

	/**
	 * Appends an object to the end of the builder and selects it, while setting color and formats.
	 *
	 * @param object the object
	 * @param color the color of the object
	 * @param formats the formats of the object
	 */
	public ChatElement append(Object object, LqColor color, LqFormat... formats)
	{
		push(new ChatElement(object.toString(), color, formats));
		return selectLast();
	}

	/**
	 * Appends setText to the end of the builder and selects it.
	 *
	 * @param text the text to append
	 */
	public ChatElement append(String text)
	{
		push(new ChatElement(text));
		return selectLast();
	}

	/**
	 * Appends setText to the end of the builder and selects it, while setting color and formats.
	 *
	 * @param text the text to append
	 * @param color the color of the text
	 * @param formats the formats of the text
	 */
	public ChatElement append(String text, LqColor color, LqFormat... formats)
	{
		push(new ChatElement(text, color, formats));
		return selectLast();
	}

	/**
	 * Appends a list of elements to the end of the builder and selects the last one.
	 *
	 * @param list the list of elements
	 */
	public ChatElement append(List<ChatElement> list)
	{
		push(list);
		return selectLast();
	}

	/**
	 * Appends an array of elements to the end of the builder and selects the last one.
	 *
	 * @param element the elemnt to append
	 */
	public ChatElement append(ChatElement element)
	{
		push(element);
		return selectLast();
	}

	/**
	 * Appends an array of elements to the end of the builder and selects the last one.
	 *
	 * @param array an array of elements
	 */
	public ChatElement append(ChatElement... array)
	{
		push(array);
		return selectLast();
	}

	public ChatElement insert(int index, Object object)
	{
		push(index, new ChatElement(object.toString(), color));
		return selectLast();
	}

	/**
	 * Appends an object to the end of the builder and selects it, while setting color and formats.
	 *
	 * @param object the object
	 * @param color the color of the object
	 * @param formats the formats of the object
	 */
	public ChatElement insert(int index, Object object, LqColor color, LqFormat... formats)
	{
		push(index, new ChatElement(object.toString(), color, formats));
		return selectLast();
	}

	/**
	 * Appends text to the end of the builder and selects it.
	 *
	 * @param text the text to append
	 */
	public ChatElement insert(int index, String text)
	{
		push(index, new ChatElement(text, color));
		return selectLast();
	}

	/**
	 * Appends text to the end of the builder and selects it, while setting color and formats.
	 *
	 * @param text the text to append
	 * @param color the color of the text
	 * @param formats the formats of the text
	 */
	public ChatElement insert(int index, String text, LqColor color, LqFormat... formats)
	{
		push(index, new ChatElement(text, color, formats));
		return selectLast();
	}

	/**
	 * Appends a list of elements to the end of the builder and selects the last one.
	 *
	 * @param list the list of elements
	 */
	public ChatElement append(int index, List<ChatElement> list)
	{
		push(index, list);
		return selectLast();
	}

	/**
	 * Appends an array of elements to the end of the builder and selects the last one.
	 *
	 * @param array an array of elements
	 */
	public ChatElement insert(int index, ChatElement... array)
	{
		push(index, array);
		return selectLast();
	}

	/**
	 * Appends text (colorized according to '&' color codes) to the end of the builder and selects it.
	 *
	 * @param text the text of the element
	 */
	public ChatElement legacy(String text)
	{
		return append(Chat.colorize(text));
	}

	/**
	 * Appends text (colorized according to a custom color code) to the end of the builder and selects it.
	 *
	 * @param text the text of the element
	 * @param code the color code
	 */
	public ChatElement legacy(String text, char code)
	{
		return append(Chat.colorize(text, code));
	}

	/**
	 * Aligns the selected element to fit the specified width.
	 * If the base element is selected, it will be cleared and all generated strings will be appended.
	 *
	 * @param width the desired width of the block
	 * @param alignment the alignment of the block
	 */
	public ChatElement block(int width, LqAlignment alignment)
	{
		ChatElement block = Chat.block(selection, width, alignment);
		if(selection == this)
		{
			text = "";
		} else {
			remove_last();
		}
		return append(block);
	}

	/**
	 * Appends a space to the end of the ChatElement.
	 *
	 * @return the ChatElement
	 */
	public ChatElement sp()
	{
		return append(' ');
	}

	/**
	 * Appends any amount of spaces to the end of the ChatElement.
	 *
	 * @param x the amount of spaces
	 *
	 * @return the ChatElement
	 */
	public ChatElement sp(int x)
	{
		char[] spaces = new char[x];
		Arrays.fill(spaces, ' ');
		return append(new String(spaces));
	}

	/**
	 * Pushes filler to the end of the ChatElement, as a new ChatElement. Fillers fit setText to a pixel width (according
	 * to Minecraft's default font) Fillers will contain filler characters if the width is too abnormal. If you want to
	 * avoid filler characters, make sure the width is divisible by 4. (the width of a space) Unexpected behavior might
	 * occur if used with the translate feature of MC's new chat system. It will also select the last selection.
	 *
	 * @param width The width of the filler.
	 */
	public ChatElement filler(int width)
	{
		if(width == 2)
		{
			push(Chat.FILLER_2PX);
		}
		else
		{
			push(Chat.filler(width));
		}
		return selectLast();
	}

	/**
	 * Sends the player this object represented as a chat message.
	 *
	 * @param player The player that the message will be sent to.
	 */

	public void send(ChatReciever player)
	{
		player.sendJSONMessage(toJSONString());
	}

	public void send(ChatReciever player, boolean split)
	{
		if(split)
		{
			for(ChatElement element: extras)
			{
				if(element.getColor() == null) {element.setColor(color);}
				if(element.getFormats() == null) {element.setFormat(formats);}
				player.sendJSONMessage(element.toJSONString());
			}
		}
		else
		{
			player.sendJSONMessage(toJSONString());
		}
	}

	/**
	 * Returns this object as a legacy chat string. Actually just a shortcut to the static toLegacyString method.
	 *
	 * @return Legacy chat string
	 */
	public String toLegacyString()
	{
		return Chat.toLegacyString(this);
	}

	public String toJSONString()
	{
		return Chat.toJSONString(this);
	}

	public String toString()
	{
		return Chat.toJSONString(this);
	}
}
