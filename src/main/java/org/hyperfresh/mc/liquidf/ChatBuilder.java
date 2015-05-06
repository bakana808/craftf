package org.hyperfresh.mc.liquidf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Octopod - octopodsquad@gmail.com
 */
public class ChatBuilder
{
	List<ChatElement> elements = new ArrayList<>();

	public ChatElement newline()
	{
		ChatElement element = new ChatElement();
		elements.add(element);
		return element;
	}

	public ChatElement line(int line)
	{
		if(line < 0 || line > elements.size() - 1)
			throw new IllegalArgumentException("Index " + line + " out of bounds!");
		return elements.get(line);
	}

	/**
	 * Sends all the elements to the player separately
	 *
	 * @param player the reciever of the message
	 */
	public void send(ChatReciever player)
	{
		for(ChatElement element: elements)
		{
			player.sendJsonMessage(element.toJSONString());
		}
	}

	/**
	 * Returns the list of elements as a JSON string. Note that the resulting JSON will not be multiline.
	 *
	 * @return the JSON string
	 */
	public String toJSONString()
	{
		return Chat.toJSONString(elements);
	}
}
