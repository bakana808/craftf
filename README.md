ChatBuilder
======
A library relating to JSON-styled minecraft chat.
Also doubles as a chat aligner.

Make sure your implementation of a minecraft player implements the ChatReciever class
so they they can be sent messages by ChatBuilder.

As of 1.7, JSON-styled text does not support the newline character. So for now, just
create multiple builders for each line.

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