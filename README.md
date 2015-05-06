LiquidF [ ![Build Status][build-badge] ][build] [ ![Downloads][dl-badge] ][dl] [ ![Join IRC][irc-badge] ][irc]
======
#####a minecraft chat formatter that supports format codes, JSON features, and pseudo-tabs

LiquidF allows you to easily build up formatted chat messages and output them as a JSON string.
There are two ways to do this: via the `.toJSONString()` method, or to send it directly to a subclass of `ChatReciever` with `.send(ChatReciever)`.

You can also output it as a legacy string (for console output), but you will lose JSON features in the process.

Implementation
---
Sending a formatted message directly to a player requires that you wrap a Minecraft Player object in the `ChatReciever` class.

An example, in Bukkit (v1.7):
```java
import ChatReciever;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.entity.Player;

public class BukkitChatReciever extends ChatReciever {

	@Override
	public void isJSONSupported() { return true; }

	@Override
	public void sendJSONMessage(String... lines) {
		for(String line: lines) {
			PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(line));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

}
```

Examples
------
Creating colored / formatted text:
```java
new ChatElement("bold red").color(ChatColor.RED).formats(ChatColor.BOLD).send(player);
```

Creating colored / formatted text (consolidated):
```java
new ChatElement("bold red", ChatColor.RED, ChatColor.BOLD).send(player);
```

Creating colored / formatted text (legacy):
```java
new ChatElement(Chat.colorize("&c&lbold red")).send(player);
```

Creating text with a tooltip:
```java
new ChatElement("text").tooltip("more text", "even more text").send(player);
```

Creating text that runs a command:
```java
new ChatElement("[suicide]").run("/kill").send(player);
```

Creating text on multiple lines
```java
ChatBuilder cb = new ChatBuilder()
cb.newline().append("Blue Line").color(ChatColor.BLUE);
cb.newline().append("Red Line").color(ChatColor.RED);
cb.send(player);
```

Creating aligned text (with a width of 100 and LEFT alignment):
```java
new ChatElement().append("Text").block(100, ChatAlignment.LEFT).send(player);
```

[build-badge]: https://img.shields.io/travis/hyperfresh/mc-liquidf.svg?style=flat-square

[build]: https://travis-ci.org/hyperfresh/mc-liquidf

[dl-badge]: https://img.shields.io/github/downloads/hyperfresh/mc-liquidf/latest/total.svg?style=flat-square

[dl]: https://github.com/hyperfresh/mc-liquidf/releases/latest

[irc-badge]: https://img.shields.io/badge/irc-join%20chat-brightgreen.svg?style=flat-square

[irc]: https://webchat.esper.net/?channels=hyperfresh
