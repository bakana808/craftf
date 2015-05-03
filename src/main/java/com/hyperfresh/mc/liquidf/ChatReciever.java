package com.hyperfresh.mc.liquidf;

public interface ChatReciever
{
	/*
	 *	//sendJSONMessage() for Bukkit (CraftBukkit)
	 *	PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(json));
	 *	((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	 */

	/**
	 * Sends a message to the player, in JSON format.
	 * For an example on what to put in this method,
	 * visit this link: <a href="https://gist.github.com/Dinnerbone/5631634">https://gist.github.com/Dinnerbone/5631634</a>
	 * @param json
	 */
	public void sendJSONMessage(String json);
}
