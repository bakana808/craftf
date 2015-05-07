package org.hyperfresh.mc.liquidf;

public interface ChatReciever
{
	/*
	 *	//sendJSONMessage() for Bukkit (CraftBukkit)
	 *	PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(json));
	 *	((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	 */

	/**
	 * Sends a message to the player, in JSON format.
	 * If <code>isJSONSupported()</code> is false, a legacy message will be sent instead.
	 * <p>
	 * For an example on what to put in this method,
	 * visit this link: <a href="https://gist.github.com/Dinnerbone/5631634">https://gist.github.com/Dinnerbone/5631634</a>
	 *
	 * @param lines
	 */
	public void sendJSONMessage(String... lines);

	/**
	 * Returns if this ChatReciever supports the JSON format.
	 *
	 * @return true if this ChatReciever supports JSON
	 */
	public boolean isJSONSupported();
}
