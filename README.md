LiquidF [ ![Build Status][build-badge] ][build] [ ![Downloads][dl-badge] ][dl] [ ![Join IRC][irc-badge] ][irc]
======
#####a minecraft chat formatter that supports format codes, JSON features, and pseudo-tabs

LiquidF allows you to easily build up formatted chat messages and output them as a JSON string.

The JSON string is formatted according to this Gist: https://gist.github.com/Dinnerbone/5631634

You can also output it as a legacy string for use outside of Minecraft players.
Of course, any extra JSON functions won't be carried over.

 - [Contributing](#contributing)
 - [Usage](#usage)
 - [Custom Font Support](#custom-font-support)

Contributing
---

Feel free to fork this project and create pull requests.

There's no contributing guideline yet, but just remember to be consistent with the rest of the code.

Usage
---
Note that the message builder is different from `ChatElement()`: most functions are run in the opposite order.

That is because text elements are now immutable, so instead of changing information about a text element after appending it,
you have to prime the information before appending it.

####Building a Chat Message
```java
CFMessage msg = (new CFMessage()).color(CFColor.GREEN).format(CFFormat.BOLD).text("liquidf");

/*
	output: "{"text":"liquidf","color":"green","bold":"true"}"
*/
String json = msg.toJSONString();

/*
	output: "§a§lliquidf"
*/
String legacy = msg.toString();
```

####Pseudo-Tab Support
Because tabs aren't natively supported in Minecraft, we try to build around that by offering pseudo-tabs, which are created by using a combination of spaces and filler characters to achieve alignment in text.

You can test out the filler generator yourself by running `LiquidF().createFiller(40)`, which will create filler at a pixel width (40, for example).

Custom Font Support
---
Because of LiquidF's dependence on the character sizes of a font, we provide an interface `CFFontInfo` that will allow developers to support custom fonts.

```java
public class CustomFontInfo implements CFFontInfo
{
	@Override
	public int getWidth(char c)
	{
		//return width lookups here
	}
}
```

A vanilla implementation, `CFVanillaFontInfo`, is already provided.

[build-badge]: https://img.shields.io/travis/hyperfresh/mc-liquidf.svg?style=flat-square

[build]: https://travis-ci.org/hyperfresh/mc-liquidf

[dl-badge]: https://img.shields.io/github/downloads/hyperfresh/mc-liquidf/latest/total.svg?style=flat-square

[dl]: https://github.com/hyperfresh/mc-liquidf/releases/latest

[irc-badge]: https://img.shields.io/badge/irc-join%20chat-brightgreen.svg?style=flat-square

[irc]: https://webchat.esper.net/?channels=hyperfresh
